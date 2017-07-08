package com.akkademy

import akka.actor.Actor
import akka.event.Logging
import com.akkademy.messages.SetRequest

import scala.collection.mutable

class CacheLastMessage extends Actor {
  var cache: String = ""
  val log = Logging(context.system, this)
  override def receive: Receive = {
    case s:String => {
      log.info("received string: {}", s)
      cache = s
    }
    case o => log.info("received unknown message: {}", o)
  }
}
