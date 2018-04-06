package tv.codely

package object scala_http_api{

  import cats.{Id, ~>}
  import cats.data.ReaderT
  import scala.concurrent.Future
  import cats.effect.IO

  implicit def fromIdToFuture: Id ~> Future =
    λ[Id ~> Future](Future.successful(_))

  implicit def fromIOToFuture: IO ~> Future = 
    λ[IO ~> Future](io => io.unsafeToFuture)
  
  implicit def fromIdToIO: Id ~> IO =
    λ[Id ~> IO](IO.apply(_))

  implicit def toReaderT[P[_], E]: P ~> ReaderT[P,E,?] = 
    λ[P ~> ReaderT[P,E,?]](pa => cats.data.Kleisli.liftF(pa))

  implicit def hoistReaderT[P[_],Q[_],E](implicit 
    toQ: P~>Q): ReaderT[P,E,?] ~> ReaderT[Q,E,?] = 
    λ[ReaderT[P,E,?]~>ReaderT[Q,E,?]](_.mapK(toQ))
}

package scala_http_api{

  // TODO: reuse monocle lenses

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

  // TODO: reuse cats State

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
}