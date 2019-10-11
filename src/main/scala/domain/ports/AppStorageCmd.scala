package net.kgtkr.twitter_tools.domain.ports;
import cats.Monad
import cats.data.EitherT

sealed trait AppStorageCmdError;
object AppStorageCmdError {
  final case class NotFound() extends AppStorageCmdError
  final case class AlreadyExists() extends AppStorageCmdError
}

trait AppStorageCmdSYM[F[_]] {
  type Result[A] = EitherT[F, AppStorageCmdError, A]

  /**
    * ファイルに書き込む
    * ファイルが存在しなければ作る
    * ディレクトリが存在しなければNotFound
    */
  def writeFile(
      path: String,
      content: String
  ): Result[Unit]

  /**
    * ファイルまたはディレクトリを削除する
    * 存在しなければNotFound
    * ディレクトリの場合は中身も再帰的に削除される
    */
  def delete(
      path: String,
      content: String
  ): Result[Unit]

  /**
    * ディレクトリを作る
    * 既に存在する場合はAlreadyExists
    * 子ディレクトリが存在しない場合再帰的に作られる
    */
  def mkdir(path: String): Result[Unit]
}

object AppStorageCmdSYM {
  def apply[F[_]](implicit x: AppStorageCmdSYM[F]): AppStorageCmdSYM[F] =
    x
}
