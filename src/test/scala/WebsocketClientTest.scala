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

class WebsocketClientTest extends TestKit(ActorSystem("testSystem")) with ImplicitSender with Matchers with WordSpecLike with BeforeAndAfterAll {

  val webSocketActorRef: TestActorRef[WebSocketClient] = TestActorRef(new WebSocketClient(null, null))
  val webSocketActor = webSocketActorRef.underlyingActor

  val subscriber = TestActorRef[Actor]
  
  "The websocket client " must {
    
    webSocketActorRef ! UHttp.Upgraded // Switch to business logic
    
    "add new subscriber to its list of receivers when subscribe message is received" in {
      webSocketActorRef ! SubscribeReceiver(subscriber)

      webSocketActor.receivers should contain(subscriber)
    }

    "remove subscriber from its list of reveivers when unsubscribe message is received" in {
      webSocketActorRef ! UnsubscribeReceiver(subscriber)

      webSocketActor.receivers shouldNot contain(subscriber)
    }
  }

}
