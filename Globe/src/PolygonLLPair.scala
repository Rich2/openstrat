/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom; package pglobe
import geom._, annotation._, reflect.ClassTag, collection.mutable.ArrayBuffer

class PolygonLLPair[A2](val a1ArrayDbl: Array[Double], val a2: A2) extends PolygonLikeDblNPair[LatLong, PolygonLL, A2] with SpecialT {
  override def a1: PolygonLL = new PolygonLL(a1ArrayDbl)
}

object PolygonLLPair
{ def apply[A2](poly: PolygonLL, a2: A2): PolygonLLPair[A2] = new PolygonLLPair[A2](poly.unsafeArray, a2)
}

final class PolygonLLPairArr[A2](val a1Array: Array[Array[Double]], val a2Array: Array[A2]) extends
  PolygonLikeDblNPairArr[LatLong, PolygonLL, PolygonLLArr, A2, PolygonLLPair[A2]]
{ override type ThisT = PolygonLLPairArr[A2]
  override def unsafeSetElem(i: Int, value: PolygonLLPair[A2]): Unit = { a1Array(i) = value.a1ArrayDbl; a2Array(i) = value.a2 }
  override def fElemStr: PolygonLLPair[A2] => String = _.toString
  override def typeStr: String = "PolygonLLPairArray"
  override def apply(index: Int): PolygonLLPair[A2] = new PolygonLLPair[A2](a1Array(index), a2Array(index))
  override def a1Arr: PolygonLLArr = new PolygonLLArr(a1Array)
  override def fromArrays(array1: Array[Array[Double]], array2: Array[A2]): PolygonLLPairArr[A2] = new PolygonLLPairArr[A2](array1, array2)
  override def a1FromArrayDbl(array: Array[Double]): PolygonLL = new PolygonLL(array)
}

final class PolygonLLPairBuilder[A2](implicit val b2ClassTag: ClassTag[A2], @unused notB: Not[SpecialT]#L[A2]) extends
  PolygonLikeDblNPairArrBuilder[LatLong, PolygonLL, PolygonLLArr, A2, PolygonLLPair[A2], PolygonLLPairArr[A2]]
{ override type BuffT = PolygonLLPairBuff[A2]
  override type B1BuffT = PolygonLLBuff
  override def uninitialised(length: Int): PolygonLLPairArr[A2] = new PolygonLLPairArr[A2](new Array[Array[Double]](length), new Array[A2](length))

  override def arrSet(arr: PolygonLLPairArr[A2], index: Int, value: PolygonLLPair[A2]): Unit =
  { arr.a1Array(index) = value.a1ArrayDbl ; arr.a2Array(index) = value.a2 }

  override def newBuff(length: Int): PolygonLLPairBuff[A2] = new PolygonLLPairBuff[A2](new ArrayBuffer[Array[Double]](4), new ArrayBuffer[A2](4))
  override def buffToBB(buff: PolygonLLPairBuff[A2]): PolygonLLPairArr[A2] = new PolygonLLPairArr[A2](buff.b1Buffer.toArray, buff.b2Buffer.toArray)

  override def b1Builder: PolygonLikeMapBuilder[LatLong, PolygonLL] = LatLong.polygonBuildImplicit
  override def b1ArrBuilder: ArrMapBuilder[PolygonLL, PolygonLLArr] = PolygonLL.arrMapBuildImplicit
  override def arrFromArrAndArray(b1Arr: PolygonLLArr, b2s: Array[A2]): PolygonLLPairArr[A2] = new PolygonLLPairArr[A2](b1Arr.unsafeArrayOfArrays, b2s)
  override def newB1Buff(): PolygonLLBuff = PolygonLLBuff()
  override def fromArrays(arrayArrayDbl: Array[Array[Double]], a2Array: Array[A2]): PolygonLLPairArr[A2] = new PolygonLLPairArr[A2](arrayArrayDbl, a2Array)
}

class PolygonLLPairBuff[A2](val b1Buffer: ArrayBuffer[Array[Double]], val b2Buffer: ArrayBuffer[A2]) extends
  SeqLikeDblNPairBuff[LatLong, PolygonLL, A2, PolygonLLPair[A2]]
{ override type ThisT = PolygonLLPairBuff[A2]
  override def unsafeSetElem(i: Int, value: PolygonLLPair[A2]): Unit = { b1Buffer(i) = value.a1ArrayDbl; b2Buffer(i) = value.a2 }
  override def fElemStr: PolygonLLPair[A2] => String = _.toString
  override def typeStr: String = "PolygonLLPairBuff"
  override def apply(index: Int): PolygonLLPair[A2] = new PolygonLLPair[A2](b1Buffer(index), b2Buffer(index))
}