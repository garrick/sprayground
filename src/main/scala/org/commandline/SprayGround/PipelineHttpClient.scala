package org.commandline.SprayGround

import akka.actor.ActorSystem

import scala.concurrent.{ExecutionContext, Future}
import spray.http._
import spray.client.pipelining._
import spray.json.DefaultJsonProtocol
import spray.httpx.SprayJsonSupport._

import spray.httpx.encoding.{Deflate, Gzip}

case class Params(paras: Int, theType: String)
case class HipsterJesus(text: String, params: Params)

object MyJsonProtocol extends DefaultJsonProtocol {
  val API = "http://hipsterjesus.com/api/?type=hipster-centric&html=false"
//  import spray.json._
  implicit val paramsJsonFormat = jsonFormat(Params.apply, "paras","type")
  implicit val hipsterJesusJsonFormat = jsonFormat(HipsterJesus.apply, "text","params")
}

class PipelineHttpClient(implicit ec: ExecutionContext) {
  import MyJsonProtocol._

  implicit val system = ActorSystem()
  implicit val formatter = MyJsonProtocol

  val pipeline: HttpRequest => Future[HipsterJesus] = (
    addHeader("X-My-Special-Header", "fancy-value")
      ~> addCredentials(BasicHttpCredentials("bob", "secret"))
      ~> encode(Gzip)
      ~> sendReceive
      ~> decode(Deflate)
      ~> unmarshal[HipsterJesus]
    )

  def getOrderConfirmation: Future[HipsterJesus] = {
    val response: Future[HipsterJesus] =
      pipeline(Get(MyJsonProtocol.API, ""))
    response
  }
}
