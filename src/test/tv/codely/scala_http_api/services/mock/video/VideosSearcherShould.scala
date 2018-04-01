package tv.codely.scala_http_api.module.video.application.search

import tv.codely.scala_http_api.services.mock.UnitTestCase
import tv.codely.scala_http_api.module.video.domain.VideoStub
import tv.codely.scala_http_api.effects.repositories.doobie.VideoRepositoryMock

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
