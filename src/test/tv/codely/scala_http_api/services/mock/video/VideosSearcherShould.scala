package tv.codely.scala_http_api
package services.mock.video

import tv.codely.scala_http_api.application.mock.UnitTestCase
import tv.codely.scala_http_api.application.stubs.video.VideoStub
import tv.codely.scala_http_api.application.repositories.mock.VideoRepositoryMock
import tv.codely.scala_http_api.application.video.repo_publisher.VideosSearcherRepo

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
