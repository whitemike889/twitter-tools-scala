package net.kgtkr.twitter_tools.domain.models

import java.time.OffsetDateTime
import io.circe.Json

sealed trait Raw[I] {
  val id: I;
  val createdAt: OffsetDateTime;
  val raw: Json
}

final case class UserRaw(id: UserId, createdAt: OffsetDateTime, raw: Json)
    extends Raw[UserId];

final case class StatusRaw(
    id: StatusId,
    createdAt: OffsetDateTime,
    raw: Json
) extends Raw[StatusId];
