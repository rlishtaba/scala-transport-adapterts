package com.github.rlishtaba.commutable.layers

trait Transport extends OSILayer[Nothing, Array[Byte]] {
  def receive(nothing: Nothing) = {
    throw new UnsupportedOperationException("Cannot handle #receive. I am tail node in the stack.")
  }
}
