package net.kgtkr.twitter_tools.domain.services;

import cats.data.ReaderT
import net.kgtkr.twitter_tools.domain.models.Token
import net.kgtkr.twitter_tools.domain.models.FF
import cats.Monad
import net.kgtkr.twitter_tools.domain.ports.ClockSYM
import net.kgtkr.twitter_tools.domain.ports.TwitterClientQuerySYM
import net.kgtkr.twitter_tools.domain.ports.UuidGenSYM
import scala.util.chaining._
import cats.implicits._
import net.kgtkr.twitter_tools.domain.models.FFId

trait FFServiceSYM[F[_]] {
  type Result[A] = ReaderT[F, Token, A]

  def create(): Result[FF]
}

object FFServiceSYM {
  def apply[F[_]](implicit x: FFServiceSYM[F]): FFServiceSYM[F] =
    x
}

final class FFServiceImpl[F[_]: Monad: ClockSYM: TwitterClientQuerySYM: UuidGenSYM]
    extends FFServiceSYM[F] {
  def create(): Result[FF] = {
    for {
      now <- ClockSYM[F].currentDate().pipe(ReaderT.liftF)
      userId <- TwitterClientQuerySYM[F].fetchAuthUserId()
      followers <- TwitterClientQuerySYM[F]
        .fetchFollowers(userId)
        .map(_.toSet)
      friends <- TwitterClientQuerySYM[F].fetchFriends(userId).map(_.toSet)
      ffId <- UuidGenSYM[F].gen().pipe(ReaderT.liftF)
    } yield FF(
      id = FFId(ffId),
      userId = userId,
      createdAt = now,
      friends = friends,
      followers = followers
    )
  }
}
