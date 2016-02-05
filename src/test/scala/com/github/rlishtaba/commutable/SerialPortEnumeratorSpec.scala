package com.github.rlishtaba.commutable

import org.scalatest._

class SerialPortEnumeratorSpec extends FlatSpec with Matchers {
  it should "return an array of ports" in {
    val portNames = Utils.rs232portNames
    portNames shouldBe a[AnyRef]
  }
}