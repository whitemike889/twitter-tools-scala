package net.kgtkr.twitter_tools.domain.ports;

import net.kgtkr.twitter_tools.domain.models.Raw

trait RawRepositoryQuerySYM[F[_]] {
  def findLatest[A <: Raw](
      ids: List[A#I]
  ): F[List[A]]
}

object RawRepositoryQuerySYM {
  def apply[F[_]](
      implicit x: RawRepositoryQuerySYM[F]
  ): RawRepositoryQuerySYM[F] =
    x
}
