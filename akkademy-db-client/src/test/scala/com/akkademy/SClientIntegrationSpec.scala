package com.akkademy

import com.akkademy.messages.KeyNotFoundException
import org.scalatest.{FunSpecLike, Matchers}

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.language.postfixOps

class SClientIntegrationSpec extends FunSpecLike with Matchers {
  val client = new SClient("127.0.0.1:2552")

  describe("akkademyDbClient") {
    it("should set a value"){
      client.set("123", new Integer(123))
      val futureResult = client.get("123")
      val result = Await.result(futureResult, 10 seconds)
      result should equal(123)
    }

    it("set value twice"){
      client.set("k1", new Integer(1))
      client.set("k1", new Integer(2))
      val futureResult = client.get("k1")
      val result = Await.result(futureResult, 10 seconds)
      result should equal(2)
    }

    it("get missing key"){
      val futureResult = client.get("not existing")
      intercept[KeyNotFoundException] {
        Await.result(futureResult, 10 seconds)
      }
    }

    it("setIfNotExists value twice"){
      client.setIfNotExists("k_setIfNotExists", new Integer(1))
      Thread.sleep(100)
      client.setIfNotExists("k_setIfNotExists", new Integer(2))
      Thread.sleep(100)
      val futureResult = client.get("k_setIfNotExists")
      val result = Await.result(futureResult, 10 seconds)
      result should equal(1)
    }

    it("set and delete"){
      client.set("k1", new Integer(1))
      client.delete("k1")
      val futureResult = client.get("k1")
      intercept[KeyNotFoundException] {
        Await.result(futureResult, 10 seconds)
      }
    }
  }
}

