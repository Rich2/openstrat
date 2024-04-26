/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import annotation._, reflect.ClassTag, collection.mutable.ArrayBuffer

class PolygonKm2Pair[A2](val a1ArrayDbl: Array[Double], val a2: A2) extends PolygonLikeDbl2Pair[PtKm2, PolygonKm2, A2]{
  override def a1: PolygonKm2 = new PolygonKm2(a1ArrayDbl)
}

object PolygonKm2Pair
{ implicit def buildImplicit[A2](implicit ct: ClassTag[A2]): BuilderArrMap[PolygonKm2Pair[A2], PolygonKm2PairArr[A2]] = new PolygonKm2PairBuilder[A2]
}

final class PolygonKm2PairArr[A2](val a1ArrayDbls: Array[Array[Double]], val a2Array: Array[A2]) extends
  PolygonLikeDbl2PairArr[PtKm2, PolygonKm2, PolygonKm2Arr, A2, PolygonKm2Pair[A2]]
{ override type ThisT = PolygonKm2PairArr[A2]
  override def setElemUnsafe(i: Int, newElem: PolygonKm2Pair[A2]): Unit = { a1ArrayDbls(i) = newElem.a1ArrayDbl; a2Array(i) = newElem.a2 }
  override def fElemStr: PolygonKm2Pair[A2] => String = _.toString
  override def typeStr: String = "PolygonMPairArray"
  override def apply(index: Int): PolygonKm2Pair[A2] = new PolygonKm2Pair[A2](a1ArrayDbls(index), a2Array(index))
  override def a1Arr: PolygonKm2Arr = new PolygonKm2Arr(a1ArrayDbls)
  override def newFromArrays(array1: Array[Array[Double]], array2: Array[A2]): PolygonKm2PairArr[A2] = new PolygonKm2PairArr[A2](array1, array2)
  override def a1FromArrayDbl(array: Array[Double]): PolygonKm2 = new PolygonKm2(array)
}

final class PolygonKm2PairBuilder[A2](implicit val b2ClassTag: ClassTag[A2], @unused notB: Not[SpecialT]#L[A2]) extends
  PolygonLikeDblNPairArrBuilder[PtKm2, PolygonKm2, PolygonKm2Arr, A2, PolygonKm2Pair[A2], PolygonKm2PairArr[A2]]
{ override type BuffT = PolygonKm2PairBuff[A2]
  override type B1BuffT = PolygonKm2Buff
  override def uninitialised(length: Int): PolygonKm2PairArr[A2] = new PolygonKm2PairArr[A2](new Array[Array[Double]](length), new Array[A2](length))

  override def indexSet(seqLike: PolygonKm2PairArr[A2], index: Int, newElem: PolygonKm2Pair[A2]): Unit = { seqLike.a1ArrayDbls(index) = newElem.a1ArrayDbl
    seqLike.a2Array(index) = newElem.a2 }

  override def newBuff(length: Int): PolygonKm2PairBuff[A2] = new PolygonKm2PairBuff[A2](new ArrayBuffer[Array[Double]](4), new ArrayBuffer[A2](4))
  override def buffToSeqLike(buff: PolygonKm2PairBuff[A2]): PolygonKm2PairArr[A2] = new PolygonKm2PairArr[A2](buff.b1Buffer.toArray, buff.b2Buffer.toArray)

  override def b1Builder: PolygonLikeMapBuilder[PtKm2, PolygonKm2] = PtKm2.polygonBuildImplicit
  override def b1ArrBuilder: BuilderArrMap[PolygonKm2, PolygonKm2Arr] = PolygonKm2.arrBuildImplicit
  override def arrFromArrAndArray(b1Arr: PolygonKm2Arr, b2s: Array[A2]): PolygonKm2PairArr[A2] = new PolygonKm2PairArr[A2](b1Arr.unsafeArrayOfArrays, b2s)
  override def newB1Buff(): PolygonKm2Buff = PolygonKm2Buff()
  override def fromArrays(arrayArrayDbl: Array[Array[Double]], a2Array: Array[A2]): PolygonKm2PairArr[A2] = new PolygonKm2PairArr[A2](arrayArrayDbl, a2Array)
}

class PolygonKm2PairBuff[A2](val b1Buffer: ArrayBuffer[Array[Double]], val b2Buffer: ArrayBuffer[A2]) extends
  SeqLikeDblNPairBuff[PtKm2, PolygonKm2, A2, PolygonKm2Pair[A2]]
{ override type ThisT = PolygonKm2PairBuff[A2]
  override def setElemUnsafe(i: Int, newElem: PolygonKm2Pair[A2]): Unit = { b1Buffer(i) = newElem.a1ArrayDbl; b2Buffer(i) = newElem.a2 }
  override def fElemStr: PolygonKm2Pair[A2] => String = _.toString
  override def typeStr: String = "PolygonMPairBuff"
  override def apply(index: Int): PolygonKm2Pair[A2] = new PolygonKm2Pair[A2](b1Buffer(index), b2Buffer(index))
}