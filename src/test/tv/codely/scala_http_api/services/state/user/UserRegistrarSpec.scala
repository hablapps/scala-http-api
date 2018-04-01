package tv.codely.scala_http_api.services.state.user

import tv.codely.scala_http_api.State
import tv.codely.scala_http_api.services.api.user.User
import tv.codely.scala_http_api.effects.repositories.state.StateUserRepository, StateUserRepository._
import tv.codely.scala_http_api.effects.bus.api.Message
import tv.codely.scala_http_api.effects.bus.state._, StateMessagePublisher._
import tv.codely.scala_http_api.services.stubs.user.{UserRegisteredStub, UserStub}
import tv.codely.scala_http_api.services.repo_publisher.user.UserRegisterRepoPublisher

final class UserRegisterSpec extends org.scalatest.WordSpec with org.scalatest.Matchers {
  private val registrar = UserRegisterRepoPublisher[State[StateUserRegister, ?]]

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
