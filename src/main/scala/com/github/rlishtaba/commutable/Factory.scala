package com.github.rlishtaba.commutable

import scala.collection.mutable.{Map => params}

object Factory {
  def newRs232Layer(port: String, options: params[String, String]): layers.Rs232 = {
    new layers.Rs232(port, options)
  }

  def newEthernetLayer(ip: String, port: Int): layers.Ethernet = {
    new layers.Ethernet(ip, port)
  }
}
