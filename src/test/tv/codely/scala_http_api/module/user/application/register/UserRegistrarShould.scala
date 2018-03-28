package tv.codely.scala_http_api
package module
package user.application.register

import tv.codely.scala_http_api.module.UnitTestCase
import tv.codely.scala_http_api.module.shared.infrastructure.MessagePublisherMock
import tv.codely.scala_http_api.module.user.domain.{UserRegisteredStub, UserStub}
import tv.codely.scala_http_api.module.user.infrastructure.repository.UserRepositoryMock
import scala.concurrent.{Future, ExecutionContext}, ExecutionContext.Implicits.global
import cats.instances.future._

final class UserRegisterShould extends UnitTestCase with UserRepositoryMock with MessagePublisherMock{
  private val registrar = new UserRegister[Future](repository, messagePublisher)

  "register a user" in {
    val user           = UserStub.random
    val userRegistered = UserRegisteredStub(user)

    repositoryShouldSave(user)

    publisherShouldPublish(userRegistered)

    registrar.register(user.id, user.name).map(_.shouldBe(()))
  }
}
