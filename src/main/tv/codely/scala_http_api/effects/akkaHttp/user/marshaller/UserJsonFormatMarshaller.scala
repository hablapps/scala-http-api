package tv.codely.scala_http_api
package effects.akkaHttp
package user.marshaller

import spray.json.{DefaultJsonProtocol, RootJsonFormat}

import application.user.{UserId, User, UserName}
import user.marshaller.UserIdJsonFormatMarshaller._
import user.marshaller.UserNameJsonFormatMarshaller._

object UserJsonFormatMarshaller extends DefaultJsonProtocol {
  implicit val userFormat: RootJsonFormat[User] = jsonFormat2(User.apply(_: UserId, _: UserName))
}
