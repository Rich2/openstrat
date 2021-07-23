/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

opaque type Logarithm = Double

object Logarithm
{ def apply(d: Double): Logarithm = math.log(d)
  def safe(d: Double): Option[Logarithm] = if (d > 0.0) Some(math.log(d)) else None
}

extension (x: Logarithm) {
  def toDouble: Double = math.exp (x)
  def + (y: Logarithm): Logarithm = x + y
  def * (y: Logarithm): Logarithm = x * y
  }
