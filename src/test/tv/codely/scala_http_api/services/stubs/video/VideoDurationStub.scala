package tv.codely.scala_http_api.effects.repositories.api

import scala.concurrent.duration.Duration

import tv.codely.scala_http_api.services.stubs.DurationStub
import tv.codely.scala_http_api.services.api.video._

object VideoDurationStub {
  def apply(value: Duration): VideoDuration = VideoDuration(value)

  def random: VideoDuration = VideoDuration(DurationStub.random)
}
