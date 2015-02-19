package model.bitcoin

import spray.json._

case class UnconfirmedTransaction(lockTime: Int, ver: Int, size: Int, inputs: Array[Input], time: Long, tx_index: Long, vin_sz: Int, hash: String)

case class Input(sequence: Long)

object TransactionJsonProtocol extends DefaultJsonProtocol {
  implicit object UnconfirmedTransactionFormat extends RootJsonFormat[UnconfirmedTransaction] {
    def write(uct: UnconfirmedTransaction) = ???
      //JsArray(JsNumber(uct.ver), JsNumber(c.red), JsNumber(c.green), JsNumber(c.blue))

    def read(value: JsValue) = value.asJsObject.getFields("size", "tx_index", "lock_time", "out", "vout_sz", "hash", "inputs", "ver", "relayed_by", "time", "vin_sz") match {
      case Seq(JsNumber(size), JsNumber(tx_index), JsNumber(lock_time), JsArray(out), JsNumber(vout_sz), JsString(hash), JsArray(inputs), JsNumber(ver), JsString(relayed_by),
      JsNumber(time), JsNumber(vin_sz)) =>
        new UnconfirmedTransaction(lock_time.toInt, ver.toInt, size.toInt, inputs.map(_.convertTo[Input]).toArray, time.toLong, tx_index.toLong, vin_sz.toInt, hash)
      case _ => deserializationError("Unconfirmed transaction expected")
    }
    
  //implicit val unconfirmedTransactionFormat = jsonFormat6(UnconfirmedTransaction.apply)
  }
  implicit val inputFormat = jsonFormat1(Input)

}