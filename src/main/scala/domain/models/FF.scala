package net.kgtkr.twitter_tools.domain.models
import java.time.OffsetDateTime

final case class FFId(value: String) extends AnyVal

final case class FF(
    id: FFId,
    userId: UserId,
    createdAt: OffsetDateTime,
    friends: Set[UserId],
    followers: Set[UserId]
)
