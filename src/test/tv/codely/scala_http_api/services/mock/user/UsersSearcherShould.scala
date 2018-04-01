package tv.codely.scala_http_api.services.api.user

import tv.codely.scala_http_api.services.mock.UnitTestCase
import tv.codely.scala_http_api.effects.repositories.api.UserStub
import tv.codely.scala_http_api.effects.repositories.doobie.UserRepositoryMock

final class UsersSearcherRepoShould extends UnitTestCase with UserRepositoryMock {
  private val searcher = new UsersSearcherRepo()(repository)

  "search all existing users" in {
    val existingUser        = UserStub.random
    val anotherExistingUser = UserStub.random
    val existingUsers       = Seq(existingUser, anotherExistingUser)

    repositoryShouldFind(existingUsers)

    searcher.all().futureValue shouldBe existingUsers
  }
}
