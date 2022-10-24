/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import reflect.ClassTag, collection.mutable.ArrayBuffer

/** A [[Pt2]] pair class. The main urpose of this type is to be an lement of a [[Pt2PairArr]]. */
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
  override def newFromArrays(a1Array: Array[Double], a2Array: Array[A2]): Pt2PairArr[A2] = new Pt2PairArr[A2](a1Array, a2Array)

  override def newA1(dbl1: Double, dbl2: Double): Pt2 = new Pt2(dbl1, dbl2)
}

object Pt2PairArr extends Dbl2PairArrCompanion[Pt2, Pt2Arr]
{
  def apply[A2](pairs: Pt2Pair[A2]*)(implicit ct: ClassTag[A2]): Pt2PairArr[A2] ={
    val arrays = seqToArrays(pairs)
    new Pt2PairArr[A2](arrays._1, arrays._2)
  }
}

/** Buffer for [[Pt2Pair]]s. */
class Pt2PairBuff[A2](val a1DblBuffer: ArrayBuffer[Double], val a2Buffer: ArrayBuffer[A2]) extends Dbl2PairBuff[Pt2, A2, Pt2Pair[A2]]
{ override type ThisT = Pt2PairBuff[A2]
  override def typeStr: String = "Pt2PairBuff"
  override def newElem(dbl1: Double, dbl2: Double, a2: A2): Pt2Pair[A2] = new Pt2Pair[A2](dbl1, dbl2, a2)
}

/** Builder for [[Pt2PairArr]]s. */
class Pt2PairArrBuider[A2](implicit val b2CT: ClassTag[A2]) extends Dbl2PairArrBuilder[Pt2, Pt2Arr, A2, Pt2Pair[A2], Pt2PairArr[A2]]
{ override type BuffT = Pt2PairBuff[A2]
  override type B1BuffT = Pt2Buff
  override def b1ArrBuilder: ArrBuilder[Pt2, Pt2Arr] = Pt2.arrBuilderImplicit
  override def pairArrBuilder(b1Arr: Pt2Arr, b2s: Array[A2]): Pt2PairArr[A2] = new Pt2PairArr[A2](b1Arr.unsafeArray, b2s)
  override def arrFromArrays(a1ArrayDbl: Array[Double], a2Array: Array[A2]): Pt2PairArr[A2] = new Pt2PairArr[A2](a1ArrayDbl, a2Array)
  override def buffFromBuffers(a1Buffer: ArrayBuffer[Double], a2Buffer: ArrayBuffer[A2]): Pt2PairBuff[A2] = new Pt2PairBuff[A2](a1Buffer, a2Buffer)
  override def buffToBB(buff: Pt2PairBuff[A2]): Pt2PairArr[A2] = new Pt2PairArr[A2](buff.a1DblBuffer.toArray, buff.a2Buffer.toArray)
  override def newB1Buff(): Pt2Buff = Pt2Buff()
}