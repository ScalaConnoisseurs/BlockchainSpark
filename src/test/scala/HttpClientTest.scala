import java.util.Date

import akka.actor.ActorSystem
import org.scalatest._
import org.scalatest.concurrent.{ScalaFutures, Futures}
import org.scalatest.Assertions._
import org.scalatest.time.{Span, Seconds}

class HttpClientTest extends FlatSpec with Matchers with ScalaFutures {

  implicit val actorSystem = ActorSystem("police-data-client")

  val httpClient = new HttpClient()
  
  "A Client" should "return Crime Last updated date" in {
    whenReady(httpClient.getLastUpdatedDate, timeout(Span(3, Seconds))) { result =>
       println(result)
    }
  }

}
