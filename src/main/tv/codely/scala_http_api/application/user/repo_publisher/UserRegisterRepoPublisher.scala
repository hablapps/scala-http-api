package tv.codely.scala_http_api
package application
package user
package repo_publisher

import cats.Apply, cats.syntax.apply._

import effects.bus.MessagePublisher
import repositories._

final case class UserRegisterRepoPublisher[P[_]]()(
  implicit
  repository: UserRepository[P],
  publisher: MessagePublisher[P],
  Ap: Apply[P]
) extends UserRegister[P] {

  def register(id: UserId, name: UserName): P[Unit] = {
    val user = User(id, name)

    repository.save(user) *>
      publisher.publish(UserRegistered(user))
  }
}
