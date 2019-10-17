package net.kgtkr.twitter_tools.domain.ports;

import net.kgtkr.twitter_tools.domain.models.DiscordPayload
import cats.data.ReaderT

trait DiscordHookSYM[F[_]] {
  type Result[T] = ReaderT[F, String, T]

  def post(payload: DiscordPayload): Result[Unit]
}

object DiscordHookSYM {
  def apply[F[_]](implicit x: DiscordHookSYM[F]): DiscordHookSYM[F] =
    x
}
