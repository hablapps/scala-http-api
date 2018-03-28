package tv.codely.scala_http_api.module.user.infrastructure.repository

import tv.codely.scala_http_api.module.user.domain._

case class StateUserRepositoryL(users: Seq[User])

object StateUserRepositoryL {

  import tv.codely.scala_http_api.State
  import tv.codely.scala_http_api.Lens

  val forState: UserRepository[State[StateUserRepositoryL, ?]] =
    new UserRepository[State[StateUserRepositoryL, ?]] {
      def all: State[StateUserRepositoryL, Seq[User]] = State {
        case state@StateUserRepositoryL(users) => (state, users)
      }
      def save(user: User): State[StateUserRepositoryL, Unit] = State {
        case StateUserRepositoryL(users) => (StateUserRepositoryL(user +: users), ())
      }
    }

  implicit def userRepositoryfromLens[S](implicit lens: Lens[S, StateUserRepositoryL]): UserRepository[State[S, ?]] =
    new UserRepository[State[S, ?]] {
      def all: State[S, Seq[User]] = forState.all.lift
      def save(user: User): State[S, Unit] = forState.save(user).lift
    }

}
