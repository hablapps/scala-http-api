package tv.codely.scala_http_api.module.user.application.search

import tv.codely.scala_http_api.module.user.domain.{User, UserRepository}

import scala.concurrent.Future

// TODO(jfuentes): Is this an unnecessary indirection?
final class UsersSearcher(repository: UserRepository) {
  def all(): Future[Seq[User]] = repository.all()
}

import tv.codely.scala_http_api.State
import tv.codely.scala_http_api.module.user.infrastructure.repository.StateUserRepositoryL
import tv.codely.scala_http_api.module.user.infrastructure.repository.MockUserRepositoryL

trait UsersSearcherL[P[_]] {
  def all: P[Seq[User]]
}

object MockUsersSearcher extends UsersSearcherL[State[StateUserRepositoryL, ?]] {
  private val userRepository = MockUserRepositoryL.forState
  def all: State[StateUserRepositoryL, Seq[User]] = userRepository.all
}
