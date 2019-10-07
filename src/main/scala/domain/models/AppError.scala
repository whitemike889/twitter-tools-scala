package net.kgtkr.twitter_tools.domain.models;

sealed trait AppError[+U, +E];
object AppError {
  final case class Unknown[+U](error: U) extends AppError[U, Nothing];
  final case class Normal[+E](error: E) extends AppError[Nothing, E];
}
