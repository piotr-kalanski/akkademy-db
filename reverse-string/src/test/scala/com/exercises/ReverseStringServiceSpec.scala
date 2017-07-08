package com.exercises

import org.scalatest.{BeforeAndAfterEach, FunSpecLike, Matchers}

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.language.postfixOps

class ReverseStringServiceSpec extends FunSpecLike with Matchers with BeforeAndAfterEach {
  describe("service") {
    val service = new ReverseStringService
    describe("reverse string") {
      it("abc reverse") {
        val futureResult = service.reverse("abc")
        val result = Await.result(futureResult, 10 seconds)
        result should equal("cba")
      }
    }
  }
}
