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

object FileQuery {
  def apply[F[_]](implicit x: FileQuery[F]): FileQuery[F] =
    x
}
