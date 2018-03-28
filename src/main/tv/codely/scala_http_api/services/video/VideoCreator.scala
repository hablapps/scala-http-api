package tv.codely.scala_http_api.module.video.application.create

import tv.codely.scala_http_api.module.shared.user.domain.UserId
import tv.codely.scala_http_api.module.video.domain._

trait VideoCreator[P[_]]{

  def create(
    id: VideoId,
    title: VideoTitle,
    duration: VideoDuration,
    category: VideoCategory,
    creatorId: UserId): P[Unit]
}
