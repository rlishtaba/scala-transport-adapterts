package com.github.rlishtaba.commutable.layers

import java.io.{IOException, InputStream, OutputStream}
import java.net.{InetSocketAddress, Socket}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

protected[commutable] class Ethernet(ip: String, port: Int) extends Transport {
  private val socket = new Socket
  private var connected: Boolean = false
  private var in: InputStream = null
  private var out: OutputStream = null
  private val address = new InetSocketAddress(ip, port)

  def connect(timeout: Int = 5): Boolean = {
    if (isConnected) {
      return isConnected
    }
    try {
      socket.connect(address)
      in = socket.getInputStream
      out = socket.getOutputStream
      connected = socket.isConnected
    } catch {
      case ex: IOException =>
        throw new IOException(s"Cannot establish communication with remote endpoint: $ex")
    }
    isConnected
  }

  def disconnect: Boolean = {
    if (!isConnected) {
      return isConnected
    }
    try {
      socket.close()
      socket.shutdownInput()
      socket.shutdownOutput()
      connected = socket.isConnected
    } catch {
      case ex: IOException =>
        println("Cannot close communication with remote endpoint.")
    }
    isConnected
  }

  def isConnected: Boolean = {
    connected
  }

  def pushDown(message: Array[Byte]) = Future {
    write(message)
    true
  } map { ok =>
    if (ok) message
    else throw new IOException("Could not finish IO#write operation.")
  }

  private def write(bytes: Array[Byte]): Boolean = {
    try {
      out.write(bytes)
      true
    } catch {
      case ex: IOException =>
        false
    }
  }

  def read(): Unit = {
    val bytes = recvNonblock(100)
    if (!bytes.isEmpty) swim(bytes)
  }

  private def recvNonblock(size: Int): Array[Byte] = {
    if (0 == available) Array[Byte]()
    else if (available < size) recv(available)
    else recv(size)
  }

  private def recv(size: Int): Array[Byte] = {
    val buffer = new Array[Byte](size)
    for (time <- 0 until size) {
      buffer(time) = in.read.toByte
    }
    buffer
  }

  private def available: Int = {
    in.available
  }
}
