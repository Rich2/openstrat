/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import annotation._, reflect.ClassTag, collection.mutable.ArrayBuffer

final class PolygonPair[A2](val unsafeArray: Array[Double], val a2: A2) extends PolygonDblsPair[Pt2, Polygon, A2]
{
  def polygon: Polygon = new PolygonGen(unsafeArray)
}

object PolygonPair {
  implicit def buildImplicit[A2](implicit ct: ClassTag[A2]): ArrBuilder[PolygonPair[A2], PolygonPairArr[A2]] = new PolygonPairBuilder[A2]
}

final class PolygonPairArr[A2](val arrayArrayDbl: Array[Array[Double]], val a2Array: Array[A2]) extends PolygonDblsLikePairArr[Pt2, Polygon, A2, PolygonPair[A2]]
{ override type ThisT = PolygonPairArr[A2]
  override def unsafeSameSize(length: Int): PolygonPairArr[A2] = new PolygonPairArr[A2](new Array[Array[Double]](arrayArrayDbl.length), a2Array)
  override def unsafeSetElem(i: Int, value: PolygonPair[A2]): Unit = { arrayArrayDbl(i) = value.unsafeArray; a2Array(i) = value.a2 }
  override def fElemStr: PolygonPair[A2] => String = _.toString
  override def typeStr: String = "PolygonPairArray"
  override def sdIndex(index: Int): PolygonPair[A2] = new PolygonPair[A2](arrayArrayDbl(index), a2Array(index))

  override def polygonArr: PolygonArr = new PolygonArr(arrayArrayDbl)
}

final class PolygonPairBuilder[A2](implicit ct: ClassTag[A2], @unused notB: Not[SpecialT]#L[A2]) extends
  PolygonLikePairArrBuilder[Pt2, Polygon, PolygonArr, A2, PolygonPair[A2], PolygonPairArr[A2]]
{ override type BuffT = PolygonPairBuff[A2]
  override def newArr(length: Int): PolygonPairArr[A2] = new PolygonPairArr[A2](new Array[Array[Double]](length), new Array[A2](length))

  override def arrSet(arr: PolygonPairArr[A2], index: Int, value: PolygonPair[A2]): Unit =
  { arr.arrayArrayDbl(index) = value.unsafeArray ; arr.a2Array(index) = value.a2 }

  override def buffGrow(buff: PolygonPairBuff[A2], value: PolygonPair[A2]): Unit = ???
  override def buffGrowArr(buff: PolygonPairBuff[A2], arr: PolygonPairArr[A2]): Unit = ???
  override def newBuff(length: Int): PolygonPairBuff[A2] = new PolygonPairBuff[A2](new ArrayBuffer[Array[Double]](4), new ArrayBuffer[A2](4))
  override def buffToBB(buff: PolygonPairBuff[A2]): PolygonPairArr[A2] = new PolygonPairArr[A2](buff.arrayDblBuff.toArray, buff.a2Buff.toArray)


  override def b1Builder: PolygonLikeBuilder[Pt2, Polygon] = Pt2.polygonBuildImplicit

  override def b1ArrBuilder: ArrBuilder[Polygon, PolygonArr] = Polygon.arrBuildImplicit

  override def pairArrBuilder(polygonArr: PolygonArr, a2s: Array[A2]): PolygonPairArr[A2] = new PolygonPairArr[A2](polygonArr.unsafeArrayOfArrays, a2s)
}

class PolygonPairBuff[A2](val arrayDblBuff: ArrayBuffer[Array[Double]], val a2Buff: ArrayBuffer[A2]) extends SeqDefPairBuff[Polygon, A2, PolygonPair[A2]]
{ override type ThisT = PolygonPairBuff[A2]
  override def unsafeSetElem(i: Int, value: PolygonPair[A2]): Unit = { arrayDblBuff(i) = value.unsafeArray; a2Buff(i) = value.a2 }
  override def fElemStr: PolygonPair[A2] => String = _.toString
  override def typeStr: String = "PolygonPairBuff"
  override def sdIndex(index: Int): PolygonPair[A2] = new PolygonPair[A2](arrayDblBuff(index), a2Buff(index))
}