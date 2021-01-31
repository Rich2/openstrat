/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

trait ApproxT[D, T]
{ def precisionDefault: D
  def approxT(op1: T, op2: T, precision: D): Boolean
}

object ApproxT
{
  implicit val doubleImplicit: ApproxT[Double, Double] = new ApproxT[Double, Double]
  {  override def precisionDefault: Double = 1e-12
    override def approxT(op1: Double, op2: Double, precision: Double): Boolean =  (op1 - op2).abs <= precision
  }

  implicit def optionImplicit[D, A](implicit ev: ApproxT[D, A]): ApproxT[D, Option[A]] = new ApproxT[D, Option[A]]
  { override def precisionDefault: D = ev.precisionDefault

    override def approxT(op1: Option[A], op2: Option[A], precision: D): Boolean = (op1, op2) match {
      case (Some(a1), Some(a2)) => ev.approxT(a1, a2, precision)
      case (None, None) => true
      case _ => false
    }
  }

  implicit def arrayDblImplicit: ApproxT[Double, Array[Double]] = new ApproxT[Double, Array[Double]]
  {
    override def precisionDefault: Double = 1e-12

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

case class Approx2DblsT[T](fArg1: T => Double, fArg2: T => Double, precisionDefault: Double = 1e-12) extends ApproxT[Double, T]
{ override def approxT(op1: T, op2: T, precision: Double): Boolean = fArg1(op1) =~(fArg1(op2), precision) & fArg2(op1) =~(fArg2(op2), precision)
}