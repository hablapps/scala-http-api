package tv.codely.scala_http_api.entry_point.controller.video

import scala.concurrent.duration.Duration
import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.model.StatusCodes.NoContent
import akka.http.scaladsl.server.Route
import tv.codely.scala_http_api.module.video.application.create.VideoCreator
import akka.http.scaladsl.server.Directives.complete
import tv.codely.scala_http_api.module.shared.user.domain.UserId
import tv.codely.scala_http_api.module.video.domain.{VideoCategory, VideoDuration, VideoId, VideoTitle}
import scala.concurrent.{ExecutionContext, Future}
import cats.instances.future._

final class VideoPostController(creator: VideoCreator[Future])(implicit executionContext: ExecutionContext) {
  def post(id: String, title: String, duration: Duration, category: String, creatorId: String): Route =
    complete(creator.create(VideoId(id), VideoTitle(title), VideoDuration(duration), VideoCategory(category), UserId(creatorId)).map{
      _ => HttpResponse(NoContent)
    })
}
