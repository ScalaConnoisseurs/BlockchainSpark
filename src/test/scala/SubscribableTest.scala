import akka.actor.ActorSystem
import akka.testkit.TestKit
import org.scalatest.{Matchers, WordSpecLike}

class SubscribableTest extends TestKit(ActorSystem()) with Matchers with WordSpecLike {
  
  val subscribable = new Subscribable {}

  "Subsribable " must {

    "add new subscriber to its list of receivers when subscribe message is received" in {

      subscribable.receiveSubscribeMessage(SubscribeReceiver(testActor))
      
      subscribable.receivers should contain(testActor)
    }

    "remove subscriber from its list of reveivers when unsubscribe message is received" in {
      subscribable.receiveSubscribeMessage(UnsubscribeReceiver(testActor))

      subscribable.receivers shouldNot contain(testActor)
    }
  }
}
