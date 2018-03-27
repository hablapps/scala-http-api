package tv.codely.scala_http_api.entry_point.controller.user

import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.model.StatusCodes.NoContent
// import akka.http.scaladsl.server.Directives.complete
import akka.http.scaladsl.server.Directives.{onSuccess, complete}
// import akka.http.scaladsl.server.StandardRoute
import akka.http.scaladsl.server.Route
import tv.codely.scala_http_api.module.shared.user.domain.UserId
import tv.codely.scala_http_api.module.user.application.register.UserRegistrar
import tv.codely.scala_http_api.module.user.domain.UserName
import scala.concurrent.{ExecutionContext, Future}
import cats.instances.future._

final class UserPostController(registrar: UserRegistrar[Future])(implicit executionContext: ExecutionContext) {
  def post(id: String, name: String): Route = //  : StandardRoute =
    onSuccess(registrar.register(UserId(id), UserName(name))).tapply{
      case _ => complete(HttpResponse(NoContent))
    }
}
