package tv.codely.scala_http_api
package recording.code
package template1

import application.user.api.{UserId, UserName, User}
import effects.bus.api.Message

/** 
 * 1. APIs como interfaces abstractas convencionales
 */
object ConventionalAPIs{

  // Implementación asíncrona para los Futures de Scala

  import scala.concurrent.Future

  trait UserRepositoryAsyncScala{
    def all(): Future[Seq[User]]
    def save(user: User): Future[Unit]
  }

  type UserRepository = UserRepositoryAsyncScala

  // Implementación asíncrona para cats IO

  import cats.effect.IO

  trait UserRepositoryAsyncCats{
    def all(): IO[Seq[User]]
    def save(user: User): IO[Unit]
  }

  // Implementación para testing unitario funcional

  case class UserState(users: List[User])

  trait UserRepositoryPureUnitTesting{
    def all(): UserState => (UserState, Seq[User])
    def save(user: User): UserState => (UserState, Unit)
  }

  // Otras APIs

  type MessagePublisher = MessagePublisherVoid

  trait MessagePublisherVoid{
    def publish(message: Message): Unit
  }

  type UserRegister = UserRegisterAsync

  trait UserRegisterAsync{
    def register(id: UserId, name: UserName): Future[Unit]
  }
}

/** 
 * 2. ¡APIs como type classes!
 */
object FunctionalAPIs{

  trait UserRepository[P[_]]{
    def all(): P[Seq[User]]
    def save(user: User): P[Unit]
  }

  trait UserRegister[P[_]]{
    def register(id: UserId, name: UserName): P[Unit]
  }

  trait MessagePublisher[P[_]]{
    def publish(message: Message): P[Unit]
  }

}

/**
 * 3. Probemos que, efectivamente, podemos implementar todos estos
 * tipos de instancias
 */
object Refactoring{

  import FunctionalAPIs._

  // Implementación asíncrona para los Futures de Scala

  import scala.concurrent.Future

  // trait UserRepositoryAsyncScala{
  //   def all(): Future[Seq[User]]
  //   def save(user: User): Future[Unit]
  // }

  type UserRepositoryAsyncScala = UserRepository[Future]



  // Implementación asíncrona para cats IO

  import cats.effect.IO

  // trait UserRepositoryAsyncCats{
  //   def all(): IO[Seq[User]]
  //   def save(user: User): IO[Unit]
  // }

  type UserRepositoryAsyncCats = UserRepository[IO]



  // Implementación para testing unitario funcional

  case class UserState(users: List[User])

  // trait UserRepositoryTrans{
  //   def all(): UserState => (UserState, Seq[User])
  //   def save(user: User): UserState => (UserState, Unit)
  // }

  type Action[T] = UserState => (UserState, T)

  type UserRepositoryTrans = UserRepository[Action]

  // trait MessagePublisherVoid{
  //   def publish(message: Message): Unit
  // }

  type Id[T] = T

  type MessagePublisherVoid = UserRepository[Id]

}


/**
 * 4.¿Y qué pasa con las instancias cuando hacemos esa refactorización?
 */
object Instances{

  import doobie.implicits._
  import effects.repositories.doobie.TypesConversions._
  import scala.concurrent.{ExecutionContext, Future}
  type DoobieDbConnection = effects.repositories.doobie.DoobieDbConnection[Future]
  import cats.implicits._

  import FunctionalAPIs.UserRepository

  final class DoobieMySqlUserRepository(
    db: DoobieDbConnection)(implicit 
    executionContext: ExecutionContext) extends UserRepository[Future] {
    
    override def all(): Future[Seq[User]] = {
      db.read(sql"SELECT user_id, name FROM users".query[User].to[Seq])
    }

    override def save(user: User): Future[Unit] =
      sql"INSERT INTO users(user_id, name) VALUES (${user.id}, ${user.name})".update.run
        .transact(db.transactor)
        .map(_ => ())
  }
  
}
