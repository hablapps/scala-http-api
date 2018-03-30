package tv.codely.scala_http_api.module.shared.bus.domain

trait MessagePublisher[P[_]]{
  def publish(message: Message): P[Unit]
}

object MessagePublisher{

  import cats.~>

  implicit def toFuture[P[_], Q[_]](implicit 
    P: MessagePublisher[P],
    nat: P ~> Q): MessagePublisher[Q] = new MessagePublisher[Q]{
      def publish(message: Message): Q[Unit] =
        nat(P.publish(message))
  }

  implicit def toFutureView[P[_], Q[_]](P: MessagePublisher[P])(implicit 
    nat: P ~> Q): MessagePublisher[Q] = toFuture(P, nat)

}
