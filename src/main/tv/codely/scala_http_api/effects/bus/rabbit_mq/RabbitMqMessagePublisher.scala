package tv.codely.scala_http_api.effects
package bus
package rabbit_mq

import com.rabbitmq.client.MessageProperties
import com.rabbitmq.client.Channel

import cats.Id

import akkaHttp.system.marshaller.MessageJsonFormatMarshaller.MessageMarshaller

final class RabbitMqMessagePublisher(channel: Channel) extends MessagePublisher[Id] {

  // Use the default nameless exchange in order to route the published messages based on
  // the mapping between the message routing key and the queue names.
  // Example: A message with routing key "codelytv_scala_api.video_created"
  // will be routed to the "codelytv_scala_api.video_created" queue.
  private val exchange = ""

  private def createQueueIfNotExists(name: String) = {
    val availableAfterRestart     = true
    val exclusiveToConnection     = false
    val deleteOnceMessageConsumed = false
    val arguments                 = null

    channel.queueDeclare(name, availableAfterRestart, exclusiveToConnection, deleteOnceMessageConsumed, arguments)
  }

  override def publish(message: Message): Unit = {
    val routingKey    = message.`type`
    val messageJson   = MessageMarshaller.write(message)
    val messageBytes  = messageJson.toString.getBytes
    val persistToDisk = MessageProperties.PERSISTENT_TEXT_PLAIN

    createQueueIfNotExists(name = message.`type`)

    channel.basicPublish(exchange, routingKey, persistToDisk, messageBytes)
  }
}

object RabbitMqMessagePublisher {

  def apply(config: RabbitMqConfig): RabbitMqMessagePublisher = {
    new RabbitMqMessagePublisher(new RabbitMqChannelFactory(config).channel)
  }
}
