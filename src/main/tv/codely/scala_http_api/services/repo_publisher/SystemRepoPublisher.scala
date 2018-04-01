package tv.codely.scala_http_api.module

import tv.codely.scala_http_api.services.api.video._
import tv.codely.scala_http_api.services.api.video._
import tv.codely.scala_http_api.services.api.user._
import tv.codely.scala_http_api.services.api.user._
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