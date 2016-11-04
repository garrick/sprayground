package org.commandline.SprayGround.daos


import slick.driver.PostgresDriver.api._
import slick.backend.DatabaseConfig
import slick.driver.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

object ArtistDaoSingleton extends ArtistDao

class ArtistDao {
  implicit val ec: ExecutionContext = ExecutionContext.Implicits.global
  val db = DatabaseConfig.forConfig[JdbcProfile]("database").db
  val testConnectionQuery = sql"select 1".as[Int]

  def checkDatabaseConnection: Future[Any] = {
    db.run(testConnectionQuery).transform(
      _ => "OK",
      cause => new Exception(s"Failed: $cause", cause))
  }

}
