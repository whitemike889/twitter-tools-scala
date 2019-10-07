package net.kgtkr.twitter_tools.domain.ports;

import cats.data.EitherT
import net.kgtkr.twitter_tools.domain.models.AppError

sealed trait ConfigQueryError;
object ConfigQueryError {
  final case class NotFound() extends ConfigQueryError
  final case class Parse() extends ConfigQueryError
  final case class Validation() extends ConfigQueryError
}

trait ConfigQuery[F[_]] {
  type Result[A] = EitherT[F, AppError[Any, ConfigQueryError], A]

  /**
    * 設定ファイルを読み込む
    * ファイルが存在しなければNotFound
    * yamlのパースに失敗すればParse
    * バリデーションに失敗すればValidation
    */
  def readConfig(path: String): Result[String]
}

object ConfigQuery {
  def apply[F[_]](implicit x: ConfigQuery[F]): ConfigQuery[F] =
    x
}
