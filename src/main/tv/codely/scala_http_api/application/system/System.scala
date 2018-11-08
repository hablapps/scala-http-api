package tv.codely.scala_http_api.application
package system

import video.VideoCreator
import video.VideosSearcher
import user.UserRegister
import user.UsersSearcher

trait System[P[_]] {
  val UserRegister: UserRegister[P]
  val UsersSearcher: UsersSearcher[P]
  val VideoCreator: VideoCreator[P]
  val VideosSearcher: VideosSearcher[P]
}
