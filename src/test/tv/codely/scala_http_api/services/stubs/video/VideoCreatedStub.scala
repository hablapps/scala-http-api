package tv.codely.scala_http_api.effects.repositories.api

import tv.codely.scala_http_api.services.api.user.UserId
import tv.codely.scala_http_api.effects.repositories.api.UserIdStub
import tv.codely.scala_http_api.services.api.video._

object VideoCreatedStub {
  def apply(
      id: VideoId = VideoIdStub.random,
      title: VideoTitle = VideoTitleStub.random,
      duration: VideoDuration = VideoDurationStub.random,
      category: VideoCategory = VideoCategoryStub.random,
      creatorId: UserId = UserIdStub.random
  ): VideoCreated = VideoCreated(id, title, duration, category, creatorId)

  def apply(video: Video): VideoCreated = apply(video.id, video.title, video.duration, video.category, video.creatorId)

  def random: VideoCreated = apply()
}
