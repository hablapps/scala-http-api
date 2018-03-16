package tv.codely.scala_http_api.module.shared.bus.domain

trait MessagePublisher {
  def publish[T <: Message](message: T): Unit
}

import tv.codely.scala_http_api.State
import tv.codely.scala_http_api.Lens

trait MessagePublisherL[P[_]] {
  def publish[T <: Message](message: T): P[Unit]
}

object MessagePublisherL {
  case class StateMessagePublisherL(published: Seq[Message])

  val forState = new MessagePublisherL[State[StateMessagePublisherL, ?]] {
    def publish[T <: Message](message: T): State[StateMessagePublisherL, Unit] = State {
      case StateMessagePublisherL(published) => (StateMessagePublisherL(message +: published), ())
    }
  }

  implicit def messagePublisherfromLens[S](implicit lens: Lens[S, StateMessagePublisherL]): MessagePublisherL[State[S, ?]] =
    new MessagePublisherL[State[S, ?]] {
      def publish[T <: Message](message: T): State[S, Unit] = forState.publish(message).lift
    }

}
