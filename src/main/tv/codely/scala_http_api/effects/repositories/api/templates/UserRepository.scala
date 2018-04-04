package tv.codely.scala_http_api
package effects.repositories.api.template

import application.user.api.User
import scala.concurrent.Future

/** 
 * PRIMER PASO: ABSTRACCIÓN
 */

trait UserRepository{
  def all(): Future[Seq[User]]
  def save(user: User): Future[Unit]
}


