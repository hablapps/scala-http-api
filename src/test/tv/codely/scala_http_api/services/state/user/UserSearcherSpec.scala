package tv.codely.scala_http_api.services.api.user

import tv.codely.scala_http_api.State
import tv.codely.scala_http_api.effects.repositories.doobie.StateUserRepository
import tv.codely.scala_http_api.effects.repositories.api.UserStub

final class UsersSearcherRepoSpec extends org.scalatest.WordSpec with org.scalatest.Matchers {
  private val searcher = UsersSearcherRepo[State[StateUserRepository, ?]]

  "search all existing users" in {
    val existingUser        = UserStub.random
    val anotherExistingUser = UserStub.random
    val existingUsers       = Seq(existingUser, anotherExistingUser)

    val initialState = StateUserRepository(existingUsers)

    val (finalState, resOutput) = searcher.all.run(initialState)

    resOutput shouldBe existingUsers
    finalState shouldBe initialState
  }
}
