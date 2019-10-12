package net.kgtkr.twitter_tools.domain.ports;

import java.time.OffsetDateTime

trait ClockSYM[F[_]] {
  type Result[T] = F[T]
  def currentDate(): Result[OffsetDateTime]
}

object ClockSYM {
  def apply[F[_]](implicit x: ClockSYM[F]): ClockSYM[F] =
    x
}
