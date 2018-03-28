package tv.codely.scala_http_api
package module.video.infrastructure.dependency_injection

import tv.codely.scala_http_api.module.shared.bus.domain.MessagePublisher
import tv.codely.scala_http_api.module.shared.persistence.infrastructure.doobie.DoobieDbConnection
import tv.codely.scala_http_api.module.video.application.create.{VideoCreator, VideoCreatorRepo}
import tv.codely.scala_http_api.module.video.application.search.VideosSearcherRepo
import tv.codely.scala_http_api.module.video.domain.VideoRepository
import tv.codely.scala_http_api.module.video.infrastructure.repository.DoobieMySqlVideoRepository

import scala.concurrent.{Future, ExecutionContext}
import cats.Id, cats.instances.future._

final class VideoModuleDependencyContainer(
    doobieDbConnection: DoobieDbConnection,
    messagePublisher: MessagePublisher[Id]
)(implicit executionContext: ExecutionContext) {
  val repository: VideoRepository[Future] = new DoobieMySqlVideoRepository(doobieDbConnection)

  val videosSearcher: VideosSearcherRepo[Future] = new VideosSearcherRepo(repository)
  val videoCreator: VideoCreator[Future]     = new VideoCreatorRepo[Future](repository, messagePublisher)
}
