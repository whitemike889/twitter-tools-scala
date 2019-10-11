package net.kgtkr.twitter_tools.domain.ports;

import net.kgtkr.twitter_tools.domain.models.FF
import net.kgtkr.twitter_tools.domain.models.UserId

trait FFRepositoryQuerySYM[F[_]] {
  def findUser(
      userId: UserId,
      limit: Int
  ): F[List[FF]]
}

object FFRepositoryQuerySYM {
  def apply[F[_]](
      implicit x: FFRepositoryQuerySYM[F]
  ): FFRepositoryQuerySYM[F] =
    x
}
