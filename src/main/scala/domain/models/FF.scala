package net.kgtkr.twitter_tools.domain.models
import java.time.OffsetDateTime
import java.util.UUID

final case class FFId(value: UUID) extends AnyVal

final case class FF(
    id: FFId,
    userId: UserId,
    createdAt: OffsetDateTime,
    friends: Set[UserId],
    followers: Set[UserId]
)
