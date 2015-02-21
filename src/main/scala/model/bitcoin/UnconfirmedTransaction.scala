package model.bitcoin

import spray.json._

case class UnconfirmedTransaction(lockTime: Int, ver: Int, size: Int, inputs: Array[Input], time: Long, tx_index: Long, vin_sz: Int, hash: String, outputs: Array[Outputs])

case class Input(sequence: Long, previousOut: PreviousOut)

case class PreviousOut(address: String)

case class Outputs(address: String)

object PreviousOutJsonProtocol extends DefaultJsonProtocol {
  implicit object PreviousOutTransactionFormat extends  RootJsonFormat[PreviousOut] {
    override def write(obj: PreviousOut): JsValue = ???

    override def read(json: JsValue): PreviousOut = json.asJsObject.getFields("addr") match {
      case Seq(JsString(address)) => new PreviousOut(address)      
    }
  }
}

object InputJsonProtocol extends DefaultJsonProtocol {
  import PreviousOutJsonProtocol._
  implicit object InputTransactionFormat extends RootJsonFormat[Input] {
    override def write(obj: Input): JsValue = ???

    override def read(json: JsValue): Input = json.asJsObject.getFields("sequence", "prev_out") match {
      case Seq(JsNumber(sequence), prevOut) => new Input(sequence.toLong, prevOut.convertTo[PreviousOut])
    }
  }
}

object OutputsJsonProtocol extends DefaultJsonProtocol {
  implicit object OutputsTransactionFormat extends RootJsonFormat[Outputs] {
    override def read(json: JsValue): Outputs = json.asJsObject.getFields("addr") match {
      case Seq(JsString(address)) => new Outputs(address)
    }

    override def write(obj: Outputs): JsValue = ???
  }
}

object TransactionJsonProtocol extends DefaultJsonProtocol {
  import InputJsonProtocol._
  import OutputsJsonProtocol._
  
  implicit object UnconfirmedTransactionFormat extends RootJsonFormat[UnconfirmedTransaction] {
    def write(uct: UnconfirmedTransaction) = ???

    def read(value: JsValue) = value.asJsObject.getFields("size", "tx_index", "lock_time", "out", "vout_sz", "hash", "inputs", "ver", "relayed_by", "time", "vin_sz") match {
      case Seq(JsNumber(size), JsNumber(tx_index), JsNumber(lock_time), JsArray(outputs), JsNumber(vout_sz), JsString(hash), JsArray(inputs), JsNumber(ver), JsString(relayed_by),
    JsNumber(time), JsNumber(vin_sz)) =>
        new UnconfirmedTransaction(lock_time.toInt, ver.toInt, size.toInt, inputs.map(_.convertTo[Input]).toArray, time.toLong, tx_index.toLong, vin_sz.toInt, hash, outputs.map(_.convertTo[Outputs]).toArray)
      case _ => deserializationError("Unconfirmed transaction expected")
    }
  }

}