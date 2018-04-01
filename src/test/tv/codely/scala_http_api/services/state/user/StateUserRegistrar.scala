package tv.codely.scala_http_api.services.api.user

import tv.codely.scala_http_api.effects.repositories.doobie.StateUserRepository
import tv.codely.scala_http_api.effects.bus.state.StateMessagePublisher

case class StateUserRegister(
  userRepository: StateUserRepository,
  messagePublisher: StateMessagePublisher)

object StateUserRegister{

  import tv.codely.scala_http_api.Lens

  implicit val userLens = Lens[StateUserRegister, StateUserRepository](
    _.userRepository,
    ur => s => s.copy(userRepository = ur))

  implicit val msgLens = Lens[StateUserRegister, StateMessagePublisher](
    _.messagePublisher,
    mp => s => s.copy(messagePublisher = mp))
}
