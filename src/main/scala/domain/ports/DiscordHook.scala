package net.kgtkr.twitter_tools.domain.ports;

import net.kgtkr.twitter_tools.domain.models.DiscordPayload

trait DiscordHookSYM[F[_]] {
  type Result[T] = F[T]

  def post(url: String, payload: DiscordPayload): Result[Unit]
}

object DiscordHookSYM {
  def apply[F[_]](implicit x: DiscordHookSYM[F]): DiscordHookSYM[F] =
    x
}
