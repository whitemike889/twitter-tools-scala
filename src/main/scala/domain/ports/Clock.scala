package net.kgtkr.twitter_tools.domain.ports;

import net.kgtkr.twitter_tools.domain.models.AppError
import cats.data.EitherT
import net.kgtkr.twitter_tools.domain.models.Raw
import java.time.OffsetDateTime

trait Clock[F[_]] {
  def currentDate(): F[OffsetDateTime]
}

object Clock {
  def apply[F[_]](implicit x: Clock[F]): Clock[F] =
    x
}
