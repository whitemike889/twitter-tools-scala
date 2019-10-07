package net.kgtkr.twitter_tools.domain.ports;

import cats.data.EitherT
import net.kgtkr.twitter_tools.domain.models.AppError

sealed trait ConfigLoaderError;
object ConfigLoaderError {
  final case class NotFound() extends ConfigLoaderError
  final case class Parse() extends ConfigLoaderError
  final case class Validation() extends ConfigLoaderError
}

trait ConfigLoader[F[_]] {
  type Result[A] = EitherT[F, AppError[Any, ConfigLoaderError], A]

  /**
    * 設定ファイルを読み込む
    * ファイルが存在しなければNotFound
    * yamlのパースに失敗すればParse
    * バリデーションに失敗すればValidation
    */
  def readConfig(path: String): Result[String]
}

object ConfigLoader {
  def apply[F[_]](implicit x: ConfigLoader[F]): ConfigLoader[F] =
    x
}
