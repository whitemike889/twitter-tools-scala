package net.kgtkr.twitter_tools.domain.ports;

import net.kgtkr.twitter_tools.domain.models.Raw
import org.atnos.eff._

trait RawRepositoryCmdSYM[F[_]] {
  def insertAll[R](
      raws: List[Raw]
  ): Eff[R, Unit]
}

object RawRepositoryCmdSYM {
  def apply[F[_]](implicit x: RawRepositoryCmdSYM[F]): RawRepositoryCmdSYM[F] =
    x
}
