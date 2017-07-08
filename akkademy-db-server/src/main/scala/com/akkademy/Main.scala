package com.akkademy

import akka.actor.{ActorSystem, Props}

object Main extends App {
  val system = ActorSystem("akkademy")
  val helloActor = system.actorOf(Props[AkkademyDb], name = "akkademy-db")
}