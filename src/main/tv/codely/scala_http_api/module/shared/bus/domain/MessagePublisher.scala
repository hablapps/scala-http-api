package tv.codely.scala_http_api.module.shared.bus.domain

trait MessagePublisher[P[_]]{
  def publish(message: Message): P[Unit]
}
