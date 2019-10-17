package net.kgtkr.twitter_tools.domain.ports;

import net.kgtkr.twitter_tools.domain.models.UserId
import net.kgtkr.twitter_tools.domain.models.Token
import net.kgtkr.twitter_tools.domain.models.UserRaw
import net.kgtkr.twitter_tools.domain.models.Token
import cats.data.ReaderT

trait TwitterClientQuerySYM[F[_]] {
  type Result[T] = F[T]

  def fetchAuthUserId(token: Token): Result[UserId]

  def fetchFollowers(token: Token, userId: UserId): Result[List[UserId]]

  def fetchFriends(token: Token, userId: UserId): Result[List[UserId]]

  def lookupUsers(token: Token, ids: List[UserId]): Result[List[UserRaw]]
}

object TwitterClientQuerySYM {
  def apply[F[_]](
      implicit x: TwitterClientQuerySYM[F]
  ): TwitterClientQuerySYM[F] =
    x
}
