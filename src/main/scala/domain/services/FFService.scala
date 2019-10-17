package net.kgtkr.twitter_tools.domain.services;

import net.kgtkr.twitter_tools.domain.models.Token
import net.kgtkr.twitter_tools.domain.models.FF
import cats.Monad
import net.kgtkr.twitter_tools.domain.ports.ClockSYM
import net.kgtkr.twitter_tools.domain.ports.TwitterClientQuerySYM
import net.kgtkr.twitter_tools.domain.ports.UuidGenSYM
import cats.implicits._
import net.kgtkr.twitter_tools.domain.models.FFId

trait FFServiceSYM[F[_]] {
  type Result[A] = F[A]

  def create(token: Token): Result[FF]
}

object FFServiceSYM {
  def apply[F[_]](implicit x: FFServiceSYM[F]): FFServiceSYM[F] =
    x
}

final class FFServiceImpl[F[_]: Monad: ClockSYM: TwitterClientQuerySYM: UuidGenSYM]
    extends FFServiceSYM[F] {
  def create(token: Token): Result[FF] = {
    for {
      now <- ClockSYM[F].currentDate()
      userId <- TwitterClientQuerySYM[F].fetchAuthUserId(token)
      followers <- TwitterClientQuerySYM[F]
        .fetchFollowers(token, userId)
        .map(_.toSet)
      friends <- TwitterClientQuerySYM[F]
        .fetchFriends(token, userId)
        .map(_.toSet)
      ffId <- UuidGenSYM[F].gen()
    } yield FF(
      id = FFId(ffId),
      userId = userId,
      createdAt = now,
      friends = friends,
      followers = followers
    )
  }
}
