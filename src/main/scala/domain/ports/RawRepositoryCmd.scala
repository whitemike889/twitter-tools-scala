package net.kgtkr.twitter_tools.domain.ports;

import net.kgtkr.twitter_tools.domain.models.AppError
import cats.data.EitherT
import net.kgtkr.twitter_tools.domain.models.Raw

sealed trait RawRepositoryCmdError;
object RawRepositoryCmdError {}

trait RawRepositoryCmd[F[_]] {
  type Result[A] = EitherT[F, AppError[Any, RawRepositoryCmdError], A]

  def insertAll(
      raws: List[Raw]
  ): Result[Unit]
}

object RawRepositoryCmd {
  def apply[F[_]](implicit x: RawRepositoryCmd[F]): RawRepositoryCmd[F] =
    x
}
