package tv.codely.scala_http_api
package effects.http4s

import cats.syntax.apply._, cats.syntax.flatMap._
import cats.effect._

import org.http4s._
import org.http4s.dsl.Http4sDsl
import org.http4s.circe._

import io.circe.generic.auto._
import io.circe.syntax._

import application.user._
import Decoders._

case class UserService[P[_]: Effect](
  usersSearcher: UsersSearcher[P],
  userRegister: UserRegister[P]
) extends Http4sDsl[P] {

  val service = HttpService[P] {
    case GET -> Root / "users" =>
      usersSearcher.all().flatMap { users =>
        Ok(users.asJson)
      }

    case req @ (POST -> Root / "users") =>
      req.as[(String, String)] >>= {
        case (id, name) =>
          userRegister.register(UserId(id), UserName(name)) *>
            NoContent()
      }
  }
}
