package net.kgtkr.twitter_tools.domain.ports;

import cats.data.EitherT
import net.kgtkr.twitter_tools.domain.models.AppError

sealed trait LogLevel;
object LogLevel {
  final case class Error() extends LogLevel
  final case class Warn() extends LogLevel
  final case class Info() extends LogLevel
  final case class Debug() extends LogLevel
  final case class Trace() extends LogLevel
}

sealed trait LoggerError;
object LoggerError {}

trait Logger[F[_]] {
  type Result[A] = EitherT[F, AppError[Any, LoggerError], A]

  def log(level: LogLevel, msg: String): Result[Unit]
}

object Logger {
  def apply[F[_]](implicit x: Logger[F]): Logger[F] =
    x
}
