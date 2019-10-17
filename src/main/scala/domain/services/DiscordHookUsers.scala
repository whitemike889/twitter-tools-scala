package net.kgtkr.twitter_tools.domain.services;

import cats.data._
import net.kgtkr.twitter_tools.domain.models.UserRaw
import cats.data.ReaderT
import net.kgtkr.twitter_tools.domain.ports.DiscordHookSYM
import net.kgtkr.twitter_tools.domain.models.DiscordPayload

trait DiscordHookUsersSYM[F[_]] {
  type Result[T] = ReaderT[F, String, T]

  def postUsers(
      content: String,
      users: List[UserRaw]
  ): Result[Unit]
}

object DiscordHookUsersSYM {
  def apply[F[_]](implicit x: DiscordHookUsersSYM[F]): DiscordHookUsersSYM[F] =
    x
}

final class DiscordHookUsersImpl[F[_]: DiscordHookSYM]
    extends DiscordHookUsersSYM[F] {

  override def postUsers(
      content: String,
      users: List[UserRaw]
  ): Result[Unit] = {
    DiscordHookSYM[F].post(
      DiscordPayload(
        content,
        Some(users.map(DiscordHookUsersImpl.userToEmbed))
      )
    )
  }
}

object DiscordHookUsersImpl {
  def userToEmbed(user: UserRaw): DiscordPayload.Embed = {
    (for {
      userJson <- user.raw.asObject
      id <- userJson("id").flatMap(_.asNumber).flatMap(_.toLong)
      name <- userJson("name").flatMap(_.asString)
      screenName <- userJson("screen_name").flatMap(_.asString)
      description <- userJson("description").flatMap(_.asString)
      followersCount <- userJson("followers_count")
        .flatMap(_.asNumber)
        .flatMap(_.toInt)
      friendsCount <- userJson("friends_count")
        .flatMap(_.asNumber)
        .flatMap(_.toInt)
      favouritesCount <- userJson("favourites_count")
        .flatMap(_.asNumber)
        .flatMap(_.toInt)
      statusesCount <- userJson("favourites_count")
        .flatMap(_.asNumber)
        .flatMap(_.toInt)
      profileImageUrlHttps <- userJson("profile_image_url_https").flatMap(
        _.asString
      )
    } yield DiscordPayload.Embed(
      title = Some(s"${id.toString}@${screenName}"),
      url = Some(s"https://twitter.com/intent/user?user_id=${id.toString()}"),
      image = Some(DiscordPayload.Embed.Image(url = profileImageUrlHttps)),
      fields = Some(
        List(
          DiscordPayload.Embed.Field(name = "id", value = id.toString()),
          DiscordPayload.Embed.Field(name = "名前", value = name),
          DiscordPayload.Embed
            .Field(name = "スクリーンネーム", value = s"@${screenName}"),
          DiscordPayload.Embed
            .Field(name = "ツイート数", value = statusesCount.toString()),
          DiscordPayload.Embed
            .Field(name = "フォロー数", value = friendsCount.toString()),
          DiscordPayload.Embed
            .Field(name = "フォロワー数", value = followersCount.toString()),
          DiscordPayload.Embed
            .Field(name = "ファボ数", value = favouritesCount.toString()),
          DiscordPayload.Embed.Field(name = "bio", value = description)
        )
      )
    )).getOrElse(
      DiscordPayload.Embed(
        title = Some(s"不明なユーザー(${user.id.value.toString()})"),
        url = Some(
          s"https://twitter.com/intent/user?user_id=${user.id.value.toString()}"
        )
      )
    )
  }
}
