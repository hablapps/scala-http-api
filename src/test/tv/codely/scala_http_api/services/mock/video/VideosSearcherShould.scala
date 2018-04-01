package tv.codely.scala_http_api.services.api.video

import tv.codely.scala_http_api.services.mock.UnitTestCase
import tv.codely.scala_http_api.effects.repositories.api.VideoStub
import tv.codely.scala_http_api.effects.repositories.doobie.VideoRepositoryMock
import tv.codely.scala_http_api.services.repo_publisher.video.VideosSearcherRepo

final class VideosSearcherRepoShould extends UnitTestCase with VideoRepositoryMock {
  private val searcher = new VideosSearcherRepo()(repository)

  "search all existing videos" in {
    val existingVideo        = VideoStub.random
    val anotherExistingVideo = VideoStub.random
    val existingVideos       = Seq(existingVideo, anotherExistingVideo)

    repositoryShouldFind(existingVideos)

    searcher.all().futureValue shouldBe existingVideos
  }
}
