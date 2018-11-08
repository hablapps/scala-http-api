package tv.codely.scala_http_api.application
package user
package repo_publisher

import repositories.UserRepository

final case class UsersSearcherRepo[P[_]]()(
  implicit
  repository: UserRepository[P]
) extends UsersSearcher[P] {
  def all(): P[Seq[User]] = repository.all()
}
