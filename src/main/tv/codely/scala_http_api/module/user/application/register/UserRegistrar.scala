package tv.codely.scala_http_api.module.user.application.register

import tv.codely.scala_http_api.module.shared.bus.domain.MessagePublisher
import tv.codely.scala_http_api.module.shared.user.domain.UserId
import tv.codely.scala_http_api.module.user.domain._

// TODO(jfuentes): We're sending the response w/o waiting for the user to be created
final class UserRegistrar(repository: UserRepository, publisher: MessagePublisher) {
  def register(id: UserId, name: UserName): Unit = {
    val user = User(id, name)

    repository.save(user)

    publisher.publish(UserRegistered(user))
  }
}

import tv.codely.scala_http_api.module.shared.bus.domain.MessagePublisherL
import tv.codely.scala_http_api.module.user.infrastructure.repository.StateUserRepositoryL
import tv.codely.scala_http_api.module.user.infrastructure.repository.MockUserRepositoryL
import tv.codely.scala_http_api.module.shared.bus.infrastructure.rabbit_mq.StateMessagePublisherL
import tv.codely.scala_http_api.module.shared.bus.infrastructure.rabbit_mq.MockMessagePublisherL
import cats.Monad
import cats.syntax.all._

trait UserRegistrarL[P[_]] {
  def register(id: UserId, name: UserName): P[Unit]
}

object UserRegistrarL {
  implicit def instance[P[_]](implicit
      repository: UserRepositoryL[P],
      publisher: MessagePublisherL[P],
      monad: Monad[P]): UserRegistrarL[P] = new UserRegistrarL[P] {
    def register(id: UserId, name: UserName): P[Unit] = {
      val user = User(id, name)

      for {
        _ <- repository.save(user)
        _ <- publisher.publish(UserRegistered(user))
      } yield ()
    }

  }
}

case class StateUserRegistrarL(userRepository: StateUserRepositoryL, messagePublisher: StateMessagePublisherL)
object MockUserRegistrarL
    extends UserRegistrarL[λ[α => StateUserRegistrarL => (α, StateUserRegistrarL)]] {
  private val mockUserRepository = MockUserRepositoryL.apply
  def register(id: UserId, name: UserName): StateUserRegistrarL => (Unit, StateUserRegistrarL) = {
    case StateUserRegistrarL(userRepository, messagePublisher) =>
      val user = User(id, name)

      val (_, userRepository2) = mockUserRepository.save(user)(userRepository)
      val (_, messagePublisher2) = MockMessagePublisherL.publish(UserRegistered(user))(messagePublisher)

      ((), StateUserRegistrarL(userRepository2, messagePublisher2))
  }
}
