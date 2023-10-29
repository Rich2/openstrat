/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom; package pglobe
import collection.mutable.ArrayBuffer, reflect.ClassTag

class LatLongPair[A2](val latMilliSecs: Double, val longMilliSecs: Double, val a2: A2) extends PointDbl2Pair[LatLong, A2]
{ override def a1Dbl1: Double = latMilliSecs
  override def a1Dbl2: Double = longMilliSecs
  override def a1: LatLong = LatLong.milliSecs(latMilliSecs, longMilliSecs)
}

class LatLongPairArr[A2](val a1ArrayDbl: Array[Double], val a2Array: Array[A2]) extends PointDbl2PairArr[LatLong, LatLongArr, A2, LatLongPair[A2]]
{ override type ThisT = LatLongPairArr[A2]
  override def typeStr: String = "LatLongPairArr"
  override def a1Arr: LatLongArr = new LatLongArr(a1ArrayDbl)

  override def newPair(dbl1: Double, dbl2: Double, a2: A2): LatLongPair[A2] = new LatLongPair[A2](dbl1, dbl2, a2)

  override def fElemStr: LatLongPair[A2] => String = _.toString

  override def newFromArrays(a1Array: Array[Double], a2Array: Array[A2]): LatLongPairArr[A2] = new LatLongPairArr[A2](a1ArrayDbl, a2Array)

  override def newA1(dbl1: Double, dbl2: Double): LatLong = LatLong.milliSecs(dbl1, dbl2)
}

object LatLongPairArr extends Dbl2PairArrCompanion [LatLong, LatLongArr]{
  def apply[A2](pairs: LatLongPair[A2]*)(implicit ct: ClassTag[A2]): LatLongPairArr[A2] =
  { val arrays = seqToArrays(pairs)
    new LatLongPairArr[A2](arrays._1, arrays._2)
  }
}

class LatLongPairBuff[B2](val b1DblBuffer: ArrayBuffer[Double], val b2Buffer: ArrayBuffer[B2]) extends BuffPairDbl2[LatLong, B2, LatLongPair[B2]]
{ override type ThisT = LatLongPairBuff[B2]
  override def typeStr: String = "LatLongPairBuff"
  override def newElem(dbl1: Double, dbl2: Double, a2: B2): LatLongPair[B2] = new LatLongPair[B2](dbl1, dbl2, a2)
}

object LatLongPairBuff{
  def apply[B2](initLen: Int = 4): LatLongPairBuff[B2] = new LatLongPairBuff[B2](new ArrayBuffer[Double](initLen * 4), new ArrayBuffer[B2](initLen))
}

/** Map builder for [[LatLongPairArr]]s. */
class LatLongPairArrMapBuilder[B2](implicit val b2ClassTag: ClassTag[B2]) extends Dbl2PairArrMapBuilder[LatLong, LatLongArr, B2, LatLongPair[B2], LatLongPairArr[B2]]
{ override type BuffT = LatLongPairBuff[B2]
  override type B1BuffT = LatLongBuff
  override def b1ArrBuilder: BuilderArrMap[LatLong, LatLongArr] = LatLong.arrMapBuilderImplicit
  override def arrFromArrAndArray(b1Arr: LatLongArr, b2s: Array[B2]): LatLongPairArr[B2] = new LatLongPairArr[B2](b1Arr.unsafeArray, b2s)
  override def arrFromArrays(b1ArrayDbl: Array[Double], b2Array: Array[B2]): LatLongPairArr[B2] = new LatLongPairArr[B2](b1ArrayDbl, b2Array)
  override def buffFromBuffers(b1Buffer: ArrayBuffer[Double], b2Buffer: ArrayBuffer[B2]): LatLongPairBuff[B2] = new LatLongPairBuff[B2](b1Buffer, b2Buffer)
  override def newB1Buff(): LatLongBuff = LatLongBuff()
}