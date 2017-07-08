package com.exercises

import akka.actor.{Actor, Status}
import akka.event.Logging

class ReverseStringActor extends Actor {
  val log = Logging(context.system, this)
  override def receive: Receive = {
    case s:String =>
      log.info("received s: {}", s)
      sender() ! s.reverse
    case o =>
      log.info("received unknown message: {}", o)
      sender() ! Status.Failure(new Exception("Unknown message"))
  }
}
