package com.exercises

import akka.actor.ActorSystem
import akka.testkit.TestActorRef
import org.scalatest.{BeforeAndAfterEach, FunSpecLike, Matchers}
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.language.postfixOps

class ReverseStringActorSpec extends FunSpecLike with Matchers with BeforeAndAfterEach {
  implicit val system = ActorSystem()
  implicit val timeout = Timeout(5 seconds)

  describe("actor") {
    val actorRef = TestActorRef(new ReverseStringActor)
    describe("reverse string") {
      it("abc reverse") {
        val futureResult = actorRef ? "abc"
        val result = Await.result(futureResult, 10 seconds)
        result should equal("cba")
      }
      it("wrong input") {
        val futureResult = actorRef ? Seq()
        intercept[Exception] {
          Await.result(futureResult, 10 seconds)
        }
      }
      it("reverse list of string") {
        import scala.concurrent.ExecutionContext.Implicits.global
        val data = Seq("123", "abc")
        val futureResult = Future.sequence(data.map(s => actorRef ? s))
        val result = Await.result(futureResult, 10 seconds)
        result should equal(Seq("321", "cba"))
      }
    }
  }
}
