package tv.codely.scala_http_api.module.user.application.register

import tv.codely.scala_http_api.module.user.infrastructure.repository.StateUserRepository
import tv.codely.scala_http_api.module.shared.infrastructure.StateMessagePublisher

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
