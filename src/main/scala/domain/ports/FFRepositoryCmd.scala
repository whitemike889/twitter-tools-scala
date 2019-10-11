package net.kgtkr.twitter_tools.domain.ports;

import net.kgtkr.twitter_tools.domain.models.FF

trait FFRepositoryCmdSYM[F[_]] {
  def insert(
      ff: FF
  ): F[Unit]
}

object FFRepositoryCmdSYM {
  def apply[F[_]](implicit x: FFRepositoryCmdSYM[F]): FFRepositoryCmdSYM[F] =
    x
}
