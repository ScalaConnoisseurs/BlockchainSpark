
import spray.http._
import spray.client.pipelining._
import akka.actor.ActorSystem

import scala.concurrent.Future

class HttpClient(implicit actorSystem: ActorSystem) {

  import actorSystem.dispatcher // execution context for futures

  val pipeline: HttpRequest => Future[HttpResponse] = sendReceive

  def getLastUpdatedDate: Future[String] = {
    // TODO: Add map(String=>Date)
    pipeline(Get("http://data.police.uk/api/crime-last-updated")).map(response => response.message.entity.data.asString)
  }
  

}
