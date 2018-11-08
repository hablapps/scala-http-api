package tv.codely.scala_http_api.application.repo_publisher.user

import tv.codely.scala_http_api.application.user.User
import tv.codely.scala_http_api.application.user.UsersSearcher
import tv.codely.scala_http_api.application.repositories.UserRepository

final case class UsersSearcherRepo[P[_]]()(
  implicit
  repository: UserRepository[P]
) extends UsersSearcher[P] {
  def all(): P[Seq[User]] = repository.all()
}
