package tv.codely.scala_http_api.effects.repositories.doobie

import doobie.implicits._
import tv.codely.scala_http_api.effects.repositories.api.{Video, VideoRepository}
import tv.codely.scala_http_api.effects.repositories.doobie.TypesConversions._

import cats.Functor, cats.syntax.functor._
import cats.effect.Async

final case class DoobieMySqlVideoRepository[P[_]: Async: Functor]()(implicit
  db: DoobieDbConnection[P])
extends VideoRepository[P] {

  override def all(): P[Seq[Video]] =
    db.read(sql"SELECT video_id, title, duration_in_seconds, category, creator_id FROM videos".query[Video].to[Seq])
  
  override def save(video: Video): P[Unit] =
    sql"INSERT INTO videos(video_id, title, duration_in_seconds, category, creator_id) VALUES (${video.id}, ${video.title}, ${video.duration}, ${video.category}, ${video.creatorId})".update.run
      .transact(db.transactor)
      .map(_ => ())
}
