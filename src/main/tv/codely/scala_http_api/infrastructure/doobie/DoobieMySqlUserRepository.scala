package tv.codely.scala_http_api.module.user.infrastructure.repository

import doobie.implicits._
import tv.codely.scala_http_api.module.user.domain.{User, UserRepository}
import tv.codely.scala_http_api.module.shared.persistence.infrastructure.doobie.TypesConversions._
import tv.codely.scala_http_api.module.shared.persistence.infrastructure.doobie.DoobieDbConnection

import scala.concurrent.{ExecutionContext, Future}
import cats.effect.IO

final case class DoobieMySqlUserRepository()(implicit 
  db: DoobieDbConnection[IO],
  executionContext: ExecutionContext)
extends UserRepository[Future] {

  override def all(): Future[Seq[User]] = {
    db.read(sql"SELECT user_id, name FROM users".query[User].to[Seq])
      .unsafeToFuture
  }

  override def save(user: User): Future[Unit] =
    sql"INSERT INTO users(user_id, name) VALUES (${user.id}, ${user.name})".update.run
      .transact(db.transactor)
      .unsafeToFuture()
      .map(_ => ())
}
