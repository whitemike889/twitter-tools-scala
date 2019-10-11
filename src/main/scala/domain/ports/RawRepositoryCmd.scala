package net.kgtkr.twitter_tools.domain.ports;

import net.kgtkr.twitter_tools.domain.models.Raw

trait RawRepositoryCmdSYM[F[_]] {
  def insertAll(
      raws: List[Raw]
  ): F[Unit]
}

object RawRepositoryCmdSYM {
  def apply[F[_]](implicit x: RawRepositoryCmdSYM[F]): RawRepositoryCmdSYM[F] =
    x
}
