package tv.codely.scala_http_api
package effects.akkaHttp
package video.marshaller

import spray.json.{DefaultJsonProtocol, RootJsonFormat}

import application.user.UserId
import application.video._
import user.marshaller.UserIdJsonFormatMarshaller._
import video.marshaller.VideoAttributesJsonFormatMarshaller._

object VideoJsonFormatMarshaller extends DefaultJsonProtocol {
  implicit val videoFormat: RootJsonFormat[Video] = jsonFormat(
    Video.apply(_: VideoId, _: VideoTitle, _: VideoDuration, _: VideoCategory, _: UserId),
    "id",
    "title",
    "duration_in_seconds",
    "category",
    "creator_id"
  )
}
