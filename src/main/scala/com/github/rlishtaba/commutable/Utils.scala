package com.github.rlishtaba.commutable

import jssc.SerialPortList

object Utils {
  def rs232portNames: Array[String] = {
    SerialPortList.getPortNames()
  }
}
