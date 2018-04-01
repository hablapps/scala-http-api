package tv.codely.scala_http_api.services.api.video

import tv.codely.scala_http_api.effects.repositories.api.{Video, VideoRepository}

final case class VideosSearcherRepo[P[_]]()(implicit
  repository: VideoRepository[P])
extends VideosSearcher[P] {
  def all(): P[Seq[Video]] = repository.all()
}
