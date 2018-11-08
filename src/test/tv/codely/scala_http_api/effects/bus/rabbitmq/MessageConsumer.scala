package tv.codely.scala_http_api.effects.bus
package rabbitmq

trait MessageConsumer {
  def startConsuming(handler: Message => Boolean): Unit
  def hasMessagesToConsume: Boolean
  def isEmpty: Boolean = !hasMessagesToConsume
}
