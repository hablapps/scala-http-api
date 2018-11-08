package tv.codely.scala_http_api.application.repositories.doobieImpl

import tv.codely.scala_http_api.application.repositories.doobieImpl.DoobieMySqlVideoRepository

trait VideoIntegrationTestCase extends IntegrationTestCase {
  protected val repository = DoobieMySqlVideoRepository[cats.effect.IO]
}
