/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import annotation._, reflect.ClassTag, collection.mutable.ArrayBuffer

class PolygonM3Pair[A2](val a1ArrayDbl: Array[Double], val a2: A2) extends PolygonLikeDblNPair[PtM3, PolygonM3, A2] with SpecialT {
  override def a1: PolygonM3 = new PolygonM3(a1ArrayDbl)
}

object PolygonM3Pair
{
  def apply[A2](poly: PolygonM3, a2: A2): PolygonM3Pair[A2] = new PolygonM3Pair[A2](poly.unsafeArray, a2)
}

final class PolygonM3PairArr[A2](val arrayArrayDbl: Array[Array[Double]], val a2Array: Array[A2]) extends
  PolygonLikeDblNPairArr[PtM3, PolygonM3, PolygonM3Arr, A2, PolygonM3Pair[A2]]
{ override type ThisT = PolygonM3PairArr[A2]
  override def unsafeSetElem(i: Int, value: PolygonM3Pair[A2]): Unit = { arrayArrayDbl(i) = value.a1ArrayDbl; a2Array(i) = value.a2 }
  override def fElemStr: PolygonM3Pair[A2] => String = _.toString
  override def typeStr: String = "PolygonM3PairArray"
  override def apply(index: Int): PolygonM3Pair[A2] = new PolygonM3Pair[A2](arrayArrayDbl(index), a2Array(index))
  override def a1Arr: PolygonM3Arr = new PolygonM3Arr(arrayArrayDbl)
  override def a1Buff: ArrayDblBuff[PolygonM3] = PolygonM3Buff()
  override def fromArrays(array1: Array[Array[Double]], array2: Array[A2]): PolygonM3PairArr[A2] = new PolygonM3PairArr[A2](array1, array2)
  override def a1FromArrayDbl(array: Array[Double]): PolygonM3 = new PolygonM3(array)
}

final class PolygonM3PairBuilder[A2](implicit val b2ClassTag: ClassTag[A2], @unused notB: Not[SpecialT]#L[A2]) extends
  PolygonLikePairArrBuilder[PtM3, PolygonM3, PolygonM3Arr, A2, PolygonM3Pair[A2], PolygonM3PairArr[A2]]
{ override type BuffT = PolygonM3PairBuff[A2]
  override def arrUninitialised(length: Int): PolygonM3PairArr[A2] = new PolygonM3PairArr[A2](new Array[Array[Double]](length), new Array[A2](length))

  override def arrSet(arr: PolygonM3PairArr[A2], index: Int, value: PolygonM3Pair[A2]): Unit =
  { arr.arrayArrayDbl(index) = value.a1ArrayDbl ; arr.a2Array(index) = value.a2 }

  override def buffGrow(buff: PolygonM3PairBuff[A2], value: PolygonM3Pair[A2]): Unit = ???
  override def newBuff(length: Int): PolygonM3PairBuff[A2] = new PolygonM3PairBuff[A2](new ArrayBuffer[Array[Double]](4), new ArrayBuffer[A2](4))
  override def buffToBB(buff: PolygonM3PairBuff[A2]): PolygonM3PairArr[A2] = new PolygonM3PairArr[A2](buff.arrayDoubleBuff.toArray, buff.a2Buffer.toArray)

  /** Builder for the first element of the pair of type B1, in this case a [[PolygonLike]]. The return type has been narrowed as it is needed for the
   * polygonMapPair method on [[PolygonLikePairArr]]. */
  override def b1Builder: PolygonLikeBuilder[PtM3, PolygonM3] = PtM3.polygonBuildImplicit

  /** Builder for an Arr of the first element of the pair. */
  override def b1ArrBuilder: ArrBuilder[PolygonM3, PolygonM3Arr] = PolygonM3.arrBuildImplicit

  /** Builder for the sequence of pairs, takes the results of the other two builder methods to produce the end product. Pun intended */
  override def pairArrBuilder(b1Arr: PolygonM3Arr, b2s: Array[A2]): PolygonM3PairArr[A2] = new PolygonM3PairArr[A2](b1Arr.unsafeArrayOfArrays, b2s)

  override type B1BuffT = PolygonM3Buff

  override def newB1Buff(): PolygonM3Buff = PolygonM3Buff()

  override def fromBuffs(a1Buff: PolygonM3Buff, b2s: ArrayBuffer[A2]): PolygonM3PairArr[A2] = new PolygonM3PairArr[A2](a1Buff.unsafeBuffer.toArray, b2s.toArray)

  override def b1BuffGrow(buff: PolygonM3Buff, newElem: PolygonM3): Unit = buff.grow(newElem)
}

class PolygonM3PairBuff[A2](val arrayDoubleBuff: ArrayBuffer[Array[Double]], val a2Buffer: ArrayBuffer[A2]) extends
  SeqSpecPairBuff[PtM3, PolygonM3, A2, PolygonM3Pair[A2]]
{ override type ThisT = PolygonM3PairBuff[A2]
  override def unsafeSetElem(i: Int, value: PolygonM3Pair[A2]): Unit = { arrayDoubleBuff(i) = value.a1ArrayDbl; a2Buffer(i) = value.a2 }
  override def fElemStr: PolygonM3Pair[A2] => String = _.toString
  override def typeStr: String = "PolygonM3PairBuff"
  override def apply(index: Int): PolygonM3Pair[A2] = new PolygonM3Pair[A2](arrayDoubleBuff(index), a2Buffer(index))

  override def grow(newElem: PolygonM3Pair[A2]): Unit = ???

  override def grows(newElems: Arr[PolygonM3Pair[A2]]): Unit = ???
}