/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import EqT.doubleImplicit.{ approxT => dblapp, eqv => dbleqv }

trait Approx[D] extends Any with Equals
{ def defaultDelta: D
  def approx(that: Any, delta: D): Boolean
}

trait ApproxDbl extends Approx[Double]
{ override def defaultDelta: Double = 1e-12
}

trait ApproxT[D, T] extends EqT[T]
{ def defaultDelta: D
  def approxT(op1: T, op2: T, delta: D): Boolean
}

object ApproxT
{
  implicit def arrayDblImplicit: ApproxT[Double, Array[Double]] = new ApproxT[Double, Array[Double]]
  {
    override def defaultDelta: Double = 1e-12

    override def eqv(a1: Array[Double], a2: Array[Double]): Boolean =
    {
      val len = a1.length
      if (len != a1.length) false
      else {
        var res = true
        var count = 0
        while(count < len & res == true)
          {
            if (a1(count) != a2(count)) res = false
            count += 1
          }
        ???
      }
    }
    override def approxT(op1: Array[Double], op2: Array[Double], delta: Double): Boolean = ???


  }
}


case class Approx2DblsT[T](fArg1: T => Double, fArg2: T => Double, defaultDelta: Double = EqT.doubleImplicit.defaultDelta) extends ApproxT[Double, T]
{ override def approxT(op1: T, op2: T, delta: Double): Boolean = dblapp(fArg1(op1), fArg1(op2), delta) & dblapp(fArg2(op1), fArg2(op2), delta)
  override def eqv(a1: T, a2: T): Boolean = dbleqv(fArg1(a1), fArg1(a2)) & dbleqv(fArg2(a1), fArg2(a2))
}