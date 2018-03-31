package tv.codely.scala_http_api
package entry_point

import scala.concurrent.Future

import cats.effect.IO
import cats.instances.future._

import akka.util.ByteString
import akka.http.scaladsl.model._
import akka.http.scaladsl.testkit.ScalatestRouteTest

import com.typesafe.config.ConfigFactory

import tv.codely.scala_http_api.module.shared.persistence.infrastructure.doobie.JdbcConfig
import tv.codely.scala_http_api.module.shared.persistence.infrastructure.doobie.{DoobieDbConnection, JdbcConfig}
import tv.codely.scala_http_api.module.user.infrastructure.repository.DoobieMySqlUserRepository
import tv.codely.scala_http_api.module.video.infrastructure.repository.DoobieMySqlVideoRepository
import tv.codely.scala_http_api.module.shared.bus.infrastructure.rabbit_mq.{RabbitMqConfig, RabbitMqMessagePublisher}

import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{Matchers, WordSpec}

protected[entry_point] abstract class AcceptanceSpec
    extends WordSpec
    with Matchers
    with ScalaFutures
    with ScalatestRouteTest {
  
  // Read configs

  val appConfig    = ConfigFactory.load("application")
  val httpServerConfig = HttpServerConfig(ConfigFactory.load("http-server"))
  val dbConfig        = JdbcConfig(appConfig.getConfig("database"))
  val publisherConfig = RabbitMqConfig(appConfig.getConfig("message-publisher"))
  val actorSystemName = appConfig.getString("main-actor-system.name")

  // Inject dependencies

  implicit val executionContext = system.dispatcher
  implicit val doobieDbConnection = new DoobieDbConnection[IO](dbConfig)
  implicit val doobieUserRepo = DoobieMySqlUserRepository[IO]
  implicit val doobieVideoRepo = DoobieMySqlVideoRepository[IO]
  implicit val rabbitMqPublisher = RabbitMqMessagePublisher(publisherConfig)
  implicit val doobieRabbitMqSystem = module.SystemRepoPublisher.apply[Future]
  val akkaHttpSystem = SystemController.apply

  // Run configuration

  protected def posting[T](path: String, request: String)(body: ⇒ T): T =
    HttpRequest(
      method = HttpMethods.POST,
      uri = path,
      entity = HttpEntity(
        MediaTypes.`application/json`,
        ByteString(request)
      )
    ) ~> akkaHttpSystem.routes.all ~> check(body)

  protected def getting[T](path: String)(body: ⇒ T): T = 
    Get(path) ~> akkaHttpSystem.routes.all ~> check(body)
}
