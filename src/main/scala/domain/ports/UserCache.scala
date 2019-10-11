package net.kgtkr.twitter_tools.domain.ports;

import cats.data.EitherT
import net.kgtkr.twitter_tools.domain.models.AppError
import net.kgtkr.twitter_tools.domain.models.UserId
import net.kgtkr.twitter_tools.domain.models.Token
import net.kgtkr.twitter_tools.domain.models.Raw

trait UserCacheSYM[F[_]] {
  type Result[A] = EitherT[F, AppError[Any, Nothing], A]

  def lookupUsers(ids: Set[UserId]): Result[Map[UserId, Raw]]
}

object UserCacheSYM {
  def apply[F[_]](implicit x: UserCacheSYM[F]): UserCacheSYM[F] =
    x
}
