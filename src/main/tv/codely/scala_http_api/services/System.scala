package tv.codely.scala_http_api.module

import tv.codely.scala_http_api.module.video.application.create.VideoCreator
import tv.codely.scala_http_api.module.video.application.search.VideosSearcher
import tv.codely.scala_http_api.module.user.application.register.UserRegister
import tv.codely.scala_http_api.module.user.application.search.UsersSearcher

trait System[P[_]]{
  val UserRegister: UserRegister[P]
  val UsersSearcher: UsersSearcher[P]
  val VideoCreator: VideoCreator[P]
  val VideosSearcher: VideosSearcher[P]
}
