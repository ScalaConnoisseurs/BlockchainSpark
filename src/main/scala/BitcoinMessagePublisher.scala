import akka.actor._
import akka.io.IO
import spray.can.server.UHttp
import spray.can.websocket.FrameCommandFailed
import spray.can.websocket.frame.{TextFrame, BinaryFrame}
import spray.can.{websocket, Http}
import spray.http.HttpRequest
import spray.routing.HttpServiceActor


class WebSocketServer extends Actor with ActorLogging {

  var receivers: List[ActorRef] = List()
  
  def receive = {
    // when a new connection comes in we register a WebSocketConnection actor as the per connection handler
    case Http.Connected(remoteAddress, localAddress) =>
      val serverConnection = sender()
      val conn = context.actorOf(WebSocketWorker.props(serverConnection))
      serverConnection ! Http.Register(conn)
      receivers = receivers.+:(conn)
  }
}
object WebSocketWorker {
  def props(serverConnection: ActorRef) = Props(classOf[WebSocketWorker], serverConnection)
}

class WebSocketWorker(val serverConnection: ActorRef) extends HttpServiceActor with websocket.WebSocketServerWorker {
  final case class Push(msg: String)

  override def receive = handshaking orElse businessLogicNoUpgrade orElse closeLogic

  def businessLogic: Receive = {
    // just bounce frames back for Autobahn testsuite
    case x@(_: BinaryFrame | _: TextFrame) =>
      sender() ! x

    case Push(msg) => send(TextFrame(msg))

    case x: FrameCommandFailed =>
      log.error("frame command failed", x)

    case x: HttpRequest => // do something
  }

  def businessLogicNoUpgrade: Receive = {
    //      implicit val refFactory: ActorRefFactory = context
    runRoute {
      getFromResourceDirectory("webapp")
    }
  }
}

class BitcoinMessagePublisher(actorSystem: ActorSystem) {

  def doMain() {

    val server = actorSystem.actorOf(Props(classOf[WebSocketServer]), "websocket")

    IO(UHttp)(actorSystem) ! Http.Bind(server, "localhost", 8090)

  }

  // because otherwise we get an ambiguous implicit if doMain is inlined
  doMain()
  
  def send() = {

  }
}