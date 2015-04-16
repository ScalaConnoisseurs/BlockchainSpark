import akka.actor.Actor.Receive
import akka.actor.ActorRef

case class SubscribeReceiver(receiverActor: ActorRef)

case class UnsubscribeReceiver(receiverActor: ActorRef)

trait Subscribable {

  var receivers: List[ActorRef] = List()

  def receiveSubscribeMessage: Receive = {
    case SubscribeReceiver(receiverActor: ActorRef) =>
      println("received subscribe from %s".format(receiverActor.toString()))
      println(Thread.currentThread().getName)
      receivers = receivers.+:(receiverActor)
    case UnsubscribeReceiver(receiverActor: ActorRef) =>
      println("received unsubscribe from %s".format(receiverActor.toString()))
      receivers = receivers.dropWhile(x => x eq receiverActor)
  }

}
