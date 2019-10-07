package net.kgtkr.twitter_tools.domain.ports;

import cats.data.EitherT
import cats.data.ReaderT
import net.kgtkr.twitter_tools.domain.models.AppError
import net.kgtkr.twitter_tools.domain.models.UserId
import net.kgtkr.twitter_tools.domain.models.Token
import net.kgtkr.twitter_tools.domain.models.UserRaw

sealed trait TwitterClientQueryError;
object TwitterClientQueryError {}

trait TwitterClientQuery[F[_]] {
  type Result[A] =
    ReaderT[EitherT[F, AppError[Any, TwitterClientQueryError], *], Token, A]

  def fetchAuthUserId(): Result[UserId]

  def fetchFollowers(userId: UserId): Result[List[UserId]]

  def fetchFriends(userId: UserId): Result[List[UserId]]

  def lookupUsers(ids: List[UserId]): Result[List[UserRaw]]
}

object TwitterClientQuery {
  def apply[F[_]](implicit x: TwitterClientQuery[F]): TwitterClientQuery[F] =
    x
}
