import akka.actor._
import akka.io.IO
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.receiver.{ActorHelper, Receiver}
import spray.can.server.UHttp
import spray.can.websocket.frame.{Frame, PingFrame, PongFrame, TextFrame}
import spray.can.{Http, websocket}
import spray.http.{HttpHeaders, HttpMethods, HttpRequest}

import scala.reflect.ClassTag

case class SubscribeReceiver(receiverActor: ActorRef)
case class UnsubscribeReceiver(receiverActor: ActorRef)

class UnconfirmedTransactionReceiverActor[T: ClassTag](urlOfPublisher: String)
  extends Actor with ActorHelper {

  private val remotePublisher = context.actorSelection(urlOfPublisher)

  override def preStart() = remotePublisher ! SubscribeReceiver(context.self)

  def receive = {
    case msg => store(msg.asInstanceOf[T])
  }

  override def postStop() = remotePublisher ! UnsubscribeReceiver(context.self)

}
