package net.kgtkr.twitter_tools.domain.ports;

import cats.data.EitherT
import net.kgtkr.twitter_tools.domain.models.AppError
import net.kgtkr.twitter_tools.domain.models.DiscordPayload

sealed trait DiscordHookError;
object DiscordHookError {}

trait DiscordHook[F[_]] {
  type Result[A] = EitherT[F, AppError[Any, DiscordHookError], A]

  def post(payload: DiscordPayload): Result[Unit]
}

object DiscordHook {
  def apply[F[_]](implicit x: DiscordHook[F]): DiscordHook[F] =
    x
}
