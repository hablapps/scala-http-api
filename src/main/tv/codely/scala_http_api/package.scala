package tv.codely

package object scala_http_api{

  import cats.{Id, ~>}
  import scala.concurrent.Future

  implicit def fromIdToFuture: Id ~> Future =
    λ[Id ~> Future](Future.successful(_))
}