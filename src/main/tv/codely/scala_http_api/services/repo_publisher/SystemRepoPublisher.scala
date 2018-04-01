package tv.codely.scala_http_api.module

import tv.codely.scala_http_api.module.video.application.create._
import tv.codely.scala_http_api.module.video.application.search._
import tv.codely.scala_http_api.module.user.application.register._
import tv.codely.scala_http_api.module.user.application.search._
import tv.codely.scala_http_api.effects.repositories.api._
import tv.codely.scala_http_api.effects.repositories.api._
import tv.codely.scala_http_api.effects.bus.api.MessagePublisher
import cats.Apply

final case class SystemRepoPublisher[P[_]]()(implicit
  userRepository: UserRepository[P], 
  videoRepository: VideoRepository[P], 
  publisher: MessagePublisher[P],
  Ap: Apply[P]) 
extends System[P]{
  val UserRegister = UserRegisterRepoPublisher[P]
  val UsersSearcher = UsersSearcherRepo[P]
  val VideoCreator = VideoCreatorRepoPublisher[P]
  val VideosSearcher = VideosSearcherRepo[P]
}