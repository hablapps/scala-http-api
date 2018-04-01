package tv.codely.scala_http_api.services.stubs

object SeqStub {
  val maximumElements = 10

  def randomOf[T](apply: => T): Seq[T] = (0 to IntStub.randomBetween(1, maximumElements)).map(_ => apply)
}
