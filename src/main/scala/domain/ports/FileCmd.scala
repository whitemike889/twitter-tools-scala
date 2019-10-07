package net.kgtkr.twitter_tools.domain.ports;
import cats.Monad
import cats.data.EitherT
import net.kgtkr.twitter_tools.domain.models.AppError

sealed trait FileCmdError;
object FileCmdError {
  final case class NotFound() extends FileCmdError
  final case class AlreadyExists() extends FileCmdError
}

trait FileCmd[F[_]] {
  type Result[A] = EitherT[F, AppError[Any, FileCmdError], A]

  def writeFile(
      path: String,
      content: String
  ): Result[Unit]
  def delete(
      path: String,
      content: String
  ): Result[Unit]
  def mkdir(path: String): Result[Unit]
}

object FileCmd {
  def apply[F[_]](implicit x: FileCmd[F]): FileCmd[F] =
    x
}
