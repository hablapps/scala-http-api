package tv.codely.scala_http_api.application.api.video

trait VideosSearcher[P[_]]{
  def all(): P[Seq[Video]]
}
