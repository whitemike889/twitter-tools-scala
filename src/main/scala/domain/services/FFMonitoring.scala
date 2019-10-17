package net.kgtkr.twitter_tools.domain.services;

import cats.Monad
import net.kgtkr.twitter_tools.domain.ports.ClockSYM
import net.kgtkr.twitter_tools.domain.ports.TwitterClientQuerySYM
import net.kgtkr.twitter_tools.domain.models.Token
import cats.implicits._
import cats.data.ReaderT
import scala.util.chaining._
import net.kgtkr.twitter_tools.domain.models.FF
import net.kgtkr.twitter_tools.domain.ports.UuidGenSYM
import net.kgtkr.twitter_tools.domain.ports.FFRepositoryCmdSYM
import net.kgtkr.twitter_tools.domain.ports.FFRepositoryQuerySYM
import net.kgtkr.twitter_tools.domain.models.FFMonitoringAccount
import net.kgtkr.twitter_tools.utils._

trait FFMonitoringSYM[F[_]] {
  type Result[A] = F[A]

  def monitoring(account: FFMonitoringAccount): Result[Unit]
}

object FFMonitoringSYM {
  def apply[F[_]](implicit x: FFMonitoringSYM[F]): FFMonitoringSYM[F] =
    x
}

final class FFMonitoringImpl[F[_]: Monad: FFServiceSYM: FFRepositoryCmdSYM: FFRepositoryQuerySYM: TwitterClientQuerySYM: UserCacheSYM: DiscordHookUsersSYM]
    extends FFMonitoringSYM[F] {
  def monitoring(account: FFMonitoringAccount): Result[Unit] = {
    val FFMonitoringAccount(token, hookUrl) = account
    for {
      ff <- FFServiceSYM[F].create().run(token)
      userId <- TwitterClientQuerySYM[F].fetchAuthUserId().run(token)
      oldFFs <- FFRepositoryQuerySYM[F].findUser(userId, 1)
      _ <- FFRepositoryCmdSYM[F].insert(ff)
      _ <- (oldFFs match {
        case List(oldFF) => {
          val welcomeFollowers = ff.followers.diff(oldFF.followers);
          val welcomeFriends = ff.friends.diff(oldFF.friends)
          val byeFollowers = oldFF.followers.diff(ff.followers);
          val byeFriends = oldFF.friends.diff(ff.friends)

          val requireUserIds = welcomeFollowers union welcomeFriends union byeFollowers union byeFriends

          if (!requireUserIds.isEmpty) {
            for {
              userMap <- UserCacheSYM[F]
                .lookupUsers(requireUserIds)
                .toReader
                .run(token)
                .pipe(runId)
              _ <- DiscordHookUsersSYM[F]
                .postUsers(
                  hookUrl,
                  "新しいフォロワー",
                  welcomeFollowers
                    .map(userMap.get(_).getOrElse(unreachable))
                    .toList
                )
              _ <- DiscordHookUsersSYM[F]
                .postUsers(
                  hookUrl,
                  "新しいフォロー",
                  welcomeFriends
                    .map(userMap.get(_).getOrElse(unreachable))
                    .toList
                )
              _ <- DiscordHookUsersSYM[F]
                .postUsers(
                  hookUrl,
                  "去ったフォロワー",
                  byeFollowers
                    .map(userMap.get(_).getOrElse(unreachable))
                    .toList
                )
              _ <- DiscordHookUsersSYM[F]
                .postUsers(
                  hookUrl,
                  "去ったフォロー",
                  byeFriends
                    .map(userMap.get(_).getOrElse(unreachable))
                    .toList
                )
            } yield ()
          } else {
            Monad[Result].pure(())
          }
        }
        case _ => {
          Monad[Result].pure(())
        }
      })
    } yield ()
  }
}
