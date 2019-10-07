package net.kgtkr.twitter_tools.domain.models

import java.time.OffsetDateTime

final case class DiscordPayload(
    content: String,
    embeds: Option[List[DiscordPayload.Embed]] = None
)
object DiscordPayload {
  final case class Embed(
      title: Option[String] = None,
      description: Option[String] = None,
      url: Option[String] = None,
      timestamp: Option[OffsetDateTime] = None,
      color: Option[Color] = None,
      image: Option[Embed.Image] = None,
      fields: Option[List[Embed.Field]] = None
  )
  object Embed {
    final case class Image(url: String)
    final case class Field(name: String, value: String)
  }
}
