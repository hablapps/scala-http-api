package tv.codely.scala_http_api.module.user.application.register

import tv.codely.scala_http_api.module.shared.bus.domain.MessagePublisher
import tv.codely.scala_http_api.module.shared.user.domain.UserId
import tv.codely.scala_http_api.module.user.domain._
import scala.concurrent.Future
import cats.Id

final class UserRegistrar(repository: UserRepository[Future], publisher: MessagePublisher[Id]) {
  def register(id: UserId, name: UserName): Unit = {
    val user = User(id, name)

    repository.save(user)

    publisher.publish(UserRegistered(user))
  }
}
