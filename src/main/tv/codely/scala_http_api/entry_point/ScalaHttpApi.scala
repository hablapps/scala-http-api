package tv.codely
package scala_http_api
package entry_point

import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory
import tv.codely.scala_http_api.module.shared.bus.infrastructure.rabbit_mq.RabbitMqConfig
import tv.codely.scala_http_api.module.shared.dependency_injection.infrastructure.SharedModuleDependencyContainer
import tv.codely.scala_http_api.module.shared.persistence.infrastructure.doobie.JdbcConfig

import scala.concurrent.ExecutionContext

object ScalaHttpApi {
  def main(args: Array[String]): Unit = {
    val appConfig    = ConfigFactory.load("application")
    val serverConfig = ConfigFactory.load("http-server")
    val dbConfig        = JdbcConfig(appConfig.getConfig("database"))
    val publisherConfig = RabbitMqConfig(appConfig.getConfig("message-publisher"))

    val actorSystemName = appConfig.getString("main-actor-system.name")


    val sharedDependencies = new SharedModuleDependencyContainer(actorSystemName, dbConfig, publisherConfig)

    implicit val actorSystem: ActorSystem                = sharedDependencies.actorSystem
    implicit val executionContext: ExecutionContext = sharedDependencies.executionContext

    import tv.codely.scala_http_api.module.user.infrastructure.repository.DoobieMySqlUserRepository
    implicit val userDoobieRepo = new DoobieMySqlUserRepository(sharedDependencies.doobieDbConnection)
    import tv.codely.scala_http_api.module.video.infrastructure.repository.DoobieMySqlVideoRepository
    implicit val videoDoobierepo = new DoobieMySqlVideoRepository(sharedDependencies.doobieDbConnection)
    import scala.concurrent.Future, cats.instances.future._
    implicit val publisherRabbit: module.shared.bus.domain.MessagePublisher[Future] = sharedDependencies.messagePublisher
    val doobiePublisherSystem = module.SystemRepoPublisher.apply[Future]

    val system = new SystemController()(doobiePublisherSystem, implicitly)

    val httpserverconfig = HttpServerConfig(serverConfig)
    system.run(httpserverconfig)
  }
}
