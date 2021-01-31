/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import EqT.doubleImplicit.{ approxT => dblapp, eqv => dbleqv }

trait ApproxT[D, T] extends EqT[T]
{ def precisionDefault: D
  def approxT(op1: T, op2: T, precision: D): Boolean
}

object ApproxT
{
  implicit val doubleImplicit: ApproxT[Double, Double] = new ApproxT[Double, Double]
  { override def eqv(a1: Double, a2: Double): Boolean = a1 == a2
    override def precisionDefault: Double = 1e-12
    override def approxT(op1: Double, op2: Double, precision: Double): Boolean =  (op1 - op2).abs <= precision
  }

  implicit def arrayDblImplicit: ApproxT[Double, Array[Double]] = new ApproxT[Double, Array[Double]]
  {
    override def precisionDefault: Double = 1e-12

    override def eqv(a1: Array[Double], a2: Array[Double]): Boolean =
    {
      val len = a1.length
      if (len != a2.length) false
      else {
        var res = true
        var count = 0
        while(count < len & res == true)
        { if (a1(count) != a2(count)) res = false
          count += 1
        }
        res
      }
    }

    override def approxT(op1: Array[Double], op2: Array[Double], precision: Double): Boolean =
    {
      val len = op1.length
      if (len != op2.length) false
      else {
        var res = true
        var count = 0
        while(count < len & res == true)
        { if (op1(count).!=~(op2(count), precision)) res = false
          count += 1
        }
        res
      }
    }
  }
}

case class Approx2DblsT[T](fArg1: T => Double, fArg2: T => Double, precisionDefault: Double = EqT.doubleImplicit.precisionDefault) extends ApproxT[Double, T]
{ override def approxT(op1: T, op2: T, precision: Double): Boolean = dblapp(fArg1(op1), fArg1(op2), precision) & dblapp(fArg2(op1), fArg2(op2), precision)
  override def eqv(a1: T, a2: T): Boolean = dbleqv(fArg1(a1), fArg1(a2)) & dbleqv(fArg2(a1), fArg2(a2))
}