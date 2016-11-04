package org.commandline.SprayGround.daos.tables

import java.util.UUID

import slick.driver.PostgresDriver.api._

object ArtistsSingleton extends Artists

class Artists extends TableQuery(new ArtistsModel(_)){
}

case class Artist(id: UUID, first: String, last: String, group_name: String)

class ArtistsModel(tag: Tag) extends Table[(UUID, String, String, String)](tag, Some("sprayground"), "artists") {
  //Columns
  def id = column[UUID]("id")
  def first = column[String]("first")
  def last = column[String]("last")
  def group_name = column[String]("group_name")
  def * = (id, first, last, group_name)
  //Projections
}
