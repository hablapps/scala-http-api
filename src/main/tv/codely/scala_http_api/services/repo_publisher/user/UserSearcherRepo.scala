package tv.codely.scala_http_api.services.repo_publisher.user

import tv.codely.scala_http_api.services.api.user._
import tv.codely.scala_http_api.effects.repositories.api.{User, UserRepository}

final case class UsersSearcherRepo[P[_]]()(implicit 
  repository: UserRepository[P])
extends UsersSearcher[P] {
  def all(): P[Seq[User]] = repository.all()
}