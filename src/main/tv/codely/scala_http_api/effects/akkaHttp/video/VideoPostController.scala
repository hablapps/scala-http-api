package tv.codely.scala_http_api.effects.akkaHttp.video

import scala.concurrent.duration.Duration
import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.model.StatusCodes.NoContent
import akka.http.scaladsl.server.Route
import tv.codely.scala_http_api.application.video.VideoCreator
import akka.http.scaladsl.server.Directives.complete
import tv.codely.scala_http_api.application.user.UserId
import tv.codely.scala_http_api.application.video._
import scala.concurrent.{ExecutionContext, Future}

final class VideoPostController(creator: VideoCreator[Future])(implicit executionContext: ExecutionContext) {
  def post(id: String, title: String, duration: Duration, category: String, creatorId: String): Route =
    complete(
      creator
        .create(VideoId(id), VideoTitle(title), VideoDuration(duration), VideoCategory(category), UserId(creatorId))
        .map { _ =>
          HttpResponse(NoContent)
        }
    )
}
