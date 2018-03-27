package tv.codely.scala_http_api.module.user.application.register

import tv.codely.scala_http_api.module.shared.bus.domain.MessagePublisher
import tv.codely.scala_http_api.module.shared.user.domain.UserId
import tv.codely.scala_http_api.module.user.domain._
import cats.Apply, cats.syntax.apply._

final class UserRegistrar[P[_]](repository: UserRepository[P], publisher: MessagePublisher[P]) {
  def register(id: UserId, name: UserName)(implicit Ap: Apply[P]): P[Unit] = {
    val user = User(id, name)

    repository.save(user) *>
    publisher.publish(UserRegistered(user))
  }
}
