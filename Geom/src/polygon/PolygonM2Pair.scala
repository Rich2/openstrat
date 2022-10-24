/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import annotation._, reflect.ClassTag, collection.mutable.ArrayBuffer

class PolygonM2Pair[A2](val a1ArrayDbl: Array[Double], val a2: A2) extends PolygonLikeDblNPair[PtM2, PolygonM2, A2]{
  override def a1: PolygonM2 = new PolygonM2(a1ArrayDbl)
}

object PolygonM2Pair {
  implicit def buildImplicit[A2](implicit ct: ClassTag[A2]): ArrBuilder[PolygonM2Pair[A2], PolygonM2PairArr[A2]] = new PolygonM2PairBuilder[A2]
}

final class PolygonM2PairArr[A2](val arrayArrayDbl: Array[Array[Double]], val a2Array: Array[A2]) extends
  PolygonLikeDblNPairArr[PtM2, PolygonM2, PolygonM2Arr, A2, PolygonM2Pair[A2]]
{ override type ThisT = PolygonM2PairArr[A2]
  override def unsafeSetElem(i: Int, value: PolygonM2Pair[A2]): Unit = { arrayArrayDbl(i) = value.a1ArrayDbl; a2Array(i) = value.a2 }
  override def fElemStr: PolygonM2Pair[A2] => String = _.toString
  override def typeStr: String = "PolygonMPairArray"
  override def apply(index: Int): PolygonM2Pair[A2] = new PolygonM2Pair[A2](arrayArrayDbl(index), a2Array(index))
  override def a1Arr: PolygonM2Arr = new PolygonM2Arr(arrayArrayDbl)
  override def fromArrays(array1: Array[Array[Double]], array2: Array[A2]): PolygonM2PairArr[A2] = new PolygonM2PairArr[A2](array1, array2)
  override def a1Buff: ArrayDblBuff[PolygonM2] = PolygonM2Buff()
  override def a1FromArrayDbl(array: Array[Double]): PolygonM2 = new PolygonM2(array)
}

final class PolygonM2PairBuilder[A2](implicit ct: ClassTag[A2], @unused notB: Not[SpecialT]#L[A2]) extends
  PolygonLikePairArrBuilder[PtM2, PolygonM2, PolygonM2Arr, A2, PolygonM2Pair[A2], PolygonM2PairArr[A2]]
{ override type BuffT = PolygonM2PairBuff[A2]
  override type B1BuffT = PolygonM2Buff
  override def arrUninitialised(length: Int): PolygonM2PairArr[A2] = new PolygonM2PairArr[A2](new Array[Array[Double]](length), new Array[A2](length))

  override def arrSet(arr: PolygonM2PairArr[A2], index: Int, value: PolygonM2Pair[A2]): Unit =
  { arr.arrayArrayDbl(index) = value.a1ArrayDbl ; arr.a2Array(index) = value.a2 }

  override def buffGrow(buff: PolygonM2PairBuff[A2], value: PolygonM2Pair[A2]): Unit = ???
  override def newBuff(length: Int): PolygonM2PairBuff[A2] = new PolygonM2PairBuff[A2](new ArrayBuffer[Array[Double]](4), new ArrayBuffer[A2](4))
  override def buffToBB(buff: PolygonM2PairBuff[A2]): PolygonM2PairArr[A2] = new PolygonM2PairArr[A2](buff.arrayDoubleBuff.toArray, buff.a2Buffer.toArray)

  /** Builder for the first element of the pair of type B1, in this case a [[PolygonLike]]. The return type has been narrowed as it is needed for the
   * polygonMapPair method on [[PolygonLikePairArr]]. */
  override def b1Builder: PolygonLikeBuilder[PtM2, PolygonM2] = PtM2.polygonBuildImplicit

  /** Builder for an Arr of the first element of the pair. */
  override def b1ArrBuilder: ArrBuilder[PolygonM2, PolygonM2Arr] = PolygonM2.arrBuildImplicit

  /** Builder for the sequence of pairs, takes the results of the other two builder methods to produce the end product. Pun intended */
  override def pairArrBuilder(b1Arr: PolygonM2Arr, b2s: Array[A2]): PolygonM2PairArr[A2] = new PolygonM2PairArr[A2](b1Arr.unsafeArrayOfArrays, b2s)

  override def newB1Buff(): PolygonM2Buff = PolygonM2Buff()

  override def b1BuffGrow(buff: PolygonM2Buff, newElem: PolygonM2): Unit = ???
}

class PolygonM2PairBuff[A2](val arrayDoubleBuff: ArrayBuffer[Array[Double]], val a2Buffer: ArrayBuffer[A2]) extends SeqSpecPairBuff[PtM2, PolygonM2, A2, PolygonM2Pair[A2]]
{ override type ThisT = PolygonM2PairBuff[A2]
  override def unsafeSetElem(i: Int, value: PolygonM2Pair[A2]): Unit = { arrayDoubleBuff(i) = value.a1ArrayDbl; a2Buffer(i) = value.a2 }
  override def fElemStr: PolygonM2Pair[A2] => String = _.toString
  override def typeStr: String = "PolygonMPairBuff"
  override def apply(index: Int): PolygonM2Pair[A2] = new PolygonM2Pair[A2](arrayDoubleBuff(index), a2Buffer(index))

  override def grow(newElem: PolygonM2Pair[A2]): Unit = ???

  override def grows(newElems: Arr[PolygonM2Pair[A2]]): Unit = ???
}