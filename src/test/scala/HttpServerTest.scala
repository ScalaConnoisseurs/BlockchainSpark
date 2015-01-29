import org.scalatest.FlatSpec
import spray.testkit.ScalatestRouteTest

class HttpServerTest extends FlatSpec with ScalatestRouteTest with HttpRoutes {
  
  def actorRefFactory = system // connect the DSL to the test ActorSystem

  "The server" should "respond with pong when pinged" in {
    Get("/ping") ~> pingRoute ~> check {
      responseAs[String] === "PONG"
    }
  }

}
