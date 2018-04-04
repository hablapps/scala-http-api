package tv.codely.scala_http_api.effects.repositories.state

import tv.codely.scala_http_api.application.api.user.User
import tv.codely.scala_http_api.application.stubs.user.UserStub

final class StateUserRepositoryShould extends org.scalatest.WordSpec with org.scalatest.Matchers {

  val repository = StateUserRepository.forState

  "return an empty sequence if there're no users" in {
    val emptyRepository = StateUserRepository(Seq.empty[User])
    val (resState, resUsers) = repository.all.run(emptyRepository)
    resUsers shouldBe Seq.empty[User]
    resState shouldBe emptyRepository
  }

  "search all existing users" in {
    val users = UserStub.randomSeq

    val initialState = StateUserRepository(users)

    val (resState, resUsers) = repository.all.run(initialState)

    resUsers shouldBe users
    resState shouldBe initialState
  }
}
