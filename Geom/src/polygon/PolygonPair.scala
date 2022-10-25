/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import annotation._, reflect.ClassTag, collection.mutable.ArrayBuffer

final class PolygonPair[A2](val a1ArrayDbl: Array[Double], val a2: A2) extends PolygonLikeDbl2Pair[Pt2, Polygon, A2]
{
  def a1: Polygon = new PolygonGen(a1ArrayDbl)
}

object PolygonPair {
  implicit def buildImplicit[A2](implicit ct: ClassTag[A2]): ArrBuilder[PolygonPair[A2], PolygonPairArr[A2]] = new PolygonPairBuilder[A2]
}

final class PolygonPairArr[A2](val a1ArrayArrayDbl: Array[Array[Double]], val a2Array: Array[A2]) extends
  PolygonLikeDbl2PairArr[Pt2, Polygon, PolygonArr, A2, PolygonPair[A2]]
{ override type ThisT = PolygonPairArr[A2]
  override def unsafeSetElem(i: Int, value: PolygonPair[A2]): Unit = { a1ArrayArrayDbl(i) = value.a1ArrayDbl; a2Array(i) = value.a2 }
  override def fElemStr: PolygonPair[A2] => String = _.toString
  override def typeStr: String = "PolygonPairArray"
  override def apply(index: Int): PolygonPair[A2] = new PolygonPair[A2](a1ArrayArrayDbl(index), a2Array(index))
  override def a1Arr: PolygonArr = new PolygonArr(a1ArrayArrayDbl)
  override def fromArrays(array1: Array[Array[Double]], array2: Array[A2]): PolygonPairArr[A2] = new PolygonPairArr[A2](array1, array2)
  override def a1Buff: PolygonBuff = PolygonBuff()
  override def a1FromArrayDbl(array: Array[Double]): Polygon = new PolygonGen(array)
}

final class PolygonPairBuilder[A2](implicit val b2ClassTag: ClassTag[A2], @unused notB: Not[SpecialT]#L[A2]) extends
  PolygonLikeDblNPairArrBuilder[Pt2, Polygon, PolygonArr, A2, PolygonPair[A2], PolygonPairArr[A2]]
{ override type BuffT = PolygonPairBuff[A2]
  override type B1BuffT = PolygonBuff
  override def arrUninitialised(length: Int): PolygonPairArr[A2] = new PolygonPairArr[A2](new Array[Array[Double]](length), new Array[A2](length))

  override def arrSet(arr: PolygonPairArr[A2], index: Int, value: PolygonPair[A2]): Unit =
  { arr.a1ArrayArrayDbl(index) = value.a1ArrayDbl ; arr.a2Array(index) = value.a2 }
  override def newBuff(length: Int): PolygonPairBuff[A2] = PolygonPairBuff(length)
  override def buffToBB(buff: PolygonPairBuff[A2]): PolygonPairArr[A2] = new PolygonPairArr[A2](buff.arrayDblBuff.toArray, buff.a2Buffer.toArray)
  override def b1Builder: PolygonLikeBuilder[Pt2, Polygon] = Pt2.polygonBuildImplicit
  override def b1ArrBuilder: ArrBuilder[Polygon, PolygonArr] = Polygon.arrBuildImplicit
  override def pairArrBuilder(b1Arr: PolygonArr, b2s: Array[A2]): PolygonPairArr[A2] = new PolygonPairArr[A2](b1Arr.unsafeArrayOfArrays, b2s)
  override def newB1Buff(): PolygonBuff = PolygonBuff()
  override def fromArrays(arrayArrayDbl: Array[Array[Double]], a2Array: Array[A2]): PolygonPairArr[A2] = fromArrays(arrayArrayDbl, a2Array)
  override def buffGrow(buff: PolygonPairBuff[A2], value: PolygonPair[A2]): Unit = buff.grow(value)
}

class PolygonPairBuff[A2](val arrayDblBuff: ArrayBuffer[Array[Double]], val a2Buffer: ArrayBuffer[A2]) extends SeqSpecPairBuff[Pt2, Polygon, A2, PolygonPair[A2]]
{ override type ThisT = PolygonPairBuff[A2]
  override def unsafeSetElem(i: Int, value: PolygonPair[A2]): Unit = { arrayDblBuff(i) = value.a1ArrayDbl; a2Buffer(i) = value.a2 }
  override def fElemStr: PolygonPair[A2] => String = _.toString
  override def typeStr: String = "PolygonPairBuff"
  override def apply(index: Int): PolygonPair[A2] = new PolygonPair[A2](arrayDblBuff(index), a2Buffer(index))

  override def grow(newElem: PolygonPair[A2]): Unit = ???

  override def grows(newElems: Arr[PolygonPair[A2]]): Unit = ???
}

object PolygonPairBuff
{ def apply[A2](n: Int): PolygonPairBuff[A2] = new PolygonPairBuff[A2](new ArrayBuffer[Array[Double]](n * 8), new ArrayBuffer[A2](n))
}