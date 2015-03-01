package model.bitcoin

import akka.actor.ActorRef

case class SubscribeReceiver(receiverActor: ActorRef)
case class UnsubscribeReceiver(receiverActor: ActorRef)
