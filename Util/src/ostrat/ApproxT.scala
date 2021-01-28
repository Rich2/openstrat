/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

trait Approx[D] extends Any with Equals
{ def defaultDelta: D
  def approx(that: Any, delta: D): Boolean
}

trait ApproxT[D, T] extends EqT[T]
{
  def defaultDelta: D
  def approxT(op1: T, op2: T, delta: D): Boolean
}

import EqT.doubleImplicit.{ approxT => dblapp, eqv => dbleqv}

case class Approx2DblsT[T](fArg1: T => Double, fArg2: T => Double, defaultDelta: Double = EqT.doubleImplicit.defaultDelta) extends ApproxT[Double, T]
{ override def approxT(op1: T, op2: T, delta: Double): Boolean = dblapp(fArg1(op1), fArg1(op2), delta) & dblapp(fArg2(op1), fArg2(op2), delta)
  override def eqv(a1: T, a2: T): Boolean = dbleqv(fArg1(a1), fArg1(a2)) & dbleqv(fArg2(a1), fArg2(a2))
}