import akka.actor.Actor.Receive
import akka.actor._
import akka.io.IO
import spray.can.{websocket, Http}
import spray.can.server.UHttp
import spray.can.websocket.FrameCommandFailed
import spray.can.websocket.frame._
import spray.http._
import spray.routing.HttpServiceActor

class BitcoinWebsocket(implicit actorSystem: ActorSystem) {

  abstract class WebSocketClient(connect: Http.Connect, val upgradeRequest: HttpRequest) extends websocket.WebSocketClientWorker {
    // Apparently we need to use 2 actor systems for 2 http clients.
    IO(UHttp)(ActorSystem("bitcoin-websoket")) ! connect

    def businessLogic: Receive = {
      case frame: Frame =>
        onMessage(frame)
      case _: Http.ConnectionClosed =>
        onClose()
        context.stop(self)
      case s: websocket.UpgradedToWebSocket.type =>
        println("Websocket connection established")
        connection ! TextFrame("{\"op\":\"unconfirmed_sub\"}")
    }

    def onMessage(frame: Frame)
    def onClose()
  }
  
  val ssl = false
  val host = "ws.blockchain.info"
  val port = 443
  val headers = List(
    HttpHeaders.Host(host, port),
    HttpHeaders.Connection("Upgrade"),
    HttpHeaders.RawHeader("Upgrade", "websocket"),
    HttpHeaders.RawHeader("Sec-WebSocket-Version", "13"),
    HttpHeaders.RawHeader("Sec-WebSocket-Key", "x3JJHMbDL1EzLkh9GBhXDw=="),
    HttpHeaders.RawHeader("Sec-WebSocket-Extensions", "permessage-deflate"))
  
  val connect = Http.Connect(host, port, sslEncryption = true)
  val req = HttpRequest(HttpMethods.GET, "/inv", headers)
 
  actorSystem.actorOf(Props(
    new WebSocketClient(connect, req) {
      def onMessage(frame: Frame) {
        frame match {
          case _: PongFrame =>
          case _: PingFrame => connection ! PongFrame()
          case TextFrame(text) => println(text.utf8String)
        }
      }

      def onClose() {
        println("Websocket closed")
      }
    }
  ))

}