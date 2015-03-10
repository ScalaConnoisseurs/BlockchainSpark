import akka.actor.{ActorSystem, ActorRef}
import akka.io.IO
import spray.can.server.UHttp
import spray.can.websocket.frame.{TextFrame, Frame}
import spray.can.{websocket, Http}
import spray.http.HttpRequest


class WebSocketClient(connect: Http.Connect, val upgradeRequest: HttpRequest) extends websocket.WebSocketClientWorker {

  var receivers: List[ActorRef] = List()
  
  context.become(stopped)

  def stopped: Receive = {
    case "start" =>  {
      IO(UHttp)(context.system) ! connect
      context.become(receive)
    }
  }
  
  def businessLogic: Receive = {
    case frame: Frame =>
      onMessage(frame)
    case _: Http.ConnectionClosed =>
      onClose()
      context.stop(self) // or try to reconnect ?
    case s: websocket.UpgradedToWebSocket.type =>
      println("Websocket connection established")
      onConnect()
    case SubscribeReceiver(receiverActor: ActorRef) =>
      println("received subscribe from %s".format(receiverActor.toString()))
      receivers = receivers.+:(receiverActor)
    case UnsubscribeReceiver(receiverActor: ActorRef) =>
      println("received unsubscribe from %s".format(receiverActor.toString()))
      receivers = receivers.dropWhile(x => x eq receiverActor)
  }

  def onMessage(frame: Frame) = {}
  
  def onConnect() = {}
  
  def onClose() {
    println("Websocket closed")
  }
}
