package tv.codely.scala_http_api.module

import tv.codely.scala_http_api.module.video.application.create._
import tv.codely.scala_http_api.module.video.application.search._
import tv.codely.scala_http_api.module.user.application.register._
import tv.codely.scala_http_api.module.user.application.search._
import tv.codely.scala_http_api.module.user.domain._
import tv.codely.scala_http_api.module.video.domain._
import tv.codely.scala_http_api.module.shared.bus.domain.MessagePublisher
import cats.Apply

final case class SystemRepoPublisher[P[_]]()(implicit
  userRepository: UserRepository[P], 
  videoRepository: VideoRepository[P], 
  publisher: MessagePublisher[P],
  Ap: Apply[P]) 
extends System[P]{
  val UserRegister = UserRegisterRepoPublisher.apply[P]
  val UsersSearcher = UsersSearcherRepo.apply[P]
  val VideoCreator = VideoCreatorRepoPublisher.apply[P]
  val VideosSearcher = VideosSearcherRepo.apply[P]
}