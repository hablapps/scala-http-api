package tv.codely.scala_http_api.module.user.application.register

import tv.codely.scala_http_api.module.shared.bus.domain.MessagePublisher
import tv.codely.scala_http_api.module.user.infrastructure.repository.StateUserRepositoryL
import tv.codely.scala_http_api.module.shared.infrastructure.StateMessagePublisherL
import tv.codely.scala_http_api.module.shared.user.domain._
import tv.codely.scala_http_api.module.user.domain._

case class StateUserRegisterL(
  userRepository: StateUserRepositoryL,
  messagePublisher: StateMessagePublisherL)

object StateUserRegisterL{

  import tv.codely.scala_http_api.Lens

  implicit val userLens = Lens[StateUserRegisterL, StateUserRepositoryL](
    _.userRepository,
    ur => s => s.copy(userRepository = ur))

  implicit val msgLens = Lens[StateUserRegisterL, StateMessagePublisherL](
    _.messagePublisher,
    mp => s => s.copy(messagePublisher = mp))
}
