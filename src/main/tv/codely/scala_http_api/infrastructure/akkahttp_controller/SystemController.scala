package tv.codely.scala_http_api.entry_point

import tv.codely.scala_http_api.module.System
import tv.codely.scala_http_api.entry_point.controller.status.StatusGetController
import tv.codely.scala_http_api.entry_point.controller.user.{UserGetController, UserPostController}
import tv.codely.scala_http_api.entry_point.controller.video.{VideoGetController, VideoPostController}
import scala.concurrent.ExecutionContext
import scala.concurrent.Future
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.http.scaladsl.Http
import scala.io.StdIn

final case class SystemController()(implicit 
  system: System[Future],
  executionContext: ExecutionContext
){
  val statusGetController = new StatusGetController

  val userGetController  = new UserGetController(system.UsersSearcher)
  val userPostController = new UserPostController(system.UserRegister)

  val videoGetController  = new VideoGetController(system.VideosSearcher)
  val videoPostController = new VideoPostController(system.VideoCreator)

  val routes = new Routes(this)

  def run(serverConfig: HttpServerConfig)(implicit actorSystem: ActorSystem): Unit = {
    implicit val materializer: ActorMaterializer = ActorMaterializer()

    val host = serverConfig.host
    val port = serverConfig.port

    val bindingFuture = Http().bindAndHandle(routes.all, host, port)

      bindingFuture.failed.foreach { t =>
        println(s"Failed to bind to http://$host:$port/:")
        pprint.log(t)
      }

      // let it run until user presses return
      println(s"Server online at http://$host:$port/\nPress RETURN to stop...")
      StdIn.readLine()

      bindingFuture
        .flatMap(_.unbind())
        .onComplete(_ => actorSystem.terminate())
  }
}
