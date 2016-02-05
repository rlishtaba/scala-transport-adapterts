package com.github.rlishtaba.commutable.layers

import scala.concurrent.Future

trait OSILayer[L, U] {
  private var upperLayer = None: Option[OSILayer[U, _]]
  private var lowerLayer = None: Option[OSILayer[_, L]]

  protected def swim(chunk: U): Unit = upperLayer match {
    case Some(upper) => upper.receive(chunk)
    case None => throw new UnsupportedOperationException("There is no upper layer linked with.")
  }

  protected def sink(chunk: L): Future[L] = lowerLayer match {
    case Some(lower) => lower.pushDown(chunk)
    case None => Future.failed(new UnsupportedOperationException("There is no lower layer linked with."))
  }

  def linkWith[A](upper: OSILayer[U, A]) = {
    upperLayer = Some(upper)
    upper.lowerLayer = Some(this)
    upper
  }

  protected def receive(chunk: L): Unit

  protected def pushDown(chunk: U): Future[U]
}
