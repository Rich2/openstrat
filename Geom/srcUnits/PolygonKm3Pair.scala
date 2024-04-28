/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import annotation._, reflect.ClassTag, collection.mutable.ArrayBuffer

/** Speccialised effeicnet class for pairs where the first ocmponent of the pair is a [[PolygonKm3]], a polygon in £d space poits specified in metre
 * scales. */
class PolygonKm3Pair[A2](val a1ArrayDbl: Array[Double], val a2: A2) extends PolygonLikeDblNPair[PtKm3, PolygonKm3, A2] with SpecialT {
  override def a1: PolygonKm3 = new PolygonKm3(a1ArrayDbl)
}

object PolygonKm3Pair
{ def apply[A2](poly: PolygonKm3, a2: A2): PolygonKm3Pair[A2] = new PolygonKm3Pair[A2](poly.arrayUnsafe, a2)
}

final class PolygonKm3PairArr[A2](val a1ArrayDbls: Array[Array[Double]], val a2Array: Array[A2]) extends
  PolygonLikeDblNPairArr[PtKm3, PolygonKm3, PolygonKm3Arr, A2, PolygonKm3Pair[A2]]
{ override type ThisT = PolygonKm3PairArr[A2]
  override def setElemUnsafe(i: Int, newElem: PolygonKm3Pair[A2]): Unit = { a1ArrayDbls(i) = newElem.a1ArrayDbl; a2Array(i) = newElem.a2 }
  override def fElemStr: PolygonKm3Pair[A2] => String = _.toString
  override def typeStr: String = "PolygonKm3PairArray"
  override def apply(index: Int): PolygonKm3Pair[A2] = new PolygonKm3Pair[A2](a1ArrayDbls(index), a2Array(index))
  override def a1Arr: PolygonKm3Arr = new PolygonKm3Arr(a1ArrayDbls)
  override def newFromArrays(array1: Array[Array[Double]], array2: Array[A2]): PolygonKm3PairArr[A2] = new PolygonKm3PairArr[A2](array1, array2)
  override def a1FromArrayDbl(array: Array[Double]): PolygonKm3 = new PolygonKm3(array)
}

final class PolygonKm3PairBuilder[A2](implicit val b2ClassTag: ClassTag[A2], @unused notB: Not[SpecialT]#L[A2]) extends
  PolygonLikeDblNPairArrBuilder[PtKm3, PolygonKm3, PolygonKm3Arr, A2, PolygonKm3Pair[A2], PolygonKm3PairArr[A2]]
{ override type BuffT = PolygonKm3PairBuff[A2]
  override type B1BuffT = PolygonKm3Buff
  override def uninitialised(length: Int): PolygonKm3PairArr[A2] = new PolygonKm3PairArr[A2](new Array[Array[Double]](length), new Array[A2](length))

  override def indexSet(seqLike: PolygonKm3PairArr[A2], index: Int, newElem: PolygonKm3Pair[A2]): Unit =
  { seqLike.a1ArrayDbls(index) = newElem.a1ArrayDbl ; seqLike.a2Array(index) = newElem.a2 }

  override def newBuff(length: Int): PolygonKm3PairBuff[A2] = new PolygonKm3PairBuff[A2](new ArrayBuffer[Array[Double]](4), new ArrayBuffer[A2](4))
  override def buffToSeqLike(buff: PolygonKm3PairBuff[A2]): PolygonKm3PairArr[A2] = new PolygonKm3PairArr[A2](buff.b1Buffer.toArray, buff.b2Buffer.toArray)

  override def b1Builder: PolygonLikeMapBuilder[PtKm3, PolygonKm3] = PtKm3.polygonBuildImplicit
  override def b1ArrBuilder: BuilderArrMap[PolygonKm3, PolygonKm3Arr] = PolygonKm3.arrBuildImplicit
  override def arrFromArrAndArray(b1Arr: PolygonKm3Arr, b2s: Array[A2]): PolygonKm3PairArr[A2] = new PolygonKm3PairArr[A2](b1Arr.unsafeArrayOfArrays, b2s)
  override def newB1Buff(): PolygonKm3Buff = PolygonKm3Buff()
  override def fromArrays(arrayArrayDbl: Array[Array[Double]], a2Array: Array[A2]): PolygonKm3PairArr[A2] = new PolygonKm3PairArr[A2](arrayArrayDbl, a2Array)
}

class PolygonKm3PairBuff[A2](val b1Buffer: ArrayBuffer[Array[Double]], val b2Buffer: ArrayBuffer[A2]) extends
  SeqLikeDblNPairBuff[PtKm3, PolygonKm3, A2, PolygonKm3Pair[A2]]
{ override type ThisT = PolygonKm3PairBuff[A2]
  override def setElemUnsafe(i: Int, newElem: PolygonKm3Pair[A2]): Unit = { b1Buffer(i) = newElem.a1ArrayDbl; b2Buffer(i) = newElem.a2 }
  override def fElemStr: PolygonKm3Pair[A2] => String = _.toString
  override def typeStr: String = "PolygonKm3PairBuff"
  override def apply(index: Int): PolygonKm3Pair[A2] = new PolygonKm3Pair[A2](b1Buffer(index), b2Buffer(index))
}