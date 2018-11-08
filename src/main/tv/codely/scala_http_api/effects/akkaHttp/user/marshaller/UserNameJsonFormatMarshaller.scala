package tv.codely.scala_http_api
package effects.akkaHttp.user.marshaller

import spray.json.{DefaultJsonProtocol, DeserializationException, JsString, JsValue, JsonFormat}
import application.user.UserName

object UserNameJsonFormatMarshaller extends DefaultJsonProtocol {
  implicit object UserNameMarshaller extends JsonFormat[UserName] {
    override def write(value: UserName): JsValue = JsString(value.value)

    override def read(value: JsValue): UserName = value match {
      case JsString(name) => UserName(name)
      case _              => throw DeserializationException("Expected 1 string for UserName")
    }
  }
}
