package org.commandline.SprayGround

import akka.actor.{ActorSystem, Props}
import akka.io.IO
import spray.can.Http

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}


object Main extends App  {

  implicit val system = ActorSystem()
  implicit val ec = scala.concurrent.ExecutionContext.global

  // the handler actor replies to incoming HttpRequests
  val handler = system.actorOf(Props[HelloWorldService], name = "handler")
  val client = new PipelineHttpClient()

  val order =   client.getOrderConfirmation
  order.onComplete {
    case Success(o) => println(o.text)
    case Failure(e) => println("You have failed me for the last time"+e.toString); e.printStackTrace()
  }

  IO(Http) ! Http.Bind(handler, interface = "localhost", port = 8080)
}