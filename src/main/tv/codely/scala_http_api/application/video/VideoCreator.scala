package tv.codely.scala_http_api.application
package video

import user.UserId

trait VideoCreator[P[_]] {

  def create(
    id: VideoId,
    title: VideoTitle,
    duration: VideoDuration,
    category: VideoCategory,
    creatorId: UserId
  ): P[Unit]
}
