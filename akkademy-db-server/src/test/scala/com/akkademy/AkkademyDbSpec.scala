package com.akkademy

import akka.actor.ActorSystem
import akka.pattern.ask
import akka.testkit.TestActorRef
import akka.util.Timeout
import com.akkademy.messages._
import org.scalatest.{BeforeAndAfterEach, FunSpecLike, Matchers}
import scala.concurrent.Await
import scala.concurrent.duration._

class AkkademyDbSpec extends FunSpecLike with Matchers with BeforeAndAfterEach {
  implicit val system = ActorSystem()
  implicit val timeout = Timeout(5 seconds)

  describe("akkademy") {
    val actorRef = TestActorRef(new AkkademyDb)
    val akkademyDb = actorRef.underlyingActor
    describe("given SetRequest") {
      it("should place key/value into map") {
        actorRef ! SetRequest("key", "value")
        akkademyDb.map.get("key") should equal(Some("value"))
      }
    }
    describe("given SetRequest and GetRequest") {
      it("should return value") {
        actorRef ! SetRequest("key", "value")
        val futureResult = actorRef ? GetRequest("key")
        val result = Await.result(futureResult, 1 second)
        result should equal("value")
      }
    }
    describe("given SetIfNotExistsRequest") {
      it("shouldn't overwrite") {
        actorRef ! SetIfNotExistsRequest("key", "value")
        actorRef ! SetIfNotExistsRequest("key", "value2")
        val futureResult = actorRef ? GetRequest("key")
        val result = Await.result(futureResult, 1 second)
        result should equal("value")
      }
    }
    describe("given DeleteRequest and GetRequest") {
      it("should throw exception") {
        actorRef ! SetRequest("keydelete", "value")
        actorRef ! DeleteRequest("keydelete")
        val futureResult = actorRef ? GetRequest("keydelete")
        intercept[KeyNotFoundException] {
          Await.result(futureResult, 1 second)
        }
      }
    }
  }

  describe("homework - cache last message") {
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
