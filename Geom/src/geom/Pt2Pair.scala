/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

import scala.reflect.ClassTag

class Pt2Pair[A2](val x: Double, val y: Double, val a2: A2) extends PointDbl2Pair[Pt2, A2]
{ override def a1Dbl1: Double = x
  override def a1Dbl2: Double = y
  override def a1: Pt2 = Pt2(x, y)
}

class Pt2PairArr[A2](val a1ArrayDbl: Array[Double], val a2Array: Array[A2])extends PointDbl2PairArr[Pt2, Pt2Arr, A2, Pt2Pair[A2]]
{ override type ThisT = Pt2PairArr[A2]
  override def typeStr: String = "Pt2Arr"
  override def newPair(dbl1: Double, dbl2: Double, a2: A2): Pt2Pair[A2] = new Pt2Pair[A2](dbl1, dbl2, a2)
  override def a1Arr: Pt2Arr = new Pt2Arr(a1ArrayDbl)
  override def fElemStr: Pt2Pair[A2] => String = _.toString
}

object Pt2PairArr extends Dbl2PairArrCompanion[Pt2, Pt2Arr]
{
  def apply[A2](pairs: Pt2Pair[A2]*)(implicit ct: ClassTag[A2]): Pt2PairArr[A2] ={
    val arrays = seqToArrays(pairs)
    new Pt2PairArr[A2](arrays._1, arrays._2)
  }
}