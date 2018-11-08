package tv.codely.scala_http_api.application
package repositories
package doobieImpl

import doobie.implicits._
import user.User
import TypesConversions._

import cats.Monad, cats.syntax.functor._

final case class DoobieMySqlUserRepository[P[_]: Monad]()(
  implicit
  db: DoobieDbConnection[P]
) extends UserRepository[P] {

  override def all(): P[Seq[User]] = {
    db.read(sql"SELECT user_id, name FROM users".query[User].to[Seq])
  }

  override def save(user: User): P[Unit] =
    sql"INSERT INTO users(user_id, name) VALUES (${user.id}, ${user.name})".update.run
      .transact(db.transactor)
      .map(_ => ())
}
