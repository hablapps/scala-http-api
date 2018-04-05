package tv.codely.scala_http_api
package recording.code
package template2

import application.user.api.{UserId, UserName, UserRegistered, User}

/**
 * 1. Situación inicial de nuestra lógica de negocio: acoplada al futuro
 */
object ConventionalLogic{

  import ConventionalAPIs.{UserRepository, MessagePublisher, UserRegister}
  import scala.concurrent.{ExecutionContext, Future}

  final case class UserRegisterRepoPublisher(
    repository: UserRepository, 
    publisher: MessagePublisher)(implicit 
    ec: ExecutionContext) 
  extends UserRegister{

    def register(id: UserId, name: UserName): Future[Unit] = {
      val user = User(id, name)

      repository.save(user).flatMap{ _ : Unit =>
        Future(publisher.publish(UserRegistered(user)): Unit)
      }
    }
  }
}

/**
 * 2. Cambiamos a nuestras super-apis, y ¡ya no compila!
 * Podríamos hacer un apañito ...
 */
object FunctionalLogicNotReally{
  import FunctionalAPIs.{UserRepository, MessagePublisher, UserRegister}
  import scala.concurrent.{ExecutionContext, Future}
  import cats.Id

  final case class UserRegisterRepoPublisher(
    repository: UserRepository[Future], 
    publisher: MessagePublisher[Id])(implicit 
    ec: ExecutionContext) 
  extends UserRegister[Future]{

    def register(id: UserId, name: UserName): Future[Unit] = {
      val user = User(id, name)

      repository.save(user).flatMap{ _ =>
        Future(publisher.publish(UserRegistered(user)): Unit)
      }
    }
  }
}

/**
 * 3. Pero de lo que se trata es de hacer una lógica de negocio
 * genérica, que valga para cualquier interpretación de nuestras APIs.
 */

object FunctionalLogicI{

  import FunctionalAPIs.{UserRepository, MessagePublisher, UserRegister}
  import scala.concurrent.{ExecutionContext, Future}
  import cats.Id, cats.FlatMap, cats.syntax.flatMap._

  final case class UserRegisterRepoPublisher[P[_]](
    repository: UserRepository[P], 
    publisher: MessagePublisher[P])(implicit 
    F: FlatMap[P]) 
  extends UserRegister[P]{

    def register(id: UserId, name: UserName): P[Unit] = {
      val user = User(id, name)

      (repository.save(user): P[Unit]).flatMap{ _ =>
        publisher.publish(UserRegistered(user))
      }
    }
  }
}


object FunctionalLogicII{

  import FunctionalAPIs.{UserRepository, MessagePublisher, UserRegister}
  import cats.Apply, cats.syntax.apply._
  
  final case class UserRegisterRepoPublisher[P[_]](
    repository: UserRepository[P], 
    publisher: MessagePublisher[P])(implicit 
    F: Apply[P])
  extends UserRegister[P]{

    def register(id: UserId, name: UserName): P[Unit] = {
      val user = User(id, name)

      repository.save(user) *>
      publisher.publish(UserRegistered(user))
    }
  }  

}
