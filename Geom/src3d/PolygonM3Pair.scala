/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import annotation._, reflect.ClassTag, collection.mutable.ArrayBuffer

/** Speccialised effeicnet class for pairs where the first ocmponent of the pair is a [[PolygonM3]], a polygon in £d space poits specified in metre
 * scales. */
class PolygonM3Pair[A2](val a1ArrayDbl: Array[Double], val a2: A2) extends PolygonLikeDblNPair[PtM3, PolygonM3, A2] with SpecialT {
  override def a1: PolygonM3 = new PolygonM3(a1ArrayDbl)
}

object PolygonM3Pair
{ def apply[A2](poly: PolygonM3, a2: A2): PolygonM3Pair[A2] = new PolygonM3Pair[A2](poly.unsafeArray, a2)
}

final class PolygonM3PairArr[A2](val a1ArrayDbls: Array[Array[Double]], val a2Array: Array[A2]) extends
  PolygonLikeDblNPairArr[PtM3, PolygonM3, PolygonM3Arr, A2, PolygonM3Pair[A2]]
{ override type ThisT = PolygonM3PairArr[A2]
  override def setElemUnsafe(i: Int, newElem: PolygonM3Pair[A2]): Unit = { a1ArrayDbls(i) = newElem.a1ArrayDbl; a2Array(i) = newElem.a2 }
  override def fElemStr: PolygonM3Pair[A2] => String = _.toString
  override def typeStr: String = "PolygonM3PairArray"
  override def apply(index: Int): PolygonM3Pair[A2] = new PolygonM3Pair[A2](a1ArrayDbls(index), a2Array(index))
  override def a1Arr: PolygonM3Arr = new PolygonM3Arr(a1ArrayDbls)
  override def newFromArrays(array1: Array[Array[Double]], array2: Array[A2]): PolygonM3PairArr[A2] = new PolygonM3PairArr[A2](array1, array2)
  override def a1FromArrayDbl(array: Array[Double]): PolygonM3 = new PolygonM3(array)
}

final class PolygonM3PairBuilder[A2](implicit val b2ClassTag: ClassTag[A2], @unused notB: Not[SpecialT]#L[A2]) extends
  PolygonLikeDblNPairArrBuilder[PtM3, PolygonM3, PolygonM3Arr, A2, PolygonM3Pair[A2], PolygonM3PairArr[A2]]
{ override type BuffT = PolygonM3PairBuff[A2]
  override type B1BuffT = PolygonM3Buff
  override def uninitialised(length: Int): PolygonM3PairArr[A2] = new PolygonM3PairArr[A2](new Array[Array[Double]](length), new Array[A2](length))

  override def indexSet(seqLike: PolygonM3PairArr[A2], index: Int, elem: PolygonM3Pair[A2]): Unit =
  { seqLike.a1ArrayDbls(index) = elem.a1ArrayDbl ; seqLike.a2Array(index) = elem.a2 }

  override def newBuff(length: Int): PolygonM3PairBuff[A2] = new PolygonM3PairBuff[A2](new ArrayBuffer[Array[Double]](4), new ArrayBuffer[A2](4))
  override def buffToSeqLike(buff: PolygonM3PairBuff[A2]): PolygonM3PairArr[A2] = new PolygonM3PairArr[A2](buff.b1Buffer.toArray, buff.b2Buffer.toArray)

  override def b1Builder: PolygonLikeMapBuilder[PtM3, PolygonM3] = PtM3.polygonBuildImplicit
  override def b1ArrBuilder: ArrMapBuilder[PolygonM3, PolygonM3Arr] = PolygonM3.arrBuildImplicit
  override def arrFromArrAndArray(b1Arr: PolygonM3Arr, b2s: Array[A2]): PolygonM3PairArr[A2] = new PolygonM3PairArr[A2](b1Arr.unsafeArrayOfArrays, b2s)
  override def newB1Buff(): PolygonM3Buff = PolygonM3Buff()
  override def fromArrays(arrayArrayDbl: Array[Array[Double]], a2Array: Array[A2]): PolygonM3PairArr[A2] = new PolygonM3PairArr[A2](arrayArrayDbl, a2Array)
}

class PolygonM3PairBuff[A2](val b1Buffer: ArrayBuffer[Array[Double]], val b2Buffer: ArrayBuffer[A2]) extends
  SeqLikeDblNPairBuff[PtM3, PolygonM3, A2, PolygonM3Pair[A2]]
{ override type ThisT = PolygonM3PairBuff[A2]
  override def setElemUnsafe(i: Int, newElem: PolygonM3Pair[A2]): Unit = { b1Buffer(i) = newElem.a1ArrayDbl; b2Buffer(i) = newElem.a2 }
  override def fElemStr: PolygonM3Pair[A2] => String = _.toString
  override def typeStr: String = "PolygonM3PairBuff"
  override def apply(index: Int): PolygonM3Pair[A2] = new PolygonM3Pair[A2](b1Buffer(index), b2Buffer(index))
}