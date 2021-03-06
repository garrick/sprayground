package org.commandline.SprayGround

import akka.actor.{ActorSystem, Props}
import akka.io.IO
import spray.can.Http

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}
import daos.ArtistDaoSingleton


object Main extends App  {

  implicit val system = ActorSystem()
  implicit val ec = scala.concurrent.ExecutionContext.global

  // the handler actor replies to incoming HttpRequests
  val handler = system.actorOf(Props[HelloWorldService], name = "handler")

  IO(Http) ! Http.Bind(handler, interface = "localhost", port = 8080)
  val dbCheck = ArtistDaoSingleton.checkDatabaseConnection
    dbCheck.onComplete {
    case Success(x) => println(x)
    case Failure(y) => println(y)
  }
}