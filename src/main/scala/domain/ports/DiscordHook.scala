package net.kgtkr.twitter_tools.domain.ports;

import cats.data.EitherT
import net.kgtkr.twitter_tools.domain.models.AppError
import net.kgtkr.twitter_tools.domain.models.DiscordPayload

trait DiscordHookSYM[F[_]] {
  type Result[A] = EitherT[F, AppError[Any, Nothing], A]

  def post(payload: DiscordPayload): Result[Unit]
}

object DiscordHookSYM {
  def apply[F[_]](implicit x: DiscordHookSYM[F]): DiscordHookSYM[F] =
    x
}
