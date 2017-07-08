package com.akkademy

import akka.actor.{Status, Actor}
import akka.event.Logging
import com.akkademy.messages._

import scala.collection.mutable

class AkkademyDb extends Actor {
  val map = new mutable.HashMap[String, Object]()
  val log = Logging(context.system, this)
  override def receive: Receive = {
    case SetRequest(key, value) =>
      log.info("received SetRequest - key: {} value: {}", key, value)
      map.put(key, value)
      sender() ! Status.Success
    case GetRequest(key) =>
      log.info("received GetRequest - key: {}", key)
      val response: Option[Object] = map.get(key)
      response match{
        case Some(x) => sender() ! x
        case None => sender() ! Status.Failure(KeyNotFoundException(key))
      }
    case SetIfNotExistsRequest(key, value) =>
      log.info("received SetIfNotExistsRequest - key: {} value: {}", key, value)
      if(!map.contains(key))
        map.put(key, value)
    case DeleteRequest(key) =>
      log.info("received DeleteRequest - key: {}", key)
      map.remove(key)
    case o => log.info("received unknown message: {}", o)
  }
}