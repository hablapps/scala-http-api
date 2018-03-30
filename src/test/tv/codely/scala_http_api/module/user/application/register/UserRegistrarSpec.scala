package tv.codely.scala_http_api.module.user.application.register

import tv.codely.scala_http_api.State
import tv.codely.scala_http_api.module.user.domain.User
import tv.codely.scala_http_api.module.user.infrastructure.repository.StateUserRepository, StateUserRepository._
import tv.codely.scala_http_api.module.shared.bus.domain.Message
import tv.codely.scala_http_api.module.shared.infrastructure.StateMessagePublisher, StateMessagePublisher._
import tv.codely.scala_http_api.module.user.domain.{UserRegisteredStub, UserStub}

final class UserRegisterSpec extends org.scalatest.WordSpec with org.scalatest.Matchers {
  private val registrar = UserRegisterRepoPublisher.apply[State[StateUserRegister, ?]]

  "register a user" in {
    val user           = UserStub.random
    val userRegistered = UserRegisteredStub(user)

    val initialState = StateUserRegister(
      StateUserRepository(Seq.empty[User]),
      StateMessagePublisher(Seq.empty[Message]))

    val (StateUserRegister(
        StateUserRepository(resUsers),
        StateMessagePublisher(resMessages)), resOutput) =
      registrar.register(user.id, user.name).run(initialState)

    resOutput shouldBe (())
    resUsers shouldBe Seq(user)
    resMessages shouldBe Seq(userRegistered)

  }
}
