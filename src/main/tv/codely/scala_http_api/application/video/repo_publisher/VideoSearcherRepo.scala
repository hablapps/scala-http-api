package tv.codely.scala_http_api.application
package video
package repo_publisher

import repositories.VideoRepository

final case class VideosSearcherRepo[P[_]]()(
  implicit
  repository: VideoRepository[P]
) extends VideosSearcher[P] {
  def all(): P[Seq[Video]] = repository.all()
}
