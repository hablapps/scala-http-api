package tv.codely.scala_http_api.application
package video

import scala.concurrent.duration.Duration
import user.UserId

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
