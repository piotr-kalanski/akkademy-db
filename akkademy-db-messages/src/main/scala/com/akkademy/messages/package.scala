package com.akkademy

package object messages {
  case class SetRequest(key: String, value: Object)
  case class GetRequest(key: String)
  case class SetIfNotExistsRequest(key: String, value: Object)
  case class DeleteRequest(key: String)
  case class KeyNotFoundException(key: String) extends Exception
}
