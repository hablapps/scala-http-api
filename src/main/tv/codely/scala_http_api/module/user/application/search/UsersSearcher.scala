package tv.codely.scala_http_api.module.user.application.search

import tv.codely.scala_http_api.module.user.domain.{User, UserRepository}

import scala.concurrent.Future

final class UsersSearcher(repository: UserRepository[Future]) {
  def all(): Future[Seq[User]] = repository.all()
}
