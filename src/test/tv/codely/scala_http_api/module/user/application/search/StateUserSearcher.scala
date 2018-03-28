package tv.codely.scala_http_api.module.user.application.search

import tv.codely.scala_http_api.module.user.infrastructure.repository.UserRepositoryL
import tv.codely.scala_http_api.module.user.domain._

trait UsersSearcherL[P[_]] {
  def all: P[Seq[User]]
}

object UsersSearcherL {
  implicit def instance[P[_]](implicit userRepository: UserRepositoryL[P]) =
    new UsersSearcherL[P] {
      def all: P[Seq[User]] = userRepository.all
    }
}
