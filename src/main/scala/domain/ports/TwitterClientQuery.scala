package net.kgtkr.twitter_tools.domain.ports;

import net.kgtkr.twitter_tools.domain.models.UserId
import net.kgtkr.twitter_tools.domain.models.Token
import net.kgtkr.twitter_tools.domain.models.UserRaw
import net.kgtkr.twitter_tools.domain.models.Token
import org.atnos.eff._
import cats.data.Reader

trait TwitterClientQuerySYM[F[_]] {
  type _readerToken[R] = Reader[Token, ?] |= R
  type _effects[R] = _readerToken[R]

  def fetchAuthUserId[R: _effects](): Eff[R, UserId]

  def fetchFollowers[R: _effects](userId: UserId): Eff[R, List[UserId]]

  def fetchFriends[R: _effects](userId: UserId): Eff[R, List[UserId]]

  def lookupUsers[R: _effects](ids: List[UserId]): Eff[R, List[UserRaw]]
}

object TwitterClientQuerySYM {
  def apply[F[_]](
      implicit x: TwitterClientQuerySYM[F]
  ): TwitterClientQuerySYM[F] =
    x
}
