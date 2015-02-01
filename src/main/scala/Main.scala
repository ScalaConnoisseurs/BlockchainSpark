import akka.actor.ActorSystem
import spray.routing.SimpleRoutingApp

object Main extends SimpleRoutingApp with HttpRoutes {
  implicit val system = ActorSystem("PoliceSpark")

  def main(args: Array[String]) {
    val port = parsePort(args)
    startServer(interface = "0.0.0.0", port) {
      pingRoute
    }
  }
  
  def parsePort(args: Array[String]): Integer = 
    try {
      Integer.parseInt(args(0))
    } catch {
      case e: Exception =>
        println("Defaulting to port 8080")
        8080
    }
}