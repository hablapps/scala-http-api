package tv.codely.scala_http_api
package effects.bus.state

import tv.codely.scala_http_api.effects.bus.api._

case class StateMessagePublisher(published: Seq[Message])

object StateMessagePublisher {

  val forState = new MessagePublisher[State[StateMessagePublisher, ?]] {
    def publish(message: Message): State[StateMessagePublisher, Unit] = State {
      case StateMessagePublisher(published) => (StateMessagePublisher(message +: published), ())
    }
  }

  implicit def messagePublisherfromLens[S](implicit lens: Lens[S, StateMessagePublisher]): MessagePublisher[State[S, ?]] =
    new MessagePublisher[State[S, ?]] {
      def publish(message: Message): State[S, Unit] = forState.publish(message).lift
    }

}