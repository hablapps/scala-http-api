package tv.codely.scala_http_api.module.user.domain

import scala.concurrent.Future

trait UserRepository {
  def all(): Future[Seq[User]]

  def save(user: User): Future[Unit]
}

trait UserRepositoryL[P[_]] {
  def all: P[Seq[User]]
  def save(user: User): P[Unit]
}
