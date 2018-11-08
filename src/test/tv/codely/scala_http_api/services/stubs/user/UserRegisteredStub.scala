package tv.codely.scala_http_api.application.stubs.user

import tv.codely.scala_http_api.application.user.User
import tv.codely.scala_http_api.application.user.UserId
import tv.codely.scala_http_api.application.user.UserName
import tv.codely.scala_http_api.application.user.UserRegistered

object UserRegisteredStub {
  def apply(
    id: UserId = UserIdStub.random,
    name: UserName = UserNameStub.random
  ): UserRegistered = UserRegistered(id, name)

  def apply(user: User): UserRegistered = apply(user.id, user.name)

  def random: UserRegistered = apply()
}
