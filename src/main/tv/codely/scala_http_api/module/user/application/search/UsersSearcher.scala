package tv.codely.scala_http_api.module.user.application.search

import tv.codely.scala_http_api.module.user.domain.{User, UserRepository}

final class UsersSearcher[P[_]](repository: UserRepository[P]) {
  def all(): P[Seq[User]] = repository.all()
}
