package tv.codely.scala_http_api
package module.shared.infrastructure

import tv.codely.scala_http_api.module.shared.bus.domain._

case class StateMessagePublisherL(published: Seq[Message])

object StateMessagePublisherL {

  val forState = new MessagePublisher[State[StateMessagePublisherL, ?]] {
    def publish(message: Message): State[StateMessagePublisherL, Unit] = State {
      case StateMessagePublisherL(published) => (StateMessagePublisherL(message +: published), ())
    }
  }

  implicit def messagePublisherfromLens[S](implicit lens: Lens[S, StateMessagePublisherL]): MessagePublisher[State[S, ?]] =
    new MessagePublisher[State[S, ?]] {
      def publish(message: Message): State[S, Unit] = forState.publish(message).lift
    }

}