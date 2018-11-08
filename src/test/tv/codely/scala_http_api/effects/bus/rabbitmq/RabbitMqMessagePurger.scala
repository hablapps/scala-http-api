package tv.codely.scala_http_api.effects.bus
package rabbitmq

import rabbit_mq.RabbitMqChannelFactory

final class RabbitMqMessagePurger(channelFactory: RabbitMqChannelFactory)(queueName: String) extends MessagePurger {
  private val channel = channelFactory.channel

  override def purgeQueue(): Unit = channel.queuePurge(queueName)
}
