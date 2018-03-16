package tv.codely.scala_http_api.module.shared.bus.domain

trait MessagePublisher {
  def publish[T <: Message](message: T): Unit
}

trait MessagePublisherL[P[_]] {
  def publish[T <: Message](message: T): P[Unit]
}
