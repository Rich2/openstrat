/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import annotation._, reflect.ClassTag, collection.mutable.ArrayBuffer

class PolygonM2Pair[A2](val a1ArrayDbl: Array[Double], val a2: A2) extends PolygonLikeDbl2Pair[PtM2, PolygonM2, A2]{
  override def a1: PolygonM2 = new PolygonM2(a1ArrayDbl)
}

object PolygonM2Pair {
  implicit def buildImplicit[A2](implicit ct: ClassTag[A2]): BuilderArrMap[PolygonM2Pair[A2], PolygonM2PairArr[A2]] = new PolygonM2PairBuilder[A2]
}

final class PolygonM2PairArr[A2](val a1ArrayDbls: Array[Array[Double]], val a2Array: Array[A2]) extends
  PolygonLikeDbl2PairArr[PtM2, PolygonM2, PolygonM2Arr, A2, PolygonM2Pair[A2]]
{ override type ThisT = PolygonM2PairArr[A2]
  override def setElemUnsafe(i: Int, newElem: PolygonM2Pair[A2]): Unit = { a1ArrayDbls(i) = newElem.a1ArrayDbl; a2Array(i) = newElem.a2 }
  override def fElemStr: PolygonM2Pair[A2] => String = _.toString
  override def typeStr: String = "PolygonMPairArray"
  override def apply(index: Int): PolygonM2Pair[A2] = new PolygonM2Pair[A2](a1ArrayDbls(index), a2Array(index))
  override def a1Arr: PolygonM2Arr = new PolygonM2Arr(a1ArrayDbls)
  override def newFromArrays(array1: Array[Array[Double]], array2: Array[A2]): PolygonM2PairArr[A2] = new PolygonM2PairArr[A2](array1, array2)
  override def a1FromArrayDbl(array: Array[Double]): PolygonM2 = new PolygonM2(array)
}

final class PolygonM2PairBuilder[A2](implicit val b2ClassTag: ClassTag[A2], @unused notB: Not[SpecialT]#L[A2]) extends
  PolygonLikeDblNPairArrBuilder[PtM2, PolygonM2, PolygonM2Arr, A2, PolygonM2Pair[A2], PolygonM2PairArr[A2]]
{ override type BuffT = PolygonM2PairBuff[A2]
  override type B1BuffT = PolygonM2Buff
  override def uninitialised(length: Int): PolygonM2PairArr[A2] = new PolygonM2PairArr[A2](new Array[Array[Double]](length), new Array[A2](length))

  override def indexSet(seqLike: PolygonM2PairArr[A2], index: Int, elem: PolygonM2Pair[A2]): Unit = { seqLike.a1ArrayDbls(index) = elem.a1ArrayDbl
    seqLike.a2Array(index) = elem.a2 }

  override def newBuff(length: Int): PolygonM2PairBuff[A2] = new PolygonM2PairBuff[A2](new ArrayBuffer[Array[Double]](4), new ArrayBuffer[A2](4))
  override def buffToSeqLike(buff: PolygonM2PairBuff[A2]): PolygonM2PairArr[A2] = new PolygonM2PairArr[A2](buff.b1Buffer.toArray, buff.b2Buffer.toArray)

  override def b1Builder: PolygonLikeMapBuilder[PtM2, PolygonM2] = PtM2.polygonBuildImplicit
  override def b1ArrBuilder: BuilderArrMap[PolygonM2, PolygonM2Arr] = PolygonM2.arrBuildImplicit
  override def arrFromArrAndArray(b1Arr: PolygonM2Arr, b2s: Array[A2]): PolygonM2PairArr[A2] = new PolygonM2PairArr[A2](b1Arr.unsafeArrayOfArrays, b2s)
  override def newB1Buff(): PolygonM2Buff = PolygonM2Buff()
  override def fromArrays(arrayArrayDbl: Array[Array[Double]], a2Array: Array[A2]): PolygonM2PairArr[A2] = new PolygonM2PairArr[A2](arrayArrayDbl, a2Array)
}

class PolygonM2PairBuff[A2](val b1Buffer: ArrayBuffer[Array[Double]], val b2Buffer: ArrayBuffer[A2]) extends
  SeqLikeDblNPairBuff[PtM2, PolygonM2, A2, PolygonM2Pair[A2]]
{ override type ThisT = PolygonM2PairBuff[A2]
  override def setElemUnsafe(i: Int, newElem: PolygonM2Pair[A2]): Unit = { b1Buffer(i) = newElem.a1ArrayDbl; b2Buffer(i) = newElem.a2 }
  override def fElemStr: PolygonM2Pair[A2] => String = _.toString
  override def typeStr: String = "PolygonMPairBuff"
  override def apply(index: Int): PolygonM2Pair[A2] = new PolygonM2Pair[A2](b1Buffer(index), b2Buffer(index))
}