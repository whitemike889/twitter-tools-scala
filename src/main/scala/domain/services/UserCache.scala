package net.kgtkr.twitter_tools.domain.services;

import net.kgtkr.twitter_tools.domain.models.UserId
import cats.Monad
import cats._
import net.kgtkr.twitter_tools.domain.ports.RawRepositoryCmdSYM
import net.kgtkr.twitter_tools.domain.ports.RawRepositoryQuerySYM
import net.kgtkr.twitter_tools.domain.ports.TwitterClientQuerySYM
import net.kgtkr.twitter_tools.domain.models.UserRaw
import net.kgtkr.twitter_tools.domain.models.Token
import cats.implicits._

trait UserCacheSYM[F[_]] {
  type Result[T] = F[T]

  def lookupUsers(
      token: Token,
      ids: Set[UserId]
  ): Result[Map[UserId, UserRaw]]
}

object UserCacheSYM {
  def apply[F[_]](implicit x: UserCacheSYM[F]): UserCacheSYM[F] =
    x
}

final class UserCacheImpl[F[_]: Monad: RawRepositoryCmdSYM: RawRepositoryQuerySYM: TwitterClientQuerySYM]
    extends UserCacheSYM[F] {

  override def lookupUsers(
      token: Token,
      ids: Set[UserId]
  ): Result[Map[UserId, UserRaw]] = {
    (for {
      dbUsers <- RawRepositoryQuerySYM[F]
        .findLatest[UserRaw](ids.toList)
        .map(_.map(x => (x.id, x)).toMap)
      fetchedUserArr <- TwitterClientQuerySYM[F]
        .lookupUsers(token, ids.diff(dbUsers.keySet).toList)
      _ <- RawRepositoryCmdSYM[F]
        .insertAll(fetchedUserArr)
    } yield (dbUsers.toSeq ++ fetchedUserArr
      .map(x => (x.id, x))
      .toSeq).toMap)
  }
}
