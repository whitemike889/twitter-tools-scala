package net.kgtkr.twitter_tools.domain.ports;

import net.kgtkr.twitter_tools.domain.models.Raw
import net.kgtkr.twitter_tools.domain.models.RawId
import org.atnos.eff._, all._
import cats.data.Reader
import org.atnos.eff.Members.{&:, &&:}

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
