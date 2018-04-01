package tv.codely.scala_http_api.services.api.video

import tv.codely.scala_http_api.services.api.user.UserId
import tv.codely.scala_http_api.effects.repositories.api._

trait VideoCreator[P[_]]{

  def create(
    id: VideoId,
    title: VideoTitle,
    duration: VideoDuration,
    category: VideoCategory,
    creatorId: UserId): P[Unit]
}
