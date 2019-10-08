package net.kgtkr.twitter_tools.domain.ports;

import net.kgtkr.twitter_tools.domain.models.AppError
import cats.data.EitherT
import net.kgtkr.twitter_tools.domain.models.FF

trait FFRepositoryCmd[F[_]] {
  type Result[A] = EitherT[F, AppError[Any, Nothing], A]

  def insert(
      ff: FF
  ): Result[Unit]
}

object FFRepositoryCmd {
  def apply[F[_]](implicit x: FFRepositoryCmd[F]): FFRepositoryCmd[F] =
    x
}
