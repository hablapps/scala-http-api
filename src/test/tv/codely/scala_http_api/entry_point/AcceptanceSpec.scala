package tv.codely.scala_http_api
package entry_point

import scala.concurrent.Future

import cats.effect.IO
import cats.instances.future._

import akka.util.ByteString
import akka.http.scaladsl.model._
import akka.http.scaladsl.testkit.ScalatestRouteTest

import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{Matchers, WordSpec}

import com.typesafe.config.ConfigFactory

import application.repositories.doobieImpl.JdbcConfig
import application.repositories.doobieImpl.{DoobieDbConnection, JdbcConfig}
import application.repositories.doobieImpl.DoobieMySqlUserRepository
import application.repositories.doobieImpl.DoobieMySqlVideoRepository
import application.system.repo_publisher.SystemRepoPublisher

import effects.bus.rabbit_mq.{RabbitMqConfig, RabbitMqMessagePublisher}
import effects.akkaHttp.system.{SystemController, HttpServerConfig}

protected[entry_point] abstract class AcceptanceSpec
    extends WordSpec
    with Matchers
    with ScalaFutures
    with ScalatestRouteTest {

  // Read configs

  val appConfig        = ConfigFactory.load("application")
  val httpServerConfig = HttpServerConfig(ConfigFactory.load("http-server"))
  val dbConfig         = JdbcConfig(appConfig.getConfig("database"))
  val publisherConfig  = RabbitMqConfig(appConfig.getConfig("message-publisher"))
  val actorSystemName  = appConfig.getString("main-actor-system.name")

  // Inject dependencies

  implicit val executionContext     = system.dispatcher
  implicit val doobieDbConnection   = new DoobieDbConnection[IO](dbConfig)
  implicit val doobieUserRepo       = DoobieMySqlUserRepository[IO]
  implicit val doobieVideoRepo      = DoobieMySqlVideoRepository[IO]
  implicit val rabbitMqPublisher    = RabbitMqMessagePublisher(publisherConfig)
  implicit val doobieRabbitMqSystem = SystemRepoPublisher[Future]
  val akkaHttpSystem                = SystemController()

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
