import spray.http._
import spray.client.pipelining._
import akka.actor.ActorSystem

import scala.concurrent.Future


object HttpClient extends App {
  
  implicit val system = ActorSystem("police-data-client")
  import system.dispatcher // execution context for futures

  val pipeline: HttpRequest => Future[HttpResponse] = sendReceive

  def callPoliceApi() {
    pipeline(Get("http://data.police.uk/api/crime-last-updated")).onComplete(response => println(response.get.toString))
  }
  
  callPoliceApi()
}
