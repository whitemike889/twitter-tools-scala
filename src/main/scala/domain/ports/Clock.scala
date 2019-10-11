package net.kgtkr.twitter_tools.domain.ports;

import java.time.OffsetDateTime

trait ClockSYM[F[_]] {
  def currentDate(): F[OffsetDateTime]
}

object ClockSYM {
  def apply[F[_]](implicit x: ClockSYM[F]): ClockSYM[F] =
    x
}
