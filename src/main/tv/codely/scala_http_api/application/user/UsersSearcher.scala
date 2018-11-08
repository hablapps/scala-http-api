package tv.codely.scala_http_api.application.user

trait UsersSearcher[P[_]] {
  def all(): P[Seq[User]]
}
