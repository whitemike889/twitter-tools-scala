package net.kgtkr.twitter_tools.domain.services;

import net.kgtkr.twitter_tools.domain.models.UserId
import cats.Monad
import cats._
import cats.data._
import net.kgtkr.twitter_tools.domain.ports.RawRepositoryCmdSYM
import net.kgtkr.twitter_tools.domain.ports.RawRepositoryQuerySYM
import net.kgtkr.twitter_tools.domain.ports.TwitterClientQuerySYM
import net.kgtkr.twitter_tools.domain.models.UserRaw
import net.kgtkr.twitter_tools.domain.models.Token
import cats.data.Reader
import cats.data.ReaderT
import scala.util.chaining._
import net.kgtkr.twitter_tools.utils._

trait UserCacheSYM[F[_]] {
  type Result[T] = Reader[Token, F[T]]

  def lookupUsers(
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
      ids: Set[UserId]
  ): Reader[Token, F[Map[UserId, UserRaw]]] = {
    (for {
      dbUsers <- RawRepositoryQuerySYM[F]
        .findLatest[UserRaw](ids.toList)
        .pipe(ReaderT.liftF)
        .map(_.map(x => (x.id, x)).toMap)
      fetchedUserArr <- TwitterClientQuerySYM[F]
        .lookupUsers(
          ids.diff(dbUsers.keySet).toList
        )
        .toReaderT
      _ <- RawRepositoryCmdSYM[F]
        .insertAll(fetchedUserArr)
        .pipe(ReaderT.liftF)
    } yield (dbUsers.toSeq ++ fetchedUserArr
      .map(x => (x.id, x))
      .toSeq).toMap).runReaderT
  }
}
