package com.akkademy

import akka.actor.ActorSystem
import akka.pattern.ask
import akka.util.Timeout
import com.akkademy.messages._

import scala.concurrent.Future
import scala.concurrent.duration._

class SClient(remoteAddress: String){
  private implicit val timeout = Timeout(2 seconds)
  private implicit val system = ActorSystem("LocalSystem")
  private val remoteDb = system.actorSelection(s"akka.tcp://akkademy@$remoteAddress/user/akkademy-db")

  def set(key: String, value: Object): Future[Any] = {
    remoteDb ? SetRequest(key, value)
  }

  def setIfNotExists(key: String, value: Object): Future[Any] = {
    remoteDb ? SetIfNotExistsRequest(key, value)
  }

  def get(key: String): Future[Any] = {
    remoteDb ? GetRequest(key)
  }

  def delete(key: String): Unit = {
    remoteDb ! DeleteRequest(key)
  }
}