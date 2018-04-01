package tv.codely.scala_http_api.services.api.user

trait UsersSearcher[P[_]]{
  def all(): P[Seq[User]]
}
