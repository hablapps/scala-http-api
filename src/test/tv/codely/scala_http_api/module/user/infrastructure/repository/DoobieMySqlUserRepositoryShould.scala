package tv.codely.scala_http_api.module.user.infrastructure.repository

import tv.codely.scala_http_api.module.user.UserIntegrationTestCase
import tv.codely.scala_http_api.module.user.domain.UserStub
import doobie.implicits._
import org.scalatest.BeforeAndAfterEach

// TODO(jfuentes): is really necessary to make this an integration test?
final class DoobieMySqlUserRepositoryShould extends UserIntegrationTestCase with BeforeAndAfterEach {
  private def cleanUsersTable(): Unit =
    sql"TRUNCATE TABLE users".update.run
      .transact(doobieDbConnection.transactor)
      .unsafeToFuture()
      .map(_ => ())
      .futureValue

  override protected def beforeEach(): Unit = {
    super.beforeEach()
    cleanUsersTable()
  }

  "return an empty sequence if there're no users" in {
    repository.all().futureValue shouldBe Seq.empty
  }

  "search all existing users" in {
    val users = UserStub.randomSeq

    users.foreach(u => repository.save(u).futureValue)

    repository.all().futureValue shouldBe users
  }
}


// TODO(jfuentes): It doesn't make sense this test? It makes sense but we're not testing
// any business logic, just the state implementation of the API
import tv.codely.scala_http_api.module.user.domain.User

final class StateUserRepositoryLShould extends org.scalatest.WordSpec with org.scalatest.Matchers { // extends UserIntegrationTestCase with BeforeAndAfterEach {

  // TODO(jfuentes): Move this to a trait as they were doing?
  val repository = MockUserRepositoryL.forState

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
