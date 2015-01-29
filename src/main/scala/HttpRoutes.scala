import akka.actor.{ActorRefFactory, ActorSystem}
import spray.routing.{HttpServiceActor, HttpService, SimpleRoutingApp}

trait HttpRoutes extends HttpService {

  def pingRoute =
    get {
      path("ping") {
        complete("PONG!")
      }
    }
}
