/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._, annotation._, reflect.ClassTag, collection.mutable.ArrayBuffer

/** A [[PolygonHC]], a Polygon with the vertices defined in [[HCoord]]s paired with an object of type A2. */
class PolygonHCPair[A2](val a1ArrayInt: Array[Int], val a2: A2)extends PolygonLikeIntNPair[HCoord, PolygonHC, A2]// with SpecialT
{ def a1: PolygonHC = new PolygonHC(a1ArrayInt)
  def polygonPair(f: HCoord => Pt2): PolygonPair[A2] = new PolygonPair[A2](a1.toPolygon(f).unsafeArray, a2)
}

/** Companion object for a [[PolygonHC]], a Polygon with the vertices defined in [[HCoord]]s paired with an object of type A2. Contains implicit
 * build instance for [[PolygonHCPArr]]. */
object PolygonHCPair {
  implicit def buildImplicit[A2](implicit ct: ClassTag[A2]): ArrMapBuilder[PolygonHCPair[A2], PolygonHCPairArr[A2]] = new PolygonHCPairBuild[A2]
}

/** An Arr of  [[PolygonHC]] pairs. A Polygon with the vertices defined in [[HCoord]]s paired with an object of type A2. */
final class PolygonHCPairArr[A2](val a1ArrayInts: Array[Array[Int]], val a2Array: Array[A2]) extends
  PolygonLikeIntNPairArr[HCoord, PolygonHC, PolygonHCArr, A2, PolygonHCPair[A2]]
{ override type ThisT = PolygonHCPairArr[A2]
  override def fElemStr: PolygonHCPair[A2] => String = _.toString
  override def typeStr: String = "PolygonHCPairArray"
  override def a1Arr: PolygonHCArr = new PolygonHCArr(a1ArrayInts)
  override def newFromArrays(array1: Array[Array[Int]], array2: Array[A2]): PolygonHCPairArr[A2] = new PolygonHCPairArr[A2](array1, array2)
  override def a1FromArrayInt(array: Array[Int]): PolygonHC = new PolygonHC(array)
  override def elemFromComponents(a1: Array[Int], a2: A2): PolygonHCPair[A2] = new PolygonHCPair[A2](a1, a2)
}

/** A builder for ann Arr of  [[PolygonHC]] pairs. A Polygon with the vertices defined in [[HCoord]]s paired with an object of type A2. */
final class PolygonHCPairBuild[A2](implicit val b2ClassTag: ClassTag[A2], @unused notB: Not[SpecialT]#L[A2]) extends
  SeqLikeIntNPairArrBuilder[HCoord, PolygonHC, PolygonHCArr, A2, PolygonHCPair[A2], PolygonHCPairArr[A2]]
{ override type BuffT = PolygonHCPairBuff[A2]
  override type B1BuffT = PolygonHCBuff
  override def uninitialised(length: Int): PolygonHCPairArr[A2] = new PolygonHCPairArr[A2](new Array[Array[Int]](length), new Array[A2](length))

  override def indexSet(seqLike: PolygonHCPairArr[A2], index: Int, elem: PolygonHCPair[A2]): Unit =
  { seqLike.a1ArrayInts(index) = elem.a1ArrayInt ; seqLike.a2Array(index) = elem.a2 }

  override def newBuff(length: Int): PolygonHCPairBuff[A2] = new PolygonHCPairBuff[A2](new ArrayBuffer[Array[Int]](4), new ArrayBuffer[A2](4))
  override def buffToSeqLike(buff: PolygonHCPairBuff[A2]): PolygonHCPairArr[A2] = new PolygonHCPairArr[A2](buff.b1Buffer.toArray, buff.b2Buffer.toArray)

  /** Construct the final target [[Arr]] type from an Array of Arrays of [[Int]]s and an Array of B2. */
  override def fromArrays(arrayArrayInt: Array[Array[Int]], a2Array: Array[A2]): PolygonHCPairArr[A2] = ???

  /** Builder for the first element of the pair of type B1. This method will need to be overwritten to a narrow type. */
  override def b1Builder: SeqLikeMapBuilder[HCoord, PolygonHC] = ???//PolygonHC.arrBuildImplicit

  /** Builder for an Arr of the first element of the pair. */
  override def b1ArrBuilder: ArrMapBuilder[PolygonHC, PolygonHCArr] = ???

  /** Builder for the sequence of pairs, takes the results of the other two builder methods to produce the end product. Pun intended */
  override def arrFromArrAndArray(b1Arr: PolygonHCArr, b2s: Array[A2]): PolygonHCPairArr[A2] = ???

  override def newB1Buff(): PolygonHCBuff = PolygonHCBuff()
}

/** A buffer of  [[PolygonHC]] pairs. A Polygon with the vertices defined in [[HCoord]]s paired with an object of type A2. */
class PolygonHCPairBuff[A2](val b1Buffer: ArrayBuffer[Array[Int]], val b2Buffer: ArrayBuffer[A2]) extends
  SeqLikeIntNPairBuff[HCoord, PolygonHC, A2, PolygonHCPair[A2]]
{ override type ThisT = PolygonHCPairBuff[A2]
  override def setElemUnsafe(i: Int, newElem: PolygonHCPair[A2]): Unit = { b1Buffer(i) = newElem.a1ArrayInt; b2Buffer(i) = newElem.a2 }
  override def fElemStr: PolygonHCPair[A2] => String = _.toString
  override def typeStr: String = "PolygonHCPairBuff"
  override def apply(index: Int): PolygonHCPair[A2] = new PolygonHCPair[A2](b1Buffer(index), b2Buffer(index))
}