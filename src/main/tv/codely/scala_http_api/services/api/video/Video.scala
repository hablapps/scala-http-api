package tv.codely.scala_http_api.services.api.video

import tv.codely.scala_http_api.services.api.user.UserId

import scala.concurrent.duration.Duration

object Video {
  def apply(id: String, title: String, duration: Duration, category: String, creatorId: String): Video = Video(
    VideoId(id),
    VideoTitle(title),
    VideoDuration(duration),
    VideoCategory(category),
    UserId(creatorId)
  )
}

case class Video(id: VideoId, title: VideoTitle, duration: VideoDuration, category: VideoCategory, creatorId: UserId)
