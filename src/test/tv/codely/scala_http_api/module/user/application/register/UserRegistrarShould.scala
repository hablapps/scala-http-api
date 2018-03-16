package tv.codely.scala_http_api.module.user.application.register

import tv.codely.scala_http_api.module.UnitTestCase
import tv.codely.scala_http_api.module.shared.infrastructure.MessagePublisherMock
import tv.codely.scala_http_api.module.user.domain.{UserRegisteredStub, UserStub}
import tv.codely.scala_http_api.module.user.infrastructure.repository.UserRepositoryMock

final class UserRegistrarShould extends UnitTestCase with UserRepositoryMock with MessagePublisherMock {
  private val registrar = new UserRegistrar(repository, messagePublisher)

  "register a user" in {
    val user           = UserStub.random
    val userRegistered = UserRegisteredStub(user)

    repositoryShouldSave(user)

    publisherShouldPublish(userRegistered)

    registrar.register(user.id, user.name).shouldBe(())
  }
}

import tv.codely.scala_http_api.module.user.domain.User
import tv.codely.scala_http_api.module.user.infrastructure.repository.StateUserRepositoryL
import tv.codely.scala_http_api.module.shared.bus.domain.Message
import tv.codely.scala_http_api.module.shared.bus.infrastructure.rabbit_mq.StateMessagePublisherL

final class UserRegistrarLShould extends org.scalatest.WordSpec with org.scalatest.Matchers {
  private val registrar = MockUserRegistrarL

  "register a user" in {
    val user           = UserStub.random
    val userRegistered = UserRegisteredStub(user)

    val initialState = StateUserRegistrarL(
      StateUserRepositoryL(Seq.empty[User]),
      StateMessagePublisherL(Seq.empty[Message]))

    val (resOutput, StateUserRegistrarL(
        StateUserRepositoryL(resUsers),
        StateMessagePublisherL(resMessages))) =
      registrar.register(user.id, user.name)(initialState)

    resOutput shouldBe (())
    resUsers shouldBe Seq(user)
    resMessages shouldBe Seq(userRegistered)

  }
}
