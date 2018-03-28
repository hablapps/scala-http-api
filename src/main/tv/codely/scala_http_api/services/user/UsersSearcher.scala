package tv.codely.scala_http_api.module.user.application.search

import tv.codely.scala_http_api.module.user.domain.User

trait UsersSearcher[P[_]]{
  def all(): P[Seq[User]]
}
