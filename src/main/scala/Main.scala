import akka.actor.{Props, ActorSystem}
import model.bitcoin.UnconfirmedTransaction
import org.apache.spark.SparkConf
import org.apache.spark.streaming.{StreamingContext, Seconds}

object Main  {
  implicit val system = ActorSystem("PoliceSpark")

  def main(args: Array[String]) {

    system.logConfiguration()
    val bitcoinWebsocket = new BitcoinWebsocket(system)


    val sparkConf = new SparkConf()
      .setMaster("local[2]") // 2 cores
      .setAppName("Bitcoin")

    val ssc = new StreamingContext(sparkConf, Seconds(5))
    val lines = ssc.actorStream[UnconfirmedTransaction](
      Props(new UnconfirmedTransactionReceiverActor[UnconfirmedTransaction]("akka.tcp://PoliceSpark@127.0.0.1:2550/user/Bitcoin")), "UnconfirmedTransactionReceiverActor")

    lines.foreachRDD(_.foreach(println))
    ssc.start()
    ssc.awaitTermination()
  }

}