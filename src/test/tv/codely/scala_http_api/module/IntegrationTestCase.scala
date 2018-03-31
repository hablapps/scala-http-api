package tv.codely.scala_http_api.module

import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory
import tv.codely.scala_http_api.module.shared.bus.infrastructure.rabbit_mq.{RabbitMqConfig, RabbitMqChannelFactory, RabbitMqMessagePublisher}
import tv.codely.scala_http_api.module.shared.logger.infrastructure.scala_logging.ScalaLoggingLogger
import tv.codely.scala_http_api.module.shared.persistence.infrastructure.doobie.{DoobieDbConnection, JdbcConfig}

protected[scala_http_api] trait IntegrationTestCase extends UnitTestCase {
  
  // Read configs

  private val actorSystemName = "scala-http-api-integration-test"
  private val appConfig    = ConfigFactory.load("application")
  private val dbConfig        = JdbcConfig(appConfig.getConfig("database"))
  private val publisherConfig = RabbitMqConfig(appConfig.getConfig("message-publisher"))

  // Inject dependencies

  protected implicit val actorSystem = ActorSystem(actorSystemName)
  protected implicit val executionContext = actorSystem.dispatcher
  protected implicit val doobieDbConnection = new DoobieDbConnection[cats.effect.IO](dbConfig)
  protected implicit val rabbitMqChannelFactory = new RabbitMqChannelFactory(publisherConfig)
  protected implicit val messagePublisher = new RabbitMqMessagePublisher(rabbitMqChannelFactory.channel)
  protected implicit val logger = new ScalaLoggingLogger
}
