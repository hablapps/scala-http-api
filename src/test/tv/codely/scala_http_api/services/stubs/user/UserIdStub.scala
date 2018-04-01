package tv.codely.scala_http_api.services.stubs.user

import java.util.UUID

import tv.codely.scala_http_api.services.stubs.UuidStub
import tv.codely.scala_http_api.services.api.user.UserId

object UserIdStub {
  def apply(value: String): UserId = UserIdStub(UuidStub(value))

  def apply(value: UUID): UserId = UserId(value)

  def random: UserId = UserId(UuidStub.random)
}
