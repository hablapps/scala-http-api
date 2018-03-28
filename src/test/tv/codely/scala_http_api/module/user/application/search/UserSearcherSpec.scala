package tv.codely.scala_http_api.module.user.application.search

import tv.codely.scala_http_api.State
import tv.codely.scala_http_api.module.user.infrastructure.repository.UserRepositoryL,UserRepositoryL.StateUserRepositoryL
import tv.codely.scala_http_api.module.user.domain.UserStub

final class UsersSearcherSpec extends org.scalatest.WordSpec with org.scalatest.Matchers {
  private val searcher = UsersSearcherL.instance[State[StateUserRepositoryL, ?]]

  "search all existing users" in {
    val existingUser        = UserStub.random
    val anotherExistingUser = UserStub.random
    val existingUsers       = Seq(existingUser, anotherExistingUser)

    val initialState = StateUserRepositoryL(existingUsers)

    val (finalState, resOutput) = searcher.all.run(initialState)

    resOutput shouldBe existingUsers
    finalState shouldBe initialState
  }
}
