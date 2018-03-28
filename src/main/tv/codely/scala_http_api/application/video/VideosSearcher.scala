package tv.codely.scala_http_api.module.video.application.search

import tv.codely.scala_http_api.module.video.domain.{Video, VideoRepository}

final class VideosSearcher[P[_]](repository: VideoRepository[P]) {
  def all(): P[Seq[Video]] = repository.all()
}
