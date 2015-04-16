import akka.actor._
import org.apache.spark.streaming.receiver.ActorHelper

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
