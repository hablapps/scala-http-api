package tv.codely.scala_http_api.module.video.domain

trait VideoRepository[P[_]] {
  def all(): P[Seq[Video]]

  def save(video: Video): P[Unit]
}
