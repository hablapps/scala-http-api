package tv.codely.scala_http_api.effects.repositories.doobie

import tv.codely.scala_http_api.effects.repositories.api._
import tv.codely.scala_http_api.services.api.user.User

case class StateUserRepository(users: Seq[User])

object StateUserRepository {

  import tv.codely.scala_http_api.State
  import tv.codely.scala_http_api.Lens

  val forState: UserRepository[State[StateUserRepository, ?]] =
    new UserRepository[State[StateUserRepository, ?]] {
      def all: State[StateUserRepository, Seq[User]] = State {
        case state@StateUserRepository(users) => (state, users)
      }
      def save(user: User): State[StateUserRepository, Unit] = State {
        case StateUserRepository(users) => (StateUserRepository(user +: users), ())
      }
    }

  implicit def userRepositoryfromLens[S](implicit lens: Lens[S, StateUserRepository]): UserRepository[State[S, ?]] =
    new UserRepository[State[S, ?]] {
      def all: State[S, Seq[User]] = forState.all.lift
      def save(user: User): State[S, Unit] = forState.save(user).lift
    }

}
