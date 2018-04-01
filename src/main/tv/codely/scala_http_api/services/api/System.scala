package tv.codely.scala_http_api.module

import tv.codely.scala_http_api.services.api.video.VideoCreator
import tv.codely.scala_http_api.services.api.video.VideosSearcher
import tv.codely.scala_http_api.services.api.user.UserRegister
import tv.codely.scala_http_api.services.api.user.UsersSearcher

trait System[P[_]]{
  val UserRegister: UserRegister[P]
  val UsersSearcher: UsersSearcher[P]
  val VideoCreator: VideoCreator[P]
  val VideosSearcher: VideosSearcher[P]
}
