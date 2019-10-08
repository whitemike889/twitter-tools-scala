package net.kgtkr.twitter_tools.domain.services;

import cats.syntax.monad
import net.kgtkr.twitter_tools.domain.models.UserId
import cats.Monad
import net.kgtkr.twitter_tools.domain.ports.RawRepositoryCmd
import net.kgtkr.twitter_tools.domain.ports.RawRepositoryQuery
import net.kgtkr.twitter_tools.domain.ports.TwitterClientQuery
import net.kgtkr.twitter_tools.domain.models.AppError
import cats.data.EitherT
import net.kgtkr.twitter_tools.domain.models.Raw
import net.kgtkr.twitter_tools.domain.models.UserRaw

object UserCache {
  def lookupUsers[F[_]: Monad: RawRepositoryCmd: RawRepositoryQuery: TwitterClientQuery](
      ids: Set[UserId]
  ): EitherT[F, AppError[Any, Nothing], Map[UserId, Raw]] = {
    for {
      dbUsers <- RawRepositoryQuery[F]
        .findLatest[UserRaw](ids.toList)
        .map(_.map(x => (x.id, x)).toMap)
      fetchedUserArr <- TwitterClientQuery[F].lookupUsers(
        ids.diff(dbUsers.keySet).toList
      )
      _ <- RawRepositoryCmd[F].insertAll(fetchedUserArr)
    } yield (dbUsers.toSeq ++ fetchedUserArr.map(x => (x.id, x)).toSeq).toMap
  }
}
