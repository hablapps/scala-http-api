package tv.codely.scala_http_api
package entry_point

import com.typesafe.config.Config
import scala.concurrent.ExecutionContext.Implicits.global
import cats.effect.IO

import effects.bus.rabbit_mq.{RabbitMqConfig, RabbitMqMessagePublisher}
import effects.http4s.Http4sSystemService

import application.repositories.doobieImpl.{DoobieMySqlVideoRepository, DoobieMySqlUserRepository, DoobieDbConnection, JdbcConfig}
import application.system.repo_publisher.SystemRepoPublisher
import application.system.System

object Http4sEntryPoint extends Http4sSystemService.App[IO](DoobieRabbitSystem.system)

object DoobieRabbitSystem {
  val system: Config => System[IO] = {
    case appConfig =>
      // Read configs
      val dbConfig        = JdbcConfig(appConfig.getConfig("database"))
      val publisherConfig = RabbitMqConfig(appConfig.getConfig("message-publisher"))

      // Inject dependencies
      implicit val doobieDbConnection = new DoobieDbConnection[IO](dbConfig)
      implicit val doobieUserRepo     = DoobieMySqlUserRepository[IO]
      implicit val doobieVideoRepo    = DoobieMySqlVideoRepository[IO]
      implicit val rabbitMqPublisher  = RabbitMqMessagePublisher(publisherConfig)
      SystemRepoPublisher[IO]
  }
}
