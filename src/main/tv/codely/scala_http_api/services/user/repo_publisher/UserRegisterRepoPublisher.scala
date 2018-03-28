// package tv.codely.scala_http_api.module.user.application.register

// import tv.codely.scala_http_api.module.shared.bus.domain.MessagePublisher
// import tv.codely.scala_http_api.module.shared.user.domain.UserId
// import tv.codely.scala_http_api.module.user.domain._
// import cats.Apply, cats.syntax.apply._

// final class UserRegisterRepoPublisher[P[_]](
//   repository: UserRepository[P], 
//   publisher: MessagePublisher[P])(implicit
//   Ap: Apply[P]) 
// extends UserRegister[P]{

//   def register(id: UserId, name: UserName): P[Unit] = {
//     val user = User(id, name)

//     repository.save(user) *>
//     publisher.publish(UserRegistered(user))
//   }
// }

// object UserRegisterRepoPublisher{
//   def instance[P[_]](implicit
//       repository: UserRepository[P],
//       publisher: MessagePublisher[P],
//       Ap: Apply[P]) = 
//     new UserRegistrar(repository, publisher)
// }
