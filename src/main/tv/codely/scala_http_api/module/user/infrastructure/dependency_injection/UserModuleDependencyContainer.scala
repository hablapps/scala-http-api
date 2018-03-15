package tv.codely.scala_http_api.module.user.infrastructure.dependency_injection

import tv.codely.scala_http_api.module.shared.bus.domain.MessagePublisher
import tv.codely.scala_http_api.module.shared.persistence.infrastructure.doobie.DoobieDbConnection
import tv.codely.scala_http_api.module.user.application.register.UserRegistrar
import tv.codely.scala_http_api.module.user.application.search.UsersSearcher
import tv.codely.scala_http_api.module.user.domain.UserRepository
import tv.codely.scala_http_api.module.user.infrastructure.repository.DoobieMySqlUserRepository

import scala.concurrent.{Future, ExecutionContext}
import cats.Id

final class UserModuleDependencyContainer(
    doobieDbConnection: DoobieDbConnection,
    messagePublisher: MessagePublisher[Id]
)(implicit executionContext: ExecutionContext) {
  val repository: UserRepository[Future] = new DoobieMySqlUserRepository(doobieDbConnection)

  val usersSearcher: UsersSearcher = new UsersSearcher(repository)
  val userRegistrar: UserRegistrar = new UserRegistrar(repository, messagePublisher)
}
