import akka.actor.ActorSystem
import org.scalatest._
import org.scalatest.concurrent.{ScalaFutures, Futures}
import org.scalatest.time.{Span, Seconds}
import org.threeten.bp.LocalDate


class HttpClientTest extends FlatSpec with Matchers with ScalaFutures {

  implicit val actorSystem = ActorSystem("police-data-client")

  val httpClient = new HttpClient()
  
  "A Client" should "return Crime Last updated date" in {
    whenReady(httpClient.getLastUpdatedDate, timeout(Span(3, Seconds))) { result =>
       println(result)
    }
  }

  it should "extract Date from Police json" in {

    httpClient.dateFromJson("""{"date":"2014-11-01"}""") should be (LocalDate.of(2014, 11, 1))

  }



}
