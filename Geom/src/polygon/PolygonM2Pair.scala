/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import annotation._, reflect.ClassTag, collection.mutable.ArrayBuffer

class PolygonM2Pair[A2](val unsafeArray: Array[Double], val a2: A2) extends PolygonLikeDblsPair[PtM2, PolygonM2, A2]{
  override def polygon: PolygonM2 = new PolygonM2(unsafeArray)
}

object PolygonM2Pair {
  implicit def buildImplicit[A2](implicit ct: ClassTag[A2]): ArrBuilder[PolygonM2Pair[A2], PolygonM2PairArr[A2]] = new PolygonM2PairBuild[A2]
}

final class PolygonM2PairArr[A2](val arrayArrayDbl: Array[Array[Double]], val a2Array: Array[A2]) extends
  PolygonLikePairArr[PtM2, PolygonM2, PolygonM2Arr, A2, PolygonM2Pair[A2]]
{ override type ThisT = PolygonM2PairArr[A2]
  override def unsafeSameSize(length: Int): PolygonM2PairArr[A2] = new PolygonM2PairArr[A2](new Array[Array[Double]](arrayArrayDbl.length), a2Array)
  override def unsafeSetElem(i: Int, value: PolygonM2Pair[A2]): Unit = { arrayArrayDbl(i) = value.unsafeArray; a2Array(i) = value.a2 }
  override def fElemStr: PolygonM2Pair[A2] => String = _.toString
  override def typeStr: String = "PolygonMPairArray"
  override def apply(index: Int): PolygonM2Pair[A2] = new PolygonM2Pair[A2](arrayArrayDbl(index), a2Array(index))
  override def a1Arr: PolygonM2Arr = new PolygonM2Arr(arrayArrayDbl)
}

final class PolygonM2PairBuild[A2](implicit ct: ClassTag[A2], @unused notB: Not[SpecialT]#L[A2]) extends ArrBuilder[PolygonM2Pair[A2], PolygonM2PairArr[A2]]
{ override type BuffT = PolygonM2PairBuff[A2]
  override def newArr(length: Int): PolygonM2PairArr[A2] = new PolygonM2PairArr[A2](new Array[Array[Double]](length), new Array[A2](length))

  override def arrSet(arr: PolygonM2PairArr[A2], index: Int, value: PolygonM2Pair[A2]): Unit =
  { arr.arrayArrayDbl(index) = value.unsafeArray ; arr.a2Array(index) = value.a2 }

  override def buffGrow(buff: PolygonM2PairBuff[A2], value: PolygonM2Pair[A2]): Unit = ???
  override def buffGrowArr(buff: PolygonM2PairBuff[A2], arr: PolygonM2PairArr[A2]): Unit = ???
  override def newBuff(length: Int): PolygonM2PairBuff[A2] = new PolygonM2PairBuff[A2](new ArrayBuffer[Array[Double]](4), new ArrayBuffer[A2](4))
  override def buffToBB(buff: PolygonM2PairBuff[A2]): PolygonM2PairArr[A2] = new PolygonM2PairArr[A2](buff.arrayDoubleBuff.toArray, buff.a2Buff.toArray)
}

class PolygonM2PairBuff[A2](val arrayDoubleBuff: ArrayBuffer[Array[Double]], val a2Buff: ArrayBuffer[A2]) extends SeqSpecPairBuff[PtM2, PolygonM2, A2, PolygonM2Pair[A2]]
{ override type ThisT = PolygonM2PairBuff[A2]
  override def unsafeSetElem(i: Int, value: PolygonM2Pair[A2]): Unit = { arrayDoubleBuff(i) = value.unsafeArray; a2Buff(i) = value.a2 }
  override def fElemStr: PolygonM2Pair[A2] => String = _.toString
  override def typeStr: String = "PolygonMPairBuff"
  override def apply(index: Int): PolygonM2Pair[A2] = new PolygonM2Pair[A2](arrayDoubleBuff(index), a2Buff(index))
}