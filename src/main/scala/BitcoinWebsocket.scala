import akka.actor.Actor._
import akka.actor._
import akka.io.IO
import model.bitcoin.UnconfirmedTransaction
import model.bitcoin.TransactionJsonProtocol._
import spray.can.{websocket, Http}
import spray.can.server.UHttp
import spray.can.websocket.frame._
import spray.http._

import scala.collection.mutable

class BitcoinWebsocket(actorSystem: ActorSystem) {

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
  import spray.json._

  actorSystem.actorOf(Props(
    new WebSocketClient(actorSystem, connect, req) {

      def onMessage(frame: Frame) {
        frame match {
          case _: PongFrame =>
          case _: PingFrame => connection ! PongFrame()
          case TextFrame(text) =>
            val textString = text.utf8String
            val transaction = JsonParser(textString).asJsObject.fields.get("x").get.convertTo[UnconfirmedTransaction]

            receivers.foreach(_ ! transaction)
        }
      }

      def onClose() {
        println("Websocket closed")
      }
    }
  ), "Bitcoin")

}