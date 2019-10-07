package net.kgtkr.twitter_tools.domain.models

import java.time.OffsetDateTime
import io.circe.Json

sealed trait Raw {
  val id: Long;
  val createdAt: OffsetDateTime;
  val raw: Json
}

final case class UserRaw(id: Long, createdAt: OffsetDateTime, raw: Json)
    extends Raw;

final case class StatusRaw(
    val id: Long,
    val createdAt: OffsetDateTime,
    raw: Json
) extends Raw;
