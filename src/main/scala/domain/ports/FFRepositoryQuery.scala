package net.kgtkr.twitter_tools.domain.ports;

import net.kgtkr.twitter_tools.domain.models.AppError
import cats.data.EitherT
import net.kgtkr.twitter_tools.domain.models.FF
import net.kgtkr.twitter_tools.domain.models.UserId

trait FFRepositoryQuery[F[_]] {
  type Result[A] = EitherT[F, AppError[Any, Nothing], A]

  def findUser(
      userId: UserId,
      limit: Int
  ): Result[List[FF]]
}

object FFRepositoryQuery {
  def apply[F[_]](implicit x: FFRepositoryQuery[F]): FFRepositoryQuery[F] =
    x
}
