package tv.codely.scala_http_api

import cats.Monad

class State[S, A](val run: S => (S, A)) {
  def lift[T](
      from: T => S,
      to: (S, T) => T): State[T, A] = State { t =>
    val s = from(t)
    val (s2, out) = run(s)
    (to(s2, t), out)
  }

  def lift[T](implicit lens: Lens[T, S]): State[T, A] = State { t =>
    val s = lens.get(t)
    val (s2, out) = run(s)
    (lens.put(s2)(t), out)
  }
}

object State {
  def apply[S, A](run: S => (S, A)) = new State[S, A](run)

  implicit def monadState[S] = new Monad[State[S, ?]] {
    def pure[A](x: A): State[S, A] = State { s => (s, x) }
    def flatMap[A, B](fa: State[S, A])(f: A => State[S, B]): State[S, B] = State { s =>
      val (s2, a) = fa.run(s)
      f(a).run(s2)
    }
    def tailRecM[A, B](a: A)(f: A => State[S,Either[A, B]]): State[S, B] = ???
  }
}
