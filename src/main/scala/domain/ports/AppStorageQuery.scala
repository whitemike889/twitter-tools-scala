package net.kgtkr.twitter_tools.domain.ports;
import cats.Monad
import cats.data.EitherT
import net.kgtkr.twitter_tools.domain.models.AppError

sealed trait AppStorageQueryError;
object AppStorageQueryError {
  final case class NotFound() extends AppStorageQueryError
}

trait AppStorageQuerySYM[F[_]] {
  type Result[A] = EitherT[F, AppError[Any, AppStorageQueryError], A]

  /**
    * ファイルを読み込む
    * ファイルが存在しない、または指定されたパスがディレクトリならNotFound
    */
  def readFile(path: String): Result[String]

  /**
    * ファイルまたはディレクトリが存在するか確認する
    */
  def exists(path: String): Result[Boolean]

  /**
    * 特定ディレクトリの子ディレクトリまたは子ファイルの名前一覧を返す
    * ディレクトリが存在しない、または指定されたパスがファイルならNotFound
    *
    */
  def readDir(path: String): Result[List[String]]
}

object AppStorageQuerySYM {
  def apply[F[_]](implicit x: AppStorageQuerySYM[F]): AppStorageQuerySYM[F] =
    x
}
