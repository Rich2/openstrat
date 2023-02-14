/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import annotation._, reflect.ClassTag, collection.mutable.ArrayBuffer

final class PolygonPair[A2](val a1ArrayDbl: Array[Double], val a2: A2) extends PolygonLikeDbl2Pair[Pt2, Polygon, A2]
{
  def a1: Polygon = new PolygonGen(a1ArrayDbl)
}

object PolygonPair {
  implicit def buildImplicit[A2](implicit ct: ClassTag[A2]): ArrMapBuilder[PolygonPair[A2], PolygonPairArr[A2]] = new PolygonPairBuilder[A2]
}

final class PolygonPairArr[A2](val a1ArrayDbls: Array[Array[Double]], val a2Array: Array[A2]) extends
  PolygonLikeDbl2PairArr[Pt2, Polygon, PolygonArr, A2, PolygonPair[A2]]
{ override type ThisT = PolygonPairArr[A2]
  override def setElemUnsafe(i: Int, newElem: PolygonPair[A2]): Unit = { a1ArrayDbls(i) = newElem.a1ArrayDbl; a2Array(i) = newElem.a2 }
  override def fElemStr: PolygonPair[A2] => String = _.toString
  override def typeStr: String = "PolygonPairArray"
  override def apply(index: Int): PolygonPair[A2] = new PolygonPair[A2](a1ArrayDbls(index), a2Array(index))
  override def a1Arr: PolygonArr = new PolygonArr(a1ArrayDbls)
  override def newFromArrays(array1: Array[Array[Double]], array2: Array[A2]): PolygonPairArr[A2] = new PolygonPairArr[A2](array1, array2)
 // override def a1Buff: PolygonBuff = PolygonBuff()
  override def a1FromArrayDbl(array: Array[Double]): Polygon = new PolygonGen(array)
}

final class PolygonPairBuilder[A2](implicit val b2ClassTag: ClassTag[A2], @unused notB: Not[SpecialT]#L[A2]) extends
  PolygonLikeDblNPairArrBuilder[Pt2, Polygon, PolygonArr, A2, PolygonPair[A2], PolygonPairArr[A2]]
{ override type BuffT = PolygonPairBuff[A2]
  override type B1BuffT = PolygonBuff
  override def uninitialised(length: Int): PolygonPairArr[A2] = new PolygonPairArr[A2](new Array[Array[Double]](length), new Array[A2](length))

  override def indexSet(seqLike: PolygonPairArr[A2], index: Int, elem: PolygonPair[A2]): Unit =
  { seqLike.a1ArrayDbls(index) = elem.a1ArrayDbl ; seqLike.a2Array(index) = elem.a2 }
  override def newBuff(length: Int): PolygonPairBuff[A2] = PolygonPairBuff(length)
  override def buffToSeqLike(buff: PolygonPairBuff[A2]): PolygonPairArr[A2] = new PolygonPairArr[A2](buff.b1Buffer.toArray, buff.b2Buffer.toArray)
  override def b1Builder: PolygonLikeMapBuilder[Pt2, Polygon] = Pt2.polygonMapBuildEv
  override def b1ArrBuilder: ArrMapBuilder[Polygon, PolygonArr] = Polygon.arrBuildImplicit
  override def arrFromArrAndArray(b1Arr: PolygonArr, b2s: Array[A2]): PolygonPairArr[A2] = new PolygonPairArr[A2](b1Arr.unsafeArrayOfArrays, b2s)
  override def newB1Buff(): PolygonBuff = PolygonBuff()
  override def fromArrays(arrayArrayDbl: Array[Array[Double]], a2Array: Array[A2]): PolygonPairArr[A2] = new PolygonPairArr[A2](arrayArrayDbl, a2Array)
}

class PolygonPairBuff[A2](val b1Buffer: ArrayBuffer[Array[Double]], val b2Buffer: ArrayBuffer[A2]) extends SeqLikeDblNPairBuff[Pt2, Polygon, A2, PolygonPair[A2]]
{ override type ThisT = PolygonPairBuff[A2]
  override def setElemUnsafe(i: Int, newElem: PolygonPair[A2]): Unit = { b1Buffer(i) = newElem.a1ArrayDbl; b2Buffer(i) = newElem.a2 }
  override def fElemStr: PolygonPair[A2] => String = _.toString
  override def typeStr: String = "PolygonPairBuff"
  override def apply(index: Int): PolygonPair[A2] = new PolygonPair[A2](b1Buffer(index), b2Buffer(index))
}

object PolygonPairBuff
{ def apply[A2](n: Int): PolygonPairBuff[A2] = new PolygonPairBuff[A2](new ArrayBuffer[Array[Double]](n * 8), new ArrayBuffer[A2](n))
}