/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import collection.mutable.ArrayBuffer, reflect.ClassTag

class PtM3Pair[A2](val a1Dbl1: Double, val a1Dbl2: Double, val a1Dbl3: Double, val a2: A2) extends PointDbl3Pair[PtM3, A2]
{ override def a1: PtM3 = new PtM3(a1Dbl1, a1Dbl2, a1Dbl3)
}

class PtM3PairArr[A2](val a1ArrayDbl: Array[Double], val a2Array: Array[A2]) extends PointDbl3PairArr[PtM3, PtM3Arr, A2, PtM3Pair[A2]]
{ override type ThisT = PtM3PairArr[A2]
  override def typeStr: String = "PtM3PairArr"
  override def newPair(dbl1: Double, dbl2: Double, dbl3: Double, a2: A2): PtM3Pair[A2] = new PtM3Pair[A2](dbl1, dbl2, dbl3, a2)
  override def a1Arr: PtM3Arr = new PtM3Arr(a1ArrayDbl)
  override def fElemStr: PtM3Pair[A2] => String = _.toString
  override def newFromArrays(a1Array: Array[Double], a2Array: Array[A2]): PtM3PairArr[A2] = new PtM3PairArr[A2](a1Array, a2Array)
  override def newA1(dbl1: Double, dbl2: Double, dbl3: Double): PtM3 = new PtM3(dbl1, dbl2, dbl3)
}

class PtM3PairBuff[B2](val b1DblBuffer: ArrayBuffer[Double], val b2Buffer: ArrayBuffer[B2]) extends BuffPairDbl3[PtM3, B2, PtM3Pair[B2]]
{ override type ThisT = PtM3PairBuff[B2]
  override def typeStr: String = "PtM3PairBuff"
  override def newElem(dbl1: Double, dbl2: Double, dbl3: Double, a2: B2): PtM3Pair[B2] = new PtM3Pair[B2](dbl1, dbl2, dbl3, a2)
}

/** Map builder for [[PtM3PairArr]]s. */
class PtM3PairArrMapBuilder[B2](implicit val b2ClassTag: ClassTag[B2]) extends BuilderArrPairDbl3[PtM3, PtM3Arr, B2, PtM3Pair[B2], PtM3PairArr[B2]]
{ override type BuffT = PtM3PairBuff[B2]
  override type B1BuffT = PtM3Buff
  override def b1ArrBuilder: BuilderArrMap[PtM3, PtM3Arr] = PtM3.arrBuilderImplicit
  override def arrFromArrAndArray(b1Arr: PtM3Arr, b2s: Array[B2]): PtM3PairArr[B2] = new PtM3PairArr[B2](b1Arr.arrayUnsafe, b2s)
  override def arrFromArrays(a1ArrayDbl: Array[Double], a2Array: Array[B2]): PtM3PairArr[B2] = new PtM3PairArr[B2](a1ArrayDbl, a2Array)
  override def buffFromBuffers(a1Buffer: ArrayBuffer[Double], a2Buffer: ArrayBuffer[B2]): PtM3PairBuff[B2] = new PtM3PairBuff[B2](a1Buffer, a2Buffer)
  override def newB1Buff(): PtM3Buff = PtM3Buff()
}