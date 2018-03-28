package tv.codely.scala_http_api
package module.video.application.create

import tv.codely.scala_http_api.module.UnitTestCase
import tv.codely.scala_http_api.module.shared.infrastructure.MessagePublisherMock
import tv.codely.scala_http_api.module.video.domain.{VideoCreatedStub, VideoStub}
import tv.codely.scala_http_api.module.video.infrastructure.repository.VideoRepositoryMock
import scala.concurrent.{Future, ExecutionContext}, ExecutionContext.Implicits.global
import cats.instances.future._

final class VideoCreatorRepoShould extends UnitTestCase with VideoRepositoryMock with MessagePublisherMock {
  private val creator = new VideoCreatorRepo[Future](repository, messagePublisher)

  "save a video" in {
    val video        = VideoStub.random
    val videoCreated = VideoCreatedStub(video)

    repositoryShouldSave(video)

    publisherShouldPublish(videoCreated)

    creator.create(video.id, video.title, video.duration, video.category, video.creatorId).map(_.shouldBe(()))
  }
}
