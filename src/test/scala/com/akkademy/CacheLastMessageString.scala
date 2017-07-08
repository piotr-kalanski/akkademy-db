package com.akkademy

import akka.actor.ActorSystem
import akka.testkit.TestActorRef
import com.akkademy.messages.SetRequest
import org.scalatest.{BeforeAndAfterEach, FunSpecLike, Matchers}

class CacheLastMessageString extends FunSpecLike with Matchers with BeforeAndAfterEach {
  implicit val system = ActorSystem()

  describe("homework") {
    val actorRef = TestActorRef(new CacheLastMessage)
    val cacheLastMessage = actorRef.underlyingActor
    describe("given first message") {
      it("should cache first message") {
        actorRef ! "s1"
        cacheLastMessage.cache should equal("s1")
      }
    }
    describe("given second message") {
      it("should cache second message") {
        actorRef ! "s2"
        cacheLastMessage.cache should equal("s2")
      }
    }
  }
}
