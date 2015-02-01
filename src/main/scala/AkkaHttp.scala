
import akka.actor.ActorSystem
import spray.http.{HttpMethods, HttpRequest, Uri}

//import spray.http._

//implicit val system: ActorSystem = ActorSystem()
//val timeout: Timeout = Timeout(15.seconds)
//import system.dispatcher // implicit execution context

object AkkaHttp extends  App {
   val system = ActorSystem("police-data-client")

  def callPoliceApi() {
    val homeUri = Uri("http://data.police.uk/api/crime-last-updated")
    //IO(Http) ? HttpRequest(GET, Uri("http://spray.io"))).mapTo[HttpResponse] : println("Np IO");
    //println(HttpRequest(GET, uri = homeUri).withHeaders(headers.`Content-Type`.apply(ContentType.apply(MediaTypes.`application/json`))).entity);
    println(HttpRequest(HttpMethods.GET, uri = homeUri));
  }

  callPoliceApi()

}
