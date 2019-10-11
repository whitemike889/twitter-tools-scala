package net.kgtkr.twitter_tools.domain.ports;

import net.kgtkr.twitter_tools.domain.models.UserId
import net.kgtkr.twitter_tools.domain.models.Token
import net.kgtkr.twitter_tools.domain.models.UserRaw

trait TwitterClientQuerySYM[F[_]] {
  def fetchAuthUserId(): F[UserId]

  def fetchFollowers(userId: UserId): F[List[UserId]]

  def fetchFriends(userId: UserId): F[List[UserId]]

  def lookupUsers(ids: List[UserId]): F[List[UserRaw]]
}

object TwitterClientQuerySYM {
  def apply[F[_]](
      implicit x: TwitterClientQuerySYM[F]
  ): TwitterClientQuerySYM[F] =
    x
}
