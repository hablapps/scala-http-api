package tv.codely.scala_http_api.application.video

import tv.codely.scala_http_api.application.user.UserId

trait VideoCreator[P[_]] {

  def create(
    id: VideoId,
    title: VideoTitle,
    duration: VideoDuration,
    category: VideoCategory,
    creatorId: UserId
  ): P[Unit]
}
