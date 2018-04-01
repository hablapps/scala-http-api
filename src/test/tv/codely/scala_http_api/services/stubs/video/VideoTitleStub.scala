package tv.codely.scala_http_api.services.stubs.video

import tv.codely.scala_http_api.services.stubs.{IntStub, StringStub}
import tv.codely.scala_http_api.services.api.video._

object VideoTitleStub {
  private val minimumChars = 1
  private val maximumChars = 50

  def apply(value: String): VideoTitle = VideoTitle(value)

  def random: VideoTitle = VideoTitle(
    StringStub.random(numChars = IntStub.randomBetween(minimumChars, maximumChars))
  )
}
