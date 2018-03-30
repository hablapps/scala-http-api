package tv.codely.scala_http_api.module.user.infrastructure.repository

import tv.codely.scala_http_api.module.user.domain.User
import tv.codely.scala_http_api.module.user.domain._

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
