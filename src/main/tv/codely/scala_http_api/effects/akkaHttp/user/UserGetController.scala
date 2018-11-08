package tv.codely.scala_http_api
package effects.akkaHttp.user

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.server.StandardRoute
import akka.http.scaladsl.server.Directives.complete
import spray.json.DefaultJsonProtocol

import scala.concurrent.Future

import application.user.UsersSearcher
import marshaller.UserJsonFormatMarshaller._

final class UserGetController(searcher: UsersSearcher[Future]) extends SprayJsonSupport with DefaultJsonProtocol {
  def get(): StandardRoute = complete(searcher.all())
}
