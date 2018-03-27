package tv.codely.scala_http_api.module.video.application.create

import tv.codely.scala_http_api.module.shared.bus.domain.MessagePublisher
import tv.codely.scala_http_api.module.shared.user.domain.UserId
import tv.codely.scala_http_api.module.video.domain._
import cats.Apply, cats.syntax.apply._

final class VideoCreator[P[_]](repository: VideoRepository[P], publisher: MessagePublisher[P]) {
  def create(
      id: VideoId,
      title: VideoTitle,
      duration: VideoDuration,
      category: VideoCategory,
      creatorId: UserId
  )(implicit A: Apply[P]): P[Unit] = {
    val video = Video(id, title, duration, category, creatorId)

    repository.save(video) *>
    publisher.publish(VideoCreated(video))
  }
}
