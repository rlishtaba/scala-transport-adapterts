package com.github.rlishtaba.commutable

trait Errors {

  case class OutOfMockDataError(message: String) extends Exception

  case class EagainError(message: String) extends Exception

}
