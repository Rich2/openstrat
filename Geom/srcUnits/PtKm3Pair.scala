/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import collection.mutable.ArrayBuffer, reflect.ClassTag

class PtKm3Pair[A2](val a1Dbl1: Double, val a1Dbl2: Double, val a1Dbl3: Double, val a2: A2) extends PointDbl3Pair[PtKm3, A2]
{ override def a1: PtKm3 = new PtKm3(a1Dbl1, a1Dbl2, a1Dbl3)
}

class PtKm3PairArr[A2](val a1ArrayDbl: Array[Double], val a2Array: Array[A2]) extends PointDbl3PairArr[PtKm3, PtKm3Arr, A2, PtKm3Pair[A2]]
{ override type ThisT = PtKm3PairArr[A2]
  override def typeStr: String = "PtKm3PairArr"
  override def newPair(dbl1: Double, dbl2: Double, dbl3: Double, a2: A2): PtKm3Pair[A2] = new PtKm3Pair[A2](dbl1, dbl2, dbl3, a2)
  override def a1Arr: PtKm3Arr = new PtKm3Arr(a1ArrayDbl)
  override def fElemStr: PtKm3Pair[A2] => String = _.toString
  override def newFromArrays(a1Array: Array[Double], a2Array: Array[A2]): PtKm3PairArr[A2] = new PtKm3PairArr[A2](a1Array, a2Array)
  override def newA1(dbl1: Double, dbl2: Double, dbl3: Double): PtKm3 = new PtKm3(dbl1, dbl2, dbl3)
}

class PtKm3PairBuff[B2](val b1DblBuffer: ArrayBuffer[Double], val b2Buffer: ArrayBuffer[B2]) extends BuffPairDbl3[PtKm3, B2, PtKm3Pair[B2]]
{ override type ThisT = PtKm3PairBuff[B2]
  override def typeStr: String = "PtKm3PairBuff"
  override def newElem(dbl1: Double, dbl2: Double, dbl3: Double, a2: B2): PtKm3Pair[B2] = new PtKm3Pair[B2](dbl1, dbl2, dbl3, a2)
}

/** Map builder for [[PtKm3PairArr]]s. */
class PtKm3PairArrMapBuilder[B2](implicit val b2ClassTag: ClassTag[B2]) extends BuilderArrPairDbl3[PtKm3, PtKm3Arr, B2, PtKm3Pair[B2], PtKm3PairArr[B2]]
{ override type BuffT = PtKm3PairBuff[B2]
  override type B1BuffT = PtKm3Buff
  override def b1ArrBuilder: BuilderArrMap[PtKm3, PtKm3Arr] = PtKm3.arrBuilderImplicit
  override def arrFromArrAndArray(b1Arr: PtKm3Arr, b2s: Array[B2]): PtKm3PairArr[B2] = new PtKm3PairArr[B2](b1Arr.arrayUnsafe, b2s)
  override def arrFromArrays(a1ArrayDbl: Array[Double], a2Array: Array[B2]): PtKm3PairArr[B2] = new PtKm3PairArr[B2](a1ArrayDbl, a2Array)
  override def buffFromBuffers(a1Buffer: ArrayBuffer[Double], a2Buffer: ArrayBuffer[B2]): PtKm3PairBuff[B2] = new PtKm3PairBuff[B2](a1Buffer, a2Buffer)
  override def newB1Buff(): PtKm3Buff = PtKm3Buff()
}