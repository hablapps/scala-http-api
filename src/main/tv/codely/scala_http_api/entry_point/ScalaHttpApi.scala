package tv.codely.scala_http_api
package entry_point

import scala.concurrent.Future
import cats.instances.future._
import cats.effect.IO

import akka.actor.ActorSystem

import com.typesafe.config.ConfigFactory

import application.repositories.doobieImpl.{DoobieDbConnection, JdbcConfig, DoobieMySqlUserRepository, DoobieMySqlVideoRepository}
import application.repo_publisher.SystemRepoPublisher
import effects.bus.rabbit_mq.{RabbitMqConfig, RabbitMqMessagePublisher}
import effects.akkaHttp.system.{HttpServerConfig, SystemController}

object ScalaHttpApi {
  def main(args: Array[String]): Unit = {

    // Read configs

    val appConfig        = ConfigFactory.load("application")
    val httpServerConfig = HttpServerConfig(ConfigFactory.load("http-server"))
    val dbConfig         = JdbcConfig(appConfig.getConfig("database"))
    val publisherConfig  = RabbitMqConfig(appConfig.getConfig("message-publisher"))
    val actorSystemName  = appConfig.getString("main-actor-system.name")

    // Inject dependencies

    implicit val actorSystem          = ActorSystem(actorSystemName)
    implicit val executionContext     = actorSystem.dispatcher
    implicit val doobieDbConnection   = new DoobieDbConnection[IO](dbConfig)
    implicit val doobieUserRepo       = DoobieMySqlUserRepository[IO]
    implicit val doobieVideoRepo      = DoobieMySqlVideoRepository[IO]
    implicit val rabbitMqPublisher    = RabbitMqMessagePublisher(publisherConfig)
    implicit val doobieRabbitMqSystem = SystemRepoPublisher[Future]
    val akkaHttpSystem                = SystemController()

    // Run system

    akkaHttpSystem.run(httpServerConfig)
  }
}
