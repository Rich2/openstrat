/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._, annotation._, reflect.ClassTag, collection.mutable.ArrayBuffer

/** A [[PolygonHC]], a Polygon with the vertices defined in [[HCoord]]s paired with an object of type A2. */
class PolygonHCPair[A2](val unsafeArray: Array[Int], val a2: A2)extends PolygonLikePair[HCoord, PolygonHC, A2]// with SpecialT
{
  def polygon: PolygonHC = new PolygonHC(unsafeArray)
  def polygonPair(f: HCoord => Pt2): PolygonPair[A2] = new PolygonPair[A2](polygon.toPolygon(f).unsafeArray, a2)
}

/** Companion object for a [[PolygonHC]], a Polygon with the vertices defined in [[HCoord]]s paired with an object of type A2. Contains implicit
 * build instance for [[PolygonHCPArr]]. */
object PolygonHCPair {
  implicit def buildImplicit[A2](implicit ct: ClassTag[A2]): ArrBuilder[PolygonHCPair[A2], PolygonHCPairArr[A2]] = new PolygonHCPairBuild[A2]
}

final class PolygonHCPairArr[A2](val arrayArrayInt: Array[Array[Int]], val a2Array: Array[A2]) extends PolygonLikePairArr[HCoord, PolygonHC, A2, PolygonHCPair[A2]]
{ override type ThisT = PolygonHCPairArr[A2]
  override def unsafeSameSize(length: Int): PolygonHCPairArr[A2] = new PolygonHCPairArr[A2](new Array[Array[Int]](arrayArrayInt.length), a2Array)
  override def unsafeSetElem(i: Int, value: PolygonHCPair[A2]): Unit = { arrayArrayInt(i) = value.unsafeArray; a2Array(i) = value.a2 }
  override def fElemStr: PolygonHCPair[A2] => String = _.toString
  override def typeStr: String = "PolygonHCPairArray"
  override def sdIndex(index: Int): PolygonHCPair[A2] = new PolygonHCPair[A2](arrayArrayInt(index), a2Array(index))

  override def polygonArr: PolygonHCArr = new PolygonHCArr(arrayArrayInt)
}

final class PolygonHCPairBuild[A2](implicit ct: ClassTag[A2], @unused notB: Not[SpecialT]#L[A2]) extends ArrBuilder[PolygonHCPair[A2], PolygonHCPairArr[A2]]
{ override type BuffT = PolygonHCPairBuff[A2]
  override def newArr(length: Int): PolygonHCPairArr[A2] = new PolygonHCPairArr[A2](new Array[Array[Int]](length), new Array[A2](length))

  override def arrSet(arr: PolygonHCPairArr[A2], index: Int, value: PolygonHCPair[A2]): Unit =
  { arr.arrayArrayInt(index) = value.unsafeArray ; arr.a2Array(index) = value.a2 }

  override def buffGrow(buff: PolygonHCPairBuff[A2], value: PolygonHCPair[A2]): Unit = ???
  override def buffGrowArr(buff: PolygonHCPairBuff[A2], arr: PolygonHCPairArr[A2]): Unit = ???
  override def newBuff(length: Int): PolygonHCPairBuff[A2] = new PolygonHCPairBuff[A2](new ArrayBuffer[Array[Int]](4), new ArrayBuffer[A2](4))
  override def buffToBB(buff: PolygonHCPairBuff[A2]): PolygonHCPairArr[A2] = new PolygonHCPairArr[A2](buff.arrayIntBuff.toArray, buff.a2Buff.toArray)
}

class PolygonHCPairBuff[A2](val arrayIntBuff: ArrayBuffer[Array[Int]], val a2Buff: ArrayBuffer[A2]) extends PairBuff[PolygonHC, A2, PolygonHCPair[A2]]
{ override type ThisT = PolygonHCPairBuff[A2]
  override def unsafeSetElem(i: Int, value: PolygonHCPair[A2]): Unit = { arrayIntBuff(i) = value.unsafeArray; a2Buff(i) = value.a2 }
  override def fElemStr: PolygonHCPair[A2] => String = _.toString
  override def typeStr: String = "PolygonHCPairBuff"
  override def sdIndex(index: Int): PolygonHCPair[A2] = new PolygonHCPair[A2](arrayIntBuff(index), a2Buff(index))
}