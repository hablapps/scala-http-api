package tv.codely.scala_http_api
package module.video.infrastructure.dependency_injection

import tv.codely.scala_http_api.module.shared.bus.domain.MessagePublisher
import tv.codely.scala_http_api.module.shared.persistence.infrastructure.doobie.DoobieDbConnection
import tv.codely.scala_http_api.module.video.application.create.{VideoCreator, VideoCreatorRepoPublisher}
import tv.codely.scala_http_api.module.video.application.search.VideosSearcherRepo
import tv.codely.scala_http_api.module.video.domain.VideoRepository
import tv.codely.scala_http_api.module.video.infrastructure.repository.DoobieMySqlVideoRepository

import scala.concurrent.{Future, ExecutionContext}
import cats.instances.future._

final class VideoModuleDependencyContainer( 
    doobieDbConnection: DoobieDbConnection,
    messagePublisher: MessagePublisher[Future])(implicit
    executionContext: ExecutionContext) {
  implicit val repository: VideoRepository[Future] = new DoobieMySqlVideoRepository(doobieDbConnection)

  val videosSearcher: VideosSearcherRepo[Future] = VideosSearcherRepo[Future]()(repository)
  val videoCreator: VideoCreator[Future]     = VideoCreatorRepoPublisher[Future]()(repository, messagePublisher, implicitly)
}
