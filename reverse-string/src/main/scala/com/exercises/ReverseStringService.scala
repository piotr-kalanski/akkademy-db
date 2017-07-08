package com.exercises

import akka.actor.{ActorSystem, Props}
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.Future
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

class ReverseStringService {
  private implicit val timeout = Timeout(2 seconds)
  private implicit val system = ActorSystem("LocalSystem")
  private val actor = system.actorOf(Props[ReverseStringActor], name = "reversestring")

  def reverse(s: String): Future[String] = {
    (actor ? s).map(a => a.toString)
  }
}
