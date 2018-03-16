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

import tv.codely.scala_http_api.State
import tv.codely.scala_http_api.module.user.domain.User
import tv.codely.scala_http_api.module.user.domain.UserRepositoryL.StateUserRepositoryL
import tv.codely.scala_http_api.module.shared.bus.domain.Message
import tv.codely.scala_http_api.module.shared.bus.domain.MessagePublisherL.StateMessagePublisherL
import tv.codely.scala_http_api.module.user.application.register.UserRegistrarL.StateUserRegistrarL

final class UserRegistrarLShould extends org.scalatest.WordSpec with org.scalatest.Matchers {
  private val registrar = UserRegistrarL.instance[State[StateUserRegistrarL, ?]]

  "register a user" in {
    val user           = UserStub.random
    val userRegistered = UserRegisteredStub(user)

    val initialState = StateUserRegistrarL(
      StateUserRepositoryL(Seq.empty[User]),
      StateMessagePublisherL(Seq.empty[Message]))

    val (StateUserRegistrarL(
        StateUserRepositoryL(resUsers),
        StateMessagePublisherL(resMessages)), resOutput) =
      registrar.register(user.id, user.name).run(initialState)

    resOutput shouldBe (())
    resUsers shouldBe Seq(user)
    resMessages shouldBe Seq(userRegistered)

  }
}
