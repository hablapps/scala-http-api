package tv.codely.scala_http_api.entry_point

import tv.codely.scala_http_api.entry_point.controller.status.StatusGetController
import tv.codely.scala_http_api.entry_point.controller.user.{UserGetController, UserPostController}
import tv.codely.scala_http_api.entry_point.controller.video.{VideoGetController, VideoPostController}
import tv.codely.scala_http_api.module.user.infrastructure.dependency_injection.UserModuleDependencyContainer
import tv.codely.scala_http_api.module.video.infrastructure.dependency_injection.VideoModuleDependencyContainer
import scala.concurrent.ExecutionContext

final class EntryPointDependencyContainer(
    userDependencies: UserModuleDependencyContainer,
    videoDependencies: VideoModuleDependencyContainer
)(implicit executionContext: ExecutionContext) {
  val statusGetController = new StatusGetController

  val userGetController  = new UserGetController(userDependencies.usersSearcher)
  val userPostController = new UserPostController(userDependencies.userRegister)

  val videoGetController  = new VideoGetController(videoDependencies.videosSearcher)
  val videoPostController = new VideoPostController(videoDependencies.videoCreator)
}
