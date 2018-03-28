package tv.codely.scala_http_api

trait Lens[S, A] {
  def get(s: S): A
  def put(a: A)(s: S): S
}

object Lens extends LensInstances {
  def apply[S, A](from: S => A, to: A => S => S) =
    new Lens[S, A] {
      def get(s: S): A = from(s)
      def put(a: A)(s: S): S = to(a)(s)
    }
}

trait LensInstances {
  implicit def identity[A] = new Lens[A, A] {
    def get(s: A): A = s
    def put(a: A)(s: A): A = a
  }
}
