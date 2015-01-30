import akka.actor.ActorSystem
import akka.http.Http
import akka.http.model._
import akka.io.IO
import spray.client.pipelining._
import HttpMethods._
import HttpProtocols._
import MediaTypes._
import akka.http.model.headers._
import scala.concurrent.Future
import scala.concurrent.duration._

import akka.actor.ActorSystem
import akka.util.Timeout
import akka.pattern.ask
import akka.io.IO

import spray.can.Http
//import spray.http._

//implicit val system: ActorSystem = ActorSystem()
//val timeout: Timeout = Timeout(15.seconds)
//import system.dispatcher // implicit execution context

/**
 * Created by michaeldecourci on 19/01/2015.
 */
object AkkaHttp extends  App {
   val system = ActorSystem("police-data-client")

  def callPoliceApi() {
    val homeUri = Uri("http://data.police.uk/api/crime-last-updated")
    //IO(Http) ? HttpRequest(GET, Uri("http://spray.io"))).mapTo[HttpResponse] : println("Np IO");
    //println(HttpRequest(GET, uri = homeUri).withHeaders(headers.`Content-Type`.apply(ContentType.apply(MediaTypes.`application/json`))).entity);
    println(HttpRequest(GET, uri = homeUri));
  }

  callPoliceApi()

}
