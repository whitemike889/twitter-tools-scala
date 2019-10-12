package net.kgtkr.twitter_tools.domain.ports;

import net.kgtkr.twitter_tools.domain.models.FF

trait FFRepositoryCmdSYM[F[_]] {
  type Result[T] = F[T]

  def insert(
      ff: FF
  ): Result[Unit]
}

object FFRepositoryCmdSYM {
  def apply[F[_]](implicit x: FFRepositoryCmdSYM[F]): FFRepositoryCmdSYM[F] =
    x
}
