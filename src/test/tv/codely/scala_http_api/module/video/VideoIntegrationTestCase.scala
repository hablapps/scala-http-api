package tv.codely.scala_http_api
package module.video

import tv.codely.scala_http_api.module.IntegrationTestCase
import tv.codely.scala_http_api.module.video.domain.VideoRepository
import tv.codely.scala_http_api.module.video.infrastructure.dependency_injection.VideoModuleDependencyContainer
import scala.concurrent.Future
import cats.instances.future._

protected[video] trait VideoIntegrationTestCase extends IntegrationTestCase {
  private val container = new VideoModuleDependencyContainer(doobieDbConnection, messagePublisher)

  protected val repository: VideoRepository[Future] = container.repository
}
