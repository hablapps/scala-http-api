package tv.codely.scala_http_api.entry_point

import com.typesafe.config.Config

final case class HttpServerConfig(host: String, port: Int)

object HttpServerConfig {
  def apply(serverConfig: Config): HttpServerConfig = HttpServerConfig(
    serverConfig.getString("http-server.host"),
    serverConfig.getInt("http-server.port"))
}

