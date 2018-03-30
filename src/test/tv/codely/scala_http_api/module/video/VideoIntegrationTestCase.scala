package tv.codely.scala_http_api
package module.video

import tv.codely.scala_http_api.module.IntegrationTestCase
import tv.codely.scala_http_api.module.video.infrastructure.repository.DoobieMySqlVideoRepository

protected[video] trait VideoIntegrationTestCase extends IntegrationTestCase {
  protected val repository = DoobieMySqlVideoRepository.apply
}
