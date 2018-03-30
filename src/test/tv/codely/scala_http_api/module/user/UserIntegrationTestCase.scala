package tv.codely.scala_http_api
package module.user

import tv.codely.scala_http_api.module.IntegrationTestCase
import tv.codely.scala_http_api.module.user.infrastructure.repository.DoobieMySqlUserRepository

protected[user] trait UserIntegrationTestCase extends IntegrationTestCase {
  protected val repository = DoobieMySqlUserRepository.apply
}
