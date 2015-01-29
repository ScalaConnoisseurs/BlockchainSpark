import akka.actor.{ActorSystem, ActorRefFactory, Props}
import spray.routing.SimpleRoutingApp

object Main extends App with SimpleRoutingApp with HttpRoutes {
  implicit val system = ActorSystem("system")

  startServer(interface = "localhost", port = 80) {
    pingRoute
  }
}