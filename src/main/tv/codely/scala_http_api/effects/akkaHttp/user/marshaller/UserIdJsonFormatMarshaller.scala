package tv.codely.scala_http_api
package effects
package akkaHttp
package user.marshaller

import java.util.UUID
import spray.json.{DefaultJsonProtocol, JsValue, JsonFormat, _}

import application.user.UserId
import system.marshaller.UuidJsonFormatMarshaller._

object UserIdJsonFormatMarshaller extends DefaultJsonProtocol {
  implicit object UserIdMarshaller extends JsonFormat[UserId] {
    override def write(value: UserId): JsValue = value.value.toJson

    override def read(value: JsValue): UserId = UserId(value.convertTo[UUID])
  }
}
