package tv.codely.scala_http_api.module.user.domain

import tv.codely.scala_http_api.module.shared.user.domain.UserId

object User {
  def apply(id: String, name: String): User = User(UserId(id), UserName(name))
}

// TODO(jfuentes): Is necessary to create classes for every field?
// I'm not against it, but at least using value classes...
case class User(id: UserId, name: UserName)
