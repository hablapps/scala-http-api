package tv.codely.scala_http_api
package services.mock.user

import tv.codely.scala_http_api.effects.bus.mock.MessagePublisherMock
import tv.codely.scala_http_api.application.mock.UnitTestCase
import tv.codely.scala_http_api.application.stubs.user.{UserRegisteredStub, UserStub}
import tv.codely.scala_http_api.application.repositories.mock.UserRepositoryMock
import tv.codely.scala_http_api.application.user.repo_publisher.UserRegisterRepoPublisher
import scala.concurrent.{ExecutionContext, Future}, ExecutionContext.Implicits.global
import cats.instances.future._

final class UserRegisterShould extends UnitTestCase with UserRepositoryMock with MessagePublisherMock {
  private val registrar = UserRegisterRepoPublisher[Future]()(repository, messagePublisher, implicitly)

  "register a user" in {
    val user           = UserStub.random
    val userRegistered = UserRegisteredStub(user)

    repositoryShouldSave(user)

    publisherShouldPublish(userRegistered)

    registrar.register(user.id, user.name).map(_.shouldBe(()))
  }
}
