import akka.actor.{ActorSystem, ActorRef}
import akka.io.IO
import spray.can.server.UHttp
import spray.can.websocket.frame.{PingFrame, PongFrame, TextFrame, Frame}
import spray.can.{websocket, Http}
import spray.http.HttpRequest


class WebSocketClient(connect: Http.Connect, val upgradeRequest: HttpRequest) extends websocket.WebSocketClientWorker with Subscribable {
  
  context.become(stopped)

  def stopped: Receive = {
    case "start" =>
      IO(UHttp)(context.system) ! connect
      context.become(receive)
  }
  
  def businessLogic: Receive = {
    receiveSubscribeMessage orElse receiveMessage
  }

  def receiveMessage: Receive = {
    case _: PingFrame => connection ! PongFrame()
    case _: PongFrame =>
    case frame: Frame =>
      onMessage(frame)
    case _: Http.ConnectionClosed =>
      onClose()
      context.stop(self) // or try to reconnect ?
    case s: websocket.UpgradedToWebSocket.type =>
      println("Websocket connection established")
      onConnect()
  }
  
  def onMessage(frame: Frame) = {}
  
  def onConnect() = {}
  
  def onClose() {
    println("Websocket closed")
  }
}
