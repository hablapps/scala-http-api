package tv.codely.scala_http_api.effects.repositories.api

import java.util.UUID

import tv.codely.scala_http_api.services.stubs.UuidStub
import tv.codely.scala_http_api.services.api.video._

object VideoIdStub {
  def apply(value: String): VideoId = VideoIdStub(UuidStub(value))

  def apply(value: UUID): VideoId = VideoId(value)

  def random: VideoId = VideoId(UuidStub.random)
}
