package tv.codely.scala_http_api.application.repositories.doobieImpl

import tv.codely.scala_http_api.application.repositories.doobieImpl.DoobieMySqlUserRepository

trait UserIntegrationTestCase extends IntegrationTestCase {
  protected val repository = DoobieMySqlUserRepository[cats.effect.IO]
}
