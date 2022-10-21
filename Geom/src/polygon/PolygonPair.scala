/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import annotation._, reflect.ClassTag, collection.mutable.ArrayBuffer

final class PolygonPair[A2](val unsafeArray: Array[Double], val a2: A2) extends PolygonDblNPair[Pt2, Polygon, A2]
{
  def a1: Polygon = new PolygonGen(unsafeArray)
}

object PolygonPair {
  implicit def buildImplicit[A2](implicit ct: ClassTag[A2]): ArrBuilder[PolygonPair[A2], PolygonPairArr[A2]] = new PolygonPairBuilder[A2]
}

final class PolygonPairArr[A2](val arrayArrayDbl: Array[Array[Double]], val a2Array: Array[A2]) extends
  PolygonDblNPairArr[Pt2, Polygon, PolygonArr, A2, PolygonPair[A2]]
{ override type ThisT = PolygonPairArr[A2]
  override def unsafeSetElem(i: Int, value: PolygonPair[A2]): Unit = { arrayArrayDbl(i) = value.unsafeArray; a2Array(i) = value.a2 }
  override def fElemStr: PolygonPair[A2] => String = _.toString
  override def typeStr: String = "PolygonPairArray"
  override def apply(index: Int): PolygonPair[A2] = new PolygonPair[A2](arrayArrayDbl(index), a2Array(index))

  override def a1Arr: PolygonArr = new PolygonArr(arrayArrayDbl)
  override def fromArrays(array1: Array[Array[Double]], array2: Array[A2]): PolygonPairArr[A2] = new PolygonPairArr[A2](array1, array2)
  override def a1Buff: PolygonBuff = PolygonBuff()
}

final class PolygonPairBuilder[A2](implicit ct: ClassTag[A2], @unused notB: Not[SpecialT]#L[A2]) extends
  PolygonLikePairArrBuilder[Pt2, Polygon, PolygonArr, A2, PolygonPair[A2], PolygonPairArr[A2]]
{ override type BuffT = PolygonPairBuff[A2]
  override def newArr(length: Int): PolygonPairArr[A2] = new PolygonPairArr[A2](new Array[Array[Double]](length), new Array[A2](length))

  override def arrSet(arr: PolygonPairArr[A2], index: Int, value: PolygonPair[A2]): Unit =
  { arr.arrayArrayDbl(index) = value.unsafeArray ; arr.a2Array(index) = value.a2 }

  override def buffGrow(buff: PolygonPairBuff[A2], value: PolygonPair[A2]): Unit = ???
  override def newBuff(length: Int): PolygonPairBuff[A2] = new PolygonPairBuff[A2](new ArrayBuffer[Array[Double]](4), new ArrayBuffer[A2](4))
  override def buffToBB(buff: PolygonPairBuff[A2]): PolygonPairArr[A2] = new PolygonPairArr[A2](buff.arrayDblBuff.toArray, buff.a2Buffer.toArray)
  override def b1Builder: PolygonLikeBuilder[Pt2, Polygon] = Pt2.polygonBuildImplicit
  override def b1ArrBuilder: ArrBuilder[Polygon, PolygonArr] = Polygon.arrBuildImplicit
  override def pairArrBuilder(b1Arr: PolygonArr, a2s: Array[A2]): PolygonPairArr[A2] = new PolygonPairArr[A2](b1Arr.unsafeArrayOfArrays, a2s)
}

class PolygonPairBuff[A2](val arrayDblBuff: ArrayBuffer[Array[Double]], val a2Buffer: ArrayBuffer[A2]) extends SeqSpecPairBuff[Pt2, Polygon, A2, PolygonPair[A2]]
{ override type ThisT = PolygonPairBuff[A2]
  override def unsafeSetElem(i: Int, value: PolygonPair[A2]): Unit = { arrayDblBuff(i) = value.unsafeArray; a2Buffer(i) = value.a2 }
  override def fElemStr: PolygonPair[A2] => String = _.toString
  override def typeStr: String = "PolygonPairBuff"
  override def apply(index: Int): PolygonPair[A2] = new PolygonPair[A2](arrayDblBuff(index), a2Buffer(index))

  override def grow(newElem: PolygonPair[A2]): Unit = ???

  override def grows(newElems: Arr[PolygonPair[A2]]): Unit = ???
}