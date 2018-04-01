package tv.codely.scala_http_api.services.api.user

trait UserRegister[P[_]]{
  def register(id: UserId, name: UserName): P[Unit]
}
