package tv.codely.scala_http_api.services.api.user

import tv.codely.scala_http_api.module.shared.user.domain.UserId
import tv.codely.scala_http_api.effects.repositories.api._

trait UserRegister[P[_]]{
  def register(id: UserId, name: UserName): P[Unit]
}
