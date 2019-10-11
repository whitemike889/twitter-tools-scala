package net.kgtkr.twitter_tools.domain.ports;

import net.kgtkr.twitter_tools.domain.models.Token

trait TokenReaderSYM[F[_]] {
  def token(): F[Token]
}

object TokenReaderSYM {
  def apply[F[_]](implicit x: TokenReaderSYM[F]): TokenReaderSYM[F] =
    x
}
