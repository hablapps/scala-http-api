package tv.codely.scala_http_api.module.user.application.search

import tv.codely.scala_http_api.module.user.domain.{User, UserRepository}

import scala.concurrent.Future

// TODO(jfuentes): Is this an unnecessary indirection?
final class UsersSearcher(repository: UserRepository) {
  def all(): Future[Seq[User]] = repository.all()
}

import tv.codely.scala_http_api.module.user.infrastructure.repository.StateUserRepositoryL
import tv.codely.scala_http_api.module.user.infrastructure.repository.MockUserRepositoryL

trait UsersSearcherL[P[_]] {
  def all: P[Seq[User]]
}

object MockUsersSearcher extends UsersSearcherL[λ[α => StateUserRepositoryL => (α, StateUserRepositoryL)]] {
  private val userRepository = MockUserRepositoryL.apply
  def all: StateUserRepositoryL => (Seq[User], StateUserRepositoryL) = userRepository.all
}
