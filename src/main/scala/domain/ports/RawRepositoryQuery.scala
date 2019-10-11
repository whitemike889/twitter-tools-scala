package net.kgtkr.twitter_tools.domain.ports;

import net.kgtkr.twitter_tools.domain.models.Raw
import org.atnos.eff._

trait RawRepositoryQuerySYM[F[_]] {
  def findLatest[R, A <: Raw](
      ids: List[A#I]
  ): Eff[R, List[A]]
}

object RawRepositoryQuerySYM {
  def apply[F[_]](
      implicit x: RawRepositoryQuerySYM[F]
  ): RawRepositoryQuerySYM[F] =
    x
}
