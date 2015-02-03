import org.threeten.bp.LocalDate
import akka.actor.ActorSystem
import spray.http._
import spray.client.pipelining._
import spray.json._
import scala.concurrent.Future

class HttpClient(implicit actorSystem: ActorSystem) {


  import actorSystem.dispatcher // execution context for futures

  val pipeline: HttpRequest => Future[HttpResponse] = sendReceive

  def getLastUpdatedDate: Future[JsValue] = {
    // TODO: Add map(String=>Date)
    pipeline(Get("http://data.police.uk/api/crime-last-updated"))
      .map(response => response.message.entity.data.asString)
     .map(source => source.parseJson)
  }

  def dateFromJson(json: String): LocalDate = {
    val jsonAST: JsValue = json.parseJson
    val maybeDate: Option[JsValue] = jsonAST.asJsObject.fields.get("date")

    maybeDate match {
      case Some(dateString) =>
        // TODO: find alternative to replace the quotes
        LocalDate.parse(dateString.toString().replaceAll("\"", ""))

      case _ => throw new IllegalArgumentException("No date in json " + json)
    }

  }

  case class PoliceLastUpdated(date: LocalDate)

  object PoliceLastUpdatedReader extends JsonReader[PoliceLastUpdated] {
    override def read(json: JsValue): PoliceLastUpdated = ???
  }
}
