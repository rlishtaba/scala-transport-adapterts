package com.github.rlishtaba.commutable.layers

import java.io.IOException
import jssc.SerialPortEvent
import jssc.SerialPort
import jssc.SerialPortEventListener
import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Success, Try}

protected[commutable] class Rs232
(
  portName: String,
  baudRate: Int = 9600,
  dataBits: Int = 8,
  stopBits: Int = 1,
  parity: Int = 0
) extends Transport {
  private var connected: Boolean = false
  private val serialPortName = portName
  private val port = new SerialPort(serialPortName)
  private val eventMask = SerialPort.MASK_RXCHAR + SerialPort.MASK_CTS + SerialPort.MASK_DSR

  private val eventsListener = new SerialPortEventListener {
    override def serialEvent(event: SerialPortEvent) = {
      if (event.isRXCHAR) {
        if (0 < event.getEventValue) {
          swim(port.readBytes)
        }
      }
      else if (event.isCTS) {
        println("CTS line has changed state:")
        println("   -> " + event.getEventValue)
      }
      else if (event.isDSR) {
        println("DSR line has changed state:")
        println("   -> " + event.getEventValue)
      } else {
        println("Serial port propagated an event:")
        println("   -> " + event.getEventValue)
      }
    }
  }

  def pushDown(message: Array[Byte]) = Future {
    if (!isConnected) connect()
    port.writeBytes(message)
  } map { status =>
    if (status) message
    else throw new IOException("Could not finish IO#write operation.")
  }

  @throws[IOException]
  def connect(timeout: Int = 5): Boolean = {
    if (!isConnected) {
      port.openPort()
      port.setParams(baudRate, dataBits, stopBits, parity)
      port.setEventsMask(eventMask)
      port.addEventListener(eventsListener)
      connected = port.isOpened
    }
    isConnected
  }

  def disconnect: Boolean = {
    if (isConnected) {
      port.removeEventListener()
      port.closePort()
      connected = port.isOpened
    }
    !isConnected
  }

  def isConnected =
    connected
}
