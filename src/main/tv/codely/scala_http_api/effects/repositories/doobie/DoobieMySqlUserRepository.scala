package tv.codely.scala_http_api.effects.repositories.doobie

import doobie.implicits._
import tv.codely.scala_http_api.services.api.user.User
import tv.codely.scala_http_api.effects.repositories.api.UserRepository
import tv.codely.scala_http_api.effects.repositories.doobie.TypesConversions._

import cats.Functor, cats.syntax.functor._
import cats.effect.Async

final case class DoobieMySqlUserRepository[P[_]: Async: Functor]()(implicit 
  db: DoobieDbConnection[P])
extends UserRepository[P] {

  override def all(): P[Seq[User]] = {
    db.read(sql"SELECT user_id, name FROM users".query[User].to[Seq])
  }

  override def save(user: User): P[Unit] =
    sql"INSERT INTO users(user_id, name) VALUES (${user.id}, ${user.name})".update.run
      .transact(db.transactor)
      .map(_ => ())
}