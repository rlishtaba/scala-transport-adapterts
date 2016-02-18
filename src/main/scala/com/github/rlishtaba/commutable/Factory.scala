package com.github.rlishtaba.commutable

private[commutable] trait FactoryMethods {
  def newRs232Layer(port: String, baudRate: Int = 9600, dataBits: Int = 8, stopBits: Int = 1, parity: Int = 0): layers.Transport = {
    new layers.Rs232(port, baudRate, dataBits, stopBits, parity)
  }

  def newEthernetLayer(ip: String, port: Int): layers.Transport = {
    new layers.Ethernet(ip, port)
  }
}

object Factory extends FactoryMethods
