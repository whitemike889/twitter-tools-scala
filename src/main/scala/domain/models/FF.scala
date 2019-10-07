package net.kgtkr.twitter_tools.domain.models
import java.time.OffsetDateTime

final case class FF(
    id: Long,
    userId: Long,
    createdAt: OffsetDateTime,
    friends: Set[Long],
    followers: Set[Long]
)
