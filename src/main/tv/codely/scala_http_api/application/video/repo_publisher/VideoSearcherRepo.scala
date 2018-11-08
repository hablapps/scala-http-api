package tv.codely.scala_http_api.application.video.repo_publisher

import tv.codely.scala_http_api.application.video._
import tv.codely.scala_http_api.application.repositories.VideoRepository

final case class VideosSearcherRepo[P[_]]()(
  implicit
  repository: VideoRepository[P]
) extends VideosSearcher[P] {
  def all(): P[Seq[Video]] = repository.all()
}
