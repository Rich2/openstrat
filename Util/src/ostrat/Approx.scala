/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

trait Approx[D] extends Any with Equals
{ def precisionDefault: D
  def approx(that: Any, precision: D): Boolean
}

trait ApproxDbl extends Any with Approx[Double]
{ override def precisionDefault: Double = 1e-12
}