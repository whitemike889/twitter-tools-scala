package net.kgtkr.twitter_tools.domain.ports;

import net.kgtkr.twitter_tools.domain.models.UserId
import net.kgtkr.twitter_tools.domain.models.Token
import net.kgtkr.twitter_tools.domain.models.UserRaw
import net.kgtkr.twitter_tools.domain.models.Token
import cats.data.Reader

trait TwitterClientQuerySYM[F[_]] {
  def fetchAuthUserId(): Reader[Token, F[UserId]]

  def fetchFollowers(userId: UserId): Reader[Token, F[List[UserId]]]

  def fetchFriends(userId: UserId): Reader[Token, F[List[UserId]]]

  def lookupUsers(ids: List[UserId]): Reader[Token, F[List[UserRaw]]]
}

object TwitterClientQuerySYM {
  def apply[F[_]](
      implicit x: TwitterClientQuerySYM[F]
  ): TwitterClientQuerySYM[F] =
    x
}
