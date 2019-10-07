package net.kgtkr.twitter_tools.domain.ports;
import cats.Monad
import cats.data.EitherT
import net.kgtkr.twitter_tools.domain.models.AppError

sealed trait FileQueryError;
object FileQueryError {
  final case class NotFound() extends FileQueryError
}

trait FileQuery[F[_]] {
  type Result[A] = EitherT[F, AppError[Any, FileQueryError], A]

  def readFile(path: String): Result[String]
  def exists(path: String): Result[Boolean]
  def readDir(path: String): Result[List[String]]
}

object FileQuery {
  def apply[F[_]](implicit x: FileQuery[F]): FileQuery[F] =
    x
}
