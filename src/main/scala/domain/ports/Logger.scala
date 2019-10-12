package net.kgtkr.twitter_tools.domain.ports;

sealed trait LogLevel;
object LogLevel {
  final case class Error() extends LogLevel
  final case class Warn() extends LogLevel
  final case class Info() extends LogLevel
  final case class Debug() extends LogLevel
  final case class Trace() extends LogLevel
}

trait LoggerSYM[F[_]] {
  type Result[T] = F[T]

  def log(level: LogLevel, msg: String): Result[Unit]
}

object LoggerSYM {
  def apply[F[_]](implicit x: LoggerSYM[F]): LoggerSYM[F] =
    x
}
