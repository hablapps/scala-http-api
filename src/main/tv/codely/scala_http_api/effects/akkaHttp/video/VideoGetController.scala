package tv.codely.scala_http_api
package effects.akkaHttp.video

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.server.StandardRoute
import akka.http.scaladsl.server.Directives.complete
import spray.json.DefaultJsonProtocol

import scala.concurrent.Future

import application.video.VideosSearcher
import marshaller.VideoJsonFormatMarshaller._

final class VideoGetController(searcher: VideosSearcher[Future]) extends SprayJsonSupport with DefaultJsonProtocol {
  def get(): StandardRoute = complete(searcher.all())
}
