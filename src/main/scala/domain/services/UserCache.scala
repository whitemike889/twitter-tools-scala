package net.kgtkr.twitter_tools.domain.services;

import net.kgtkr.twitter_tools.domain.models.UserId
import cats.Monad
import cats._
import cats.data._
import net.kgtkr.twitter_tools.domain.ports.RawRepositoryCmdSYM
import net.kgtkr.twitter_tools.domain.ports.RawRepositoryQuerySYM
import net.kgtkr.twitter_tools.domain.ports.TwitterClientQuerySYM
import net.kgtkr.twitter_tools.domain.models.Raw
import net.kgtkr.twitter_tools.domain.models.UserRaw
import net.kgtkr.twitter_tools.domain.models.Token
import org.atnos.eff._
import cats.data.Reader

trait UserCacheSYM[F[_]] {
  type _readerToken[R] = Reader[Token, ?] |= R
  type _effects[R] = _readerToken[R]

  def lookupUsers[R: _effects](ids: Set[UserId]): Eff[R, Map[UserId, Raw]]
}

object UserCacheSYM {
  def apply[F[_]](implicit x: UserCacheSYM[F]): UserCacheSYM[F] =
    x
}

final class UserCacheImpl[F[_]: Monad: RawRepositoryCmdSYM: RawRepositoryQuerySYM: TwitterClientQuerySYM]
    extends UserCacheSYM[F] {
  override def lookupUsers[R: _effects](
      ids: Set[UserId]
  ): Eff[R, Map[UserId, Raw]] = {
    for {
      dbUsers <- RawRepositoryQuerySYM[F]
        .findLatest[R, UserRaw](ids.toList)
        .map(_.map(x => (x.id, x)).toMap)
      fetchedUserArr <- TwitterClientQuerySYM[F].lookupUsers[R](
        ids.diff(dbUsers.keySet).toList
      )
      _ <- RawRepositoryCmdSYM[F]
        .insertAll[R](fetchedUserArr)
    } yield (dbUsers.toSeq ++ fetchedUserArr.map(x => (x.id, x)).toSeq).toMap
  }
}
