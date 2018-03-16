package tv.codely.scala_http_api.module.user.application.search

import tv.codely.scala_http_api.module.user.domain.{User, UserRepository}

import scala.concurrent.Future

// TODO(jfuentes): Is this an unnecessary indirection?
final class UsersSearcher(repository: UserRepository) {
  def all(): Future[Seq[User]] = repository.all()
}

import tv.codely.scala_http_api.module.user.domain.UserRepositoryL

trait UsersSearcherL[P[_]] {
  def all: P[Seq[User]]
}

object UsersSearcherL {
  implicit def instance[P[_]](implicit userRepository: UserRepositoryL[P]) =
    new UsersSearcherL[P] {
      def all: P[Seq[User]] = userRepository.all
    }
}
