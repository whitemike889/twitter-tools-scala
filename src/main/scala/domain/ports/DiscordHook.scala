package net.kgtkr.twitter_tools.domain.ports;

import net.kgtkr.twitter_tools.domain.models.DiscordPayload

trait DiscordHookSYM[F[_]] {
  def post(payload: DiscordPayload): F[Unit]
}

object DiscordHookSYM {
  def apply[F[_]](implicit x: DiscordHookSYM[F]): DiscordHookSYM[F] =
    x
}
