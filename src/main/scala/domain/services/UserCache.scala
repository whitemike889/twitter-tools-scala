package net.kgtkr.twitter_tools.domain.services;

import cats.syntax.monad
import net.kgtkr.twitter_tools.domain.models.UserId
import cats.Monad
import cats.implicits._
import net.kgtkr.twitter_tools.domain.ports.RawRepositoryCmdSYM
import net.kgtkr.twitter_tools.domain.ports.RawRepositoryQuerySYM
import net.kgtkr.twitter_tools.domain.ports.TwitterClientQuerySYM
import net.kgtkr.twitter_tools.domain.models.Raw
import net.kgtkr.twitter_tools.domain.models.UserRaw

trait UserCacheSYM[F[_]] {
  def lookupUsers(ids: Set[UserId]): F[Map[UserId, Raw]]
}

object UserCacheSYM {
  def apply[F[_]](implicit x: UserCacheSYM[F]): UserCacheSYM[F] =
    x
}

final class UserCacheImpl[F[_]: Monad: RawRepositoryCmdSYM: RawRepositoryQuerySYM: TwitterClientQuerySYM]
    extends UserCacheSYM[F] {
  override def lookupUsers(
      ids: Set[UserId]
  ): F[Map[UserId, Raw]] = {
    for {
      dbUsers <- RawRepositoryQuerySYM[F]
        .findLatest[UserRaw](ids.toList)
        .map(_.map(x => (x.id, x)).toMap)
      fetchedUserArr <- TwitterClientQuerySYM[F].lookupUsers(
        ids.diff(dbUsers.keySet).toList
      )
      _ <- RawRepositoryCmdSYM[F].insertAll(fetchedUserArr)
    } yield (dbUsers.toSeq ++ fetchedUserArr.map(x => (x.id, x)).toSeq).toMap
  }
}
