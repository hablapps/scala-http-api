package tv.codely.scala_http_api
package entry_point

import com.typesafe.config.Config
import scala.concurrent.ExecutionContext.Implicits.global
import cats.effect.IO

import tv.codely.scala_http_api.effects.bus.rabbit_mq.{RabbitMqConfig, RabbitMqMessagePublisher}
import tv.codely.scala_http_api.application.repositories.doobieImpl.{DoobieDbConnection, JdbcConfig}
import tv.codely.scala_http_api.application.repositories.doobieImpl.DoobieMySqlUserRepository
import tv.codely.scala_http_api.application.repositories.doobieImpl.DoobieMySqlVideoRepository
import tv.codely.scala_http_api.application.repo_publisher.SystemRepoPublisher
import tv.codely.scala_http_api.application.System
import tv.codely.scala_http_api.effects.http4s.Http4sSystemService

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
