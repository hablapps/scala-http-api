package tv.codely.scala_http_api.module.video.application.search

import tv.codely.scala_http_api.effects.repositories.api.Video

trait VideosSearcher[P[_]]{
  def all(): P[Seq[Video]]
}
