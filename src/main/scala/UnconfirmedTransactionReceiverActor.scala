import akka.actor._
import akka.io.IO
import model.bitcoin.{UnsubscribeReceiver, SubscribeReceiver}
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.receiver.{ActorHelper, Receiver}
import spray.can.websocket.frame.{Frame, PingFrame, PongFrame, TextFrame}
import spray.can.{Http, websocket}
import spray.http.{HttpHeaders, HttpMethods, HttpRequest}

import scala.reflect.ClassTag

class UnconfirmedTransactionReceiverActor[T: ClassTag](urlOfPublisher: String)
  extends Actor with ActorHelper {

  private val remotePublisher = context.actorSelection(urlOfPublisher)

  override def preStart() = remotePublisher ! SubscribeReceiver(context.self)

  def receive = {
    case msg => store(msg.asInstanceOf[T])
  }

  override def postStop() = remotePublisher ! UnsubscribeReceiver(context.self)

}
