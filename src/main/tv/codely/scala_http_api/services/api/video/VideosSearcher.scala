package tv.codely.scala_http_api.services.api.video

import tv.codely.scala_http_api.effects.repositories.api.Video

trait VideosSearcher[P[_]]{
  def all(): P[Seq[Video]]
}
