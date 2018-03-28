package tv.codely.scala_http_api.module.user.application.register

import tv.codely.scala_http_api.module.shared.bus.domain.MessagePublisherL
import tv.codely.scala_http_api.module.user.infrastructure.repository.UserRepositoryL,
  UserRepositoryL.StateUserRepositoryL
import tv.codely.scala_http_api.module.shared.bus.domain.MessagePublisherL.StateMessagePublisherL
import tv.codely.scala_http_api.module.shared.user.domain._
import tv.codely.scala_http_api.module.user.domain._
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

  import tv.codely.scala_http_api.Lens
  case class StateUserRegistrarL(
    userRepository: StateUserRepositoryL,
    messagePublisher: StateMessagePublisherL)
  implicit val userLens = Lens[StateUserRegistrarL, StateUserRepositoryL](
    _.userRepository,
    ur => s => s.copy(userRepository = ur))
  implicit val msgLens = Lens[StateUserRegistrarL, StateMessagePublisherL](
    _.messagePublisher,
    mp => s => s.copy(messagePublisher = mp))
}
