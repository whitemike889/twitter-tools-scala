package net.kgtkr.twitter_tools.domain.models;

sealed trait AppError[+U, +E];
object AppError {
  final case class Unknown[+U, +E](error: U) extends AppError[U, E];
  final case class Normal[+U, +E](error: E) extends AppError[U, E];
}
