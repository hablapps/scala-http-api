package tv.codely.scala_http_api.module.user.domain

trait UserRepository[P[_]] {
  def all(): P[Seq[User]]

  def save(user: User): P[Unit]
}
