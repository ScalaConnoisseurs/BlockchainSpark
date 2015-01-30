import org.scalatest._

/**
 * Created by michaeldecourci on 29/01/2015.
 */
class HttpClientTest extends FlatSpec with Matchers {
  "A Client" should "return Crime Last updated date" in {
    val httpClient = new HttpClient();
    httpClient.getLastUpdatedDate() should be new Date();
  }

//  it should "throw NoSuchElementException if an empty stack is popped" in {
//    val emptyStack = new Stack[Int]
//    a [NoSuchElementException] should be thrownBy {
//      emptyStack.pop()
//    }
//  }
}
