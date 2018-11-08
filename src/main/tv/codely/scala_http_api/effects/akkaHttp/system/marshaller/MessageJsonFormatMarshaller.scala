package tv.codely.scala_http_api
package effects
package akkaHttp
package system.marshaller

import spray.json.{DefaultJsonProtocol, DeserializationException, JsString, JsValue, RootJsonFormat, SerializationException, _}

import application.user.UserRegistered
import application.video.VideoCreated

import bus.Message
import user.marshaller.UserRegisteredJsonFormatMarshaller._
import video.marshaller.VideoCreatedJsonFormatMarshaller._

object MessageJsonFormatMarshaller extends DefaultJsonProtocol {
  implicit object MessageMarshaller extends RootJsonFormat[Message] {
    override def write(m: Message): JsValue = m match {
      case vc: VideoCreated   => vc.toJson
      case ur: UserRegistered => ur.toJson
      case unknown            => throw new SerializationException(s"Unknown message type to write <${unknown.getClass}>")
    }

    override def read(jv: JsValue): Message = jv.asJsObject.getFields("type") match {
      case Seq(JsString("codelytv_scala_api.video_created"))   => jv.convertTo[VideoCreated]
      case Seq(JsString("codelytv_scala_api.user_registered")) => jv.convertTo[UserRegistered]
      case Seq(JsString(unknown)) =>
        throw DeserializationException(s"Unknown message type to read <$unknown>")
    }
  }
}
