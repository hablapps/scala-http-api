package tv.codely.scala_http_api.module.user.application.search

import tv.codely.scala_http_api.module.user.domain.{User, UserRepository}

final class UsersSearcherRepo[P[_]](repository: UserRepository[P])
extends UsersSearcher[P] {
  def all(): P[Seq[User]] = repository.all()
}

object UsersSearcherRepo{
  implicit def instance[P[_]](implicit userRepository: UserRepository[P]) =
    new UsersSearcherRepo(userRepository)
}