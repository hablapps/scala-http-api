package tv.codely.scala_http_api
package application
// package system
package repo_publisher

import video._
import user._
import repositories._
import effects.bus.api.MessagePublisher
import cats.Apply

final case class SystemRepoPublisher[P[_]]()(
  implicit
  userRepository: UserRepository[P],
  videoRepository: VideoRepository[P],
  publisher: MessagePublisher[P],
  Ap: Apply[P]
) extends System[P] {
  val UserRegister   = UserRegisterRepoPublisher[P]
  val UsersSearcher  = UsersSearcherRepo[P]
  val VideoCreator   = VideoCreatorRepoPublisher[P]
  val VideosSearcher = VideosSearcherRepo[P]
}
