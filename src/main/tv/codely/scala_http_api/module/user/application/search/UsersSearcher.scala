package tv.codely.scala_http_api.module.user.application.search

import tv.codely.scala_http_api.module.user.domain.{User, UserRepository}

import scala.concurrent.Future

// TODO(jfuentes): Is this an unnecessary indirection?
final class UsersSearcher(repository: UserRepository) {
  def all(): Future[Seq[User]] = repository.all()
}