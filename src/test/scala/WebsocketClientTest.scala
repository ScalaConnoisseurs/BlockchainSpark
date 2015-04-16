import akka.actor.{Props, ActorSystem, Actor}
import akka.actor.Actor.Receive
import akka.testkit.{ImplicitSender, TestKit, TestActorRef}
import spray.can.Http
import spray.can.server.UHttp
import spray.can.websocket.frame.TextFrame
import spray.http.{HttpMethods, HttpRequest}

import scala.util.Success
import scala.concurrent.duration._
import scala.concurrent.Await
import akka.pattern.ask
import org.scalatest._
import org.scalatest.concurrent.{ScalaFutures, Futures}
import org.scalatest.time.{Span, Seconds}

class WebsocketClientTest extends Matchers with WordSpecLike with BeforeAndAfterAll {

  implicit val system = ActorSystem("test-system")
  val webSocketActorRef: TestActorRef[WebSocketClient] = TestActorRef(Props(new WebSocketClient(null, null)))
  val webSocketActor = webSocketActorRef.underlyingActor

  "The websocket client " must {

    webSocketActorRef ! "start"
    webSocketActorRef ! UHttp.Upgraded // Switch to business logic

    "make http connection when start message is received" in {
      // TODO
    }

  }

}