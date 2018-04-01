package tv.codely.scala_http_api.effects.repositories.api

import tv.codely.scala_http_api.services.stubs.{IntStub, StringStub}
import tv.codely.scala_http_api.services.api.user.UserName

object UserNameStub {
  private val minimumChars = 1
  private val maximumChars = 20

  def apply(value: String): UserName = UserName(value)

  def random: UserName = UserName(
    StringStub.random(numChars = IntStub.randomBetween(minimumChars, maximumChars))
  )
}
