package tv.codely.scala_http_api.application.api.user

trait UsersSearcher[P[_]]{
  def all(): P[Seq[User]]
}
