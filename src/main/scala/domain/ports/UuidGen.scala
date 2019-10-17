package net.kgtkr.twitter_tools.domain.ports;

import net.kgtkr.twitter_tools.domain.models.Raw
import java.util.UUID

trait UuidGenSYM[F[_]] {
  type Result[T] = F[T]

  def gen(): Result[UUID]
}

object UuidGenSYM {
  def apply[F[_]](
      implicit x: UuidGenSYM[F]
  ): UuidGenSYM[F] =
    x
}
