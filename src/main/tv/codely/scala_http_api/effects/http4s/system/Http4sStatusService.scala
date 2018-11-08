package tv.codely.scala_http_api.effects.http4s

import cats.effect._
import io.circe.literal._

import org.http4s._
import org.http4s.circe._
import org.http4s.dsl.Http4sDsl

case class StatusService[F[_]: Effect]() extends Http4sDsl[F] {
  val service = HttpService[F] {
    case GET -> Root / "status" =>
      Ok(json"""{"status":"ok"}""")
  }
}
