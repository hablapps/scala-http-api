package tv.codely.scala_http_api.module.user.application.register

import tv.codely.scala_http_api.State
import tv.codely.scala_http_api.module.user.domain.User
import tv.codely.scala_http_api.module.user.infrastructure.repository.StateUserRepositoryL, StateUserRepositoryL._
import tv.codely.scala_http_api.module.shared.bus.domain.Message
import tv.codely.scala_http_api.module.shared.infrastructure.StateMessagePublisherL, StateMessagePublisherL._
import tv.codely.scala_http_api.module.user.domain.{UserRegisteredStub, UserStub}

final class UserRegistrarSpec extends org.scalatest.WordSpec with org.scalatest.Matchers {
  private val registrar = UserRegistrar.instance[State[StateUserRegistrarL, ?]]

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
