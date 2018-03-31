package tv.codely
package scala_http_api
package entry_point

import scala.concurrent.Future
import cats.instances.future._

import akka.actor.ActorSystem

import com.typesafe.config.ConfigFactory

import tv.codely.scala_http_api.module.shared.bus.infrastructure.rabbit_mq.{RabbitMqConfig, RabbitMqMessagePublisher}
import tv.codely.scala_http_api.module.shared.persistence.infrastructure.doobie.{DoobieDbConnection, JdbcConfig}
import tv.codely.scala_http_api.module.user.infrastructure.repository.DoobieMySqlUserRepository
import tv.codely.scala_http_api.module.video.infrastructure.repository.DoobieMySqlVideoRepository

object ScalaHttpApi {
  def main(args: Array[String]): Unit = {
    
    // Read configs

    val appConfig    = ConfigFactory.load("application")
    val httpServerConfig = HttpServerConfig(ConfigFactory.load("http-server"))
    val dbConfig        = JdbcConfig(appConfig.getConfig("database"))
    val publisherConfig = RabbitMqConfig(appConfig.getConfig("message-publisher"))
    val actorSystemName = appConfig.getString("main-actor-system.name")

    // Inject dependencies

    implicit val actorSystem = ActorSystem(actorSystemName)
    implicit val executionContext = actorSystem.dispatcher
    implicit val doobieDbConnection = new DoobieDbConnection[cats.effect.IO](dbConfig)
    implicit val doobieUserRepo = DoobieMySqlUserRepository.apply
    implicit val doobieVideoRepo = DoobieMySqlVideoRepository.apply
    implicit val rabbitMqPublisher = RabbitMqMessagePublisher(publisherConfig)
    implicit val doobieRabbitMqSystem = module.SystemRepoPublisher.apply[Future]
    val akkaHttpSystem = SystemController.apply

    // Run system

    akkaHttpSystem.run(httpServerConfig)
  }
}
