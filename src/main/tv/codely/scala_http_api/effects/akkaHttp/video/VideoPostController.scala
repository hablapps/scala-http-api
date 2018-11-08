package tv.codely.scala_http_api
package effects.akkaHttp.video

import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent.duration.Duration

import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.model.StatusCodes.NoContent
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives.complete

import application.video.VideoCreator
import application.video._
import application.user.UserId

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
