package tv.codely.scala_http_api.module.user.infrastructure.repository

// TODO(jfuentes): It doesn't make sense this test? It makes sense but we're not testing
// any business logic, just the state implementation of the API
import tv.codely.scala_http_api.module.user.domain.User
import UserRepositoryL.StateUserRepositoryL
import tv.codely.scala_http_api.module.user.domain._

final class StateUserRepositoryLShould extends org.scalatest.WordSpec with org.scalatest.Matchers {

  // TODO(jfuentes): Move this to a trait as they were doing?
  val repository = UserRepositoryL.forState

  "return an empty sequence if there're no users" in {
    val emptyRepository = StateUserRepositoryL(Seq.empty[User])
    val (resState, resUsers) = repository.all.run(emptyRepository)
    resUsers shouldBe Seq.empty[User]
    resState shouldBe emptyRepository
  }

  "search all existing users" in {
    val users = UserStub.randomSeq

    val initialState = StateUserRepositoryL(users)

    val (resState, resUsers) = repository.all.run(initialState)

    resUsers shouldBe users
    resState shouldBe initialState
  }
}