package tv.codely.scala_http_api.module.user.infrastructure.repository

import tv.codely.scala_http_api.module.user.domain._

trait UserRepositoryL[P[_]] {
  def all: P[Seq[User]]
  def save(user: User): P[Unit]
}

object UserRepositoryL {

  import tv.codely.scala_http_api.State
  import tv.codely.scala_http_api.Lens

  case class StateUserRepositoryL(users: Seq[User])

  val forState: UserRepositoryL[State[StateUserRepositoryL, ?]] =
    new UserRepositoryL[State[StateUserRepositoryL, ?]] {
      def all: State[StateUserRepositoryL, Seq[User]] = State {
        case state@StateUserRepositoryL(users) => (state, users)
      }
      def save(user: User): State[StateUserRepositoryL, Unit] = State {
        case StateUserRepositoryL(users) => (StateUserRepositoryL(user +: users), ())
      }
    }

  implicit def userRepositoryfromLens[S](implicit lens: Lens[S, StateUserRepositoryL]): UserRepositoryL[State[S, ?]] =
    new UserRepositoryL[State[S, ?]] {
      def all: State[S, Seq[User]] = forState.all.lift
      def save(user: User): State[S, Unit] = forState.save(user).lift
    }

}
