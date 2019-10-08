package net.kgtkr.twitter_tools.domain.ports;

import net.kgtkr.twitter_tools.domain.models.Token

trait TokenReader[F[_]] {
  def token(): F[Token]
}

object TokenReader {
  def apply[F[_]](implicit x: TokenReader[F]): TokenReader[F] =
    x
}
