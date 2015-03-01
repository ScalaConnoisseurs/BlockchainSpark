package persistence.bitcoin

import akka.actor.{ActorRef, ActorSelection, ActorSystem, Actor}
import model.bitcoin.{UnsubscribeReceiver, SubscribeReceiver, UnconfirmedTransaction}
import scala.reflect.ClassTag
import com.datastax.driver.core.utils.UUIDs;

class UnconfirmedTransactionWriterActor[T:ClassTag](urlOfPublisher : String) extends Actor with CassandraConfig {

  val session = cluster.connect("bitcoin_spark")
  val preparedStatement = session.prepare("INSERT INTO UnconfirmedTransaction(id, version, size, hash) VALUES (?, ?, ?, ?);")

  def saveUnconfirmedTransaction(transaction : UnconfirmedTransaction): Unit = {
       session.executeAsync(preparedStatement.bind(
         UUIDs.timeBased(),
         new Integer(transaction.ver),
         new Integer(transaction.size),
         transaction.hash))
       println("Persisted trx: " + transaction.tx_index)
  }

  private val remotePublisher: ActorSelection = context.actorSelection(urlOfPublisher)

  override def preStart() = remotePublisher ! SubscribeReceiver(context.self)

  override def postStop() = remotePublisher ! UnsubscribeReceiver(context.self)

  override def receive: Receive =  {
    case trx: UnconfirmedTransaction => saveUnconfirmedTransaction(trx)
  }
}
