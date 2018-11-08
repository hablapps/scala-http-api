package tv.codely.scala_http_api
package application
package system
package akkaHttp

import scala.concurrent.Future
import akka.http.scaladsl.Http
import akka.util.ByteString
import akka.actor.ActorSystem
import akka.http.scaladsl.model._
import akka.stream.ActorMaterializer

import video._, user._

import effects.akkaHttp.system.HttpServerConfig
import effects.akkaHttp.user.marshaller.UserJsonFormatMarshaller._
import effects.akkaHttp.video.marshaller.VideoJsonFormatMarshaller._


final class SystemHttpClient(
  implicit
  httpConfig: HttpServerConfig,
  actorSystem: ActorSystem
) extends System[Future] {
  implicit val ec                              = actorSystem.dispatcher
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  import spray.json._

  val UserRegister = new UserRegister[Future] {
    def register(id: UserId, name: UserName): Future[Unit] =
      Http()
        .singleRequest(
          HttpRequest(
            method = HttpMethods.POST,
            uri = httpConfig.addPath("users"),
            entity = HttpEntity(
              MediaTypes.`application/json`,
              ByteString(User(id, name).toJson.compactPrint)
            )
          )
        )
        .map(_ => ())
  }

  val UsersSearcher = new UsersSearcher[Future] {
    def all(): Future[Seq[User]] =
      Http().singleRequest(HttpRequest(method = HttpMethods.GET, uri = httpConfig.addPath("users"))).flatMap {
        case HttpResponse(StatusCodes.OK, _, entity, _) =>
          entity.dataBytes
            .runFold(ByteString(""))(_ ++ _)
            .map(_.utf8String.parseJson.convertTo[List[User]](DefaultJsonProtocol.listFormat[User]))
        case response => Future.failed(new RuntimeException(response.toString))
      }
  }

  val VideoCreator = new VideoCreator[Future] {
    def create(
      id: VideoId,
      title: VideoTitle,
      duration: VideoDuration,
      category: VideoCategory,
      creatorId: UserId
    ): Future[Unit] =
      Http()
        .singleRequest(
          HttpRequest(
            method = HttpMethods.POST,
            uri = httpConfig.addPath("videos"),
            entity = HttpEntity(
              MediaTypes.`application/json`,
              ByteString(Video(id, title, duration, category, creatorId).toJson.compactPrint)
            )
          )
        )
        .map(_ => ())
  }

  val VideosSearcher = new VideosSearcher[Future] {
    def all(): Future[Seq[Video]] =
      Http().singleRequest(HttpRequest(method = HttpMethods.GET, uri = httpConfig.addPath("videos"))).flatMap {
        case HttpResponse(StatusCodes.OK, _, entity, _) =>
          entity.dataBytes
            .runFold(ByteString(""))(_ ++ _)
            .map(_.utf8String.parseJson.convertTo[List[Video]](DefaultJsonProtocol.listFormat[Video]))
      }
  }
}
