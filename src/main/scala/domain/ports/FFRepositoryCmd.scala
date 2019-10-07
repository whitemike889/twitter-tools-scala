package net.kgtkr.twitter_tools.domain.ports;

import net.kgtkr.twitter_tools.domain.models.AppError
import cats.data.EitherT
import net.kgtkr.twitter_tools.domain.models.FF

sealed trait FFRepositoryCmdError;
object FFRepositoryCmdError {}

trait FFRepositoryCmd[F[_]] {
  type Result[A] = EitherT[F, AppError[Any, FFRepositoryCmdError], A]

  def insert(
      ff: FF
  ): Result[Unit]
}

object FFRepositoryCmd {
  def apply[F[_]](implicit x: FFRepositoryCmd[F]): FFRepositoryCmd[F] =
    x
}
