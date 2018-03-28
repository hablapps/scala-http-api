package tv.codely.scala_http_api.module.user.application.register

import tv.codely.scala_http_api.module.shared.user.domain.UserId
import tv.codely.scala_http_api.module.user.domain._

trait UserRegister[P[_]]{
  def register(id: UserId, name: UserName): P[Unit]
}
