package net.kgtkr.twitter_tools.domain.ports;

import net.kgtkr.twitter_tools.domain.models.AppError
import cats.data.EitherT
import net.kgtkr.twitter_tools.domain.models.Raw

sealed trait RawRepositoryQueryError;
object RawRepositoryQueryError {}

trait RawRepositoryQuery[F[_]] {
  type Result[A] = EitherT[F, AppError[Any, RawRepositoryQueryError], A]

  def findLatest(
      ids: List[Raw#I]
  ): Result[List[Raw]]
}

object RawRepositoryQuery {
  def apply[F[_]](implicit x: RawRepositoryQuery[F]): RawRepositoryQuery[F] =
    x
}
