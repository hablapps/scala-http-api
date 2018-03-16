package tv.codely.scala_http_api.module.user.infrastructure.repository

import doobie.implicits._
import tv.codely.scala_http_api.module.user.domain.{User, UserRepository, UserRepositoryL}
import tv.codely.scala_http_api.module.shared.persistence.infrastructure.doobie.TypesConversions._
import tv.codely.scala_http_api.module.shared.persistence.infrastructure.doobie.DoobieDbConnection

import scala.concurrent.{ExecutionContext, Future}

final class DoobieMySqlUserRepository(db: DoobieDbConnection)(implicit executionContext: ExecutionContext)
    extends UserRepository {
  override def all(): Future[Seq[User]] = {
    db.read(sql"SELECT user_id, name FROM users".query[User].to[Seq])
  }

  override def save(user: User): Future[Unit] =
    sql"INSERT INTO users(user_id, name) VALUES (${user.id}, ${user.name})".update.run
      .transact(db.transactor)
      .unsafeToFuture()
      .map(_ => ())
}

/////////////

final class DoobieMySqlUserRepositoryL(db: DoobieDbConnection)(implicit executionContext: ExecutionContext)
    extends UserRepositoryL[Future] {
  override def all: Future[Seq[User]] = {
    db.read(sql"SELECT user_id, name FROM users".query[User].to[Seq])
  }

  override def save(user: User): Future[Unit] =
    sql"INSERT INTO users(user_id, name) VALUES (${user.id}, ${user.name})".update.run
      .transact(db.transactor)
      .unsafeToFuture()
      .map(_ => ())
}

case class StateUserRepositoryL(users: Seq[User])
object MockUserRepositoryL {

  type State[S, A] = S => (A, S)

  val apply: UserRepositoryL[State[StateUserRepositoryL, ?]] =
    new UserRepositoryL[State[StateUserRepositoryL, ?]] {
      def all: StateUserRepositoryL => (Seq[User], StateUserRepositoryL) = {
        case state@StateUserRepositoryL(users) => (users, state)
      }
      def save(user: User): StateUserRepositoryL => (Unit, StateUserRepositoryL) = {
        case StateUserRepositoryL(users) => ((), StateUserRepositoryL(user +: users))
      }
    }

  def apply[S](
      from: S => StateUserRepositoryL,
      to: (StateUserRepositoryL, S) => S): UserRepositoryL[State[S, ?]] =
    new UserRepositoryL[State[S, ?]] {
      def all: S => (Seq[User], S) = { s =>
        val userRepository = from(s)
        val (out, userRepository2) = apply.all(userRepository)
        (out, to(userRepository2, s))
      }
      def save(user: User): S => (Unit, S) = { s =>
        val userRepository = from(s)
        val (out, userRepository2) = apply.save(user)(userRepository)
        (out, to(userRepository2, s))
      }
    }
}
