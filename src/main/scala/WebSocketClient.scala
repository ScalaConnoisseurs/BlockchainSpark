import akka.actor.{ActorSystem, ActorRef}
import akka.io.IO
import spray.can.server.UHttp
import spray.can.websocket.frame.{TextFrame, Frame}
import spray.can.{websocket, Http}
import spray.http.HttpRequest

abstract class WebSocketClient(actorSystem: ActorSystem, connect: Http.Connect, val upgradeRequest: HttpRequest) extends websocket.WebSocketClientWorker {
  IO(UHttp)(actorSystem) ! connect

  var receivers: List[ActorRef] = List()

  def businessLogic: Receive = {
    case frame: Frame =>
      onMessage(frame)
    case _: Http.ConnectionClosed =>
      onClose()
      context.stop(self)
    case s: websocket.UpgradedToWebSocket.type =>
      println("Websocket connection established")
      connection ! TextFrame("{\"op\":\"unconfirmed_sub\"}")
    case SubscribeReceiver(receiverActor: ActorRef) =>
      println("received subscribe from %s".format(receiverActor.toString()))
      receivers = receivers.+:(receiverActor)
    case UnsubscribeReceiver(receiverActor: ActorRef) =>
      println("received unsubscribe from %s".format(receiverActor.toString()))
      receivers = receivers.dropWhile(x => x eq receiverActor)
  }

  def onMessage(frame: Frame)
  def onClose()
}
