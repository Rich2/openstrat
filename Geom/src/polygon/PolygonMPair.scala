/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import geom._, annotation._, reflect.ClassTag, collection.mutable.ArrayBuffer

class PolygonMPair[A2](val unsafeArray: Array[Double], val a2: A2) extends PolygonDblsPair[PtM2, PolygonM, A2]{
  override def polygon: PolygonM = new PolygonM(unsafeArray)
}

object PolygonMPair {
  implicit def buildImplicit[A2](implicit ct: ClassTag[A2]): ArrBuilder[PolygonMPair[A2], PolygonMPairArr[A2]] = new PolygonMPairBuild[A2]
}

final class PolygonMPairArr[A2](val arrayArrayDbl: Array[Array[Double]], val a2Array: Array[A2]) extends PolygonLikePairArr[PtM2, PolygonM, A2, PolygonMPair[A2]]
{ override type ThisT = PolygonMPairArr[A2]
  override def unsafeSameSize(length: Int): PolygonMPairArr[A2] = new PolygonMPairArr[A2](new Array[Array[Double]](arrayArrayDbl.length), a2Array)
  override def unsafeSetElem(i: Int, value: PolygonMPair[A2]): Unit = { arrayArrayDbl(i) = value.unsafeArray; a2Array(i) = value.a2 }
  override def fElemStr: PolygonMPair[A2] => String = _.toString
  override def typeStr: String = "PolygonMPairArray"
  override def sdIndex(index: Int): PolygonMPair[A2] = new PolygonMPair[A2](arrayArrayDbl(index), a2Array(index))
  override def polygonArr: PolygonMArr = new PolygonMArr(arrayArrayDbl)
}

final class PolygonMPairBuild[A2](implicit ct: ClassTag[A2], @unused notB: Not[SpecialT]#L[A2]) extends ArrBuilder[PolygonMPair[A2], PolygonMPairArr[A2]]
{ override type BuffT = PolygonMPairBuff[A2]
  override def newArr(length: Int): PolygonMPairArr[A2] = new PolygonMPairArr[A2](new Array[Array[Double]](length), new Array[A2](length))

  override def arrSet(arr: PolygonMPairArr[A2], index: Int, value: PolygonMPair[A2]): Unit =
  { arr.arrayArrayDbl(index) = value.unsafeArray ; arr.a2Array(index) = value.a2 }

  override def buffGrow(buff: PolygonMPairBuff[A2], value: PolygonMPair[A2]): Unit = ???
  override def buffGrowArr(buff: PolygonMPairBuff[A2], arr: PolygonMPairArr[A2]): Unit = ???
  override def newBuff(length: Int): PolygonMPairBuff[A2] = new PolygonMPairBuff[A2](new ArrayBuffer[Array[Double]](4), new ArrayBuffer[A2](4))
  override def buffToBB(buff: PolygonMPairBuff[A2]): PolygonMPairArr[A2] = new PolygonMPairArr[A2](buff.arrayDoubleBuff.toArray, buff.a2Buff.toArray)
}

class PolygonMPairBuff[A2](val arrayDoubleBuff: ArrayBuffer[Array[Double]], val a2Buff: ArrayBuffer[A2]) extends SeqDefPairBuff[PolygonM, A2, PolygonMPair[A2]]
{ override type ThisT = PolygonMPairBuff[A2]
  override def unsafeSetElem(i: Int, value: PolygonMPair[A2]): Unit = { arrayDoubleBuff(i) = value.unsafeArray; a2Buff(i) = value.a2 }
  override def fElemStr: PolygonMPair[A2] => String = _.toString
  override def typeStr: String = "PolygonMPairBuff"
  override def sdIndex(index: Int): PolygonMPair[A2] = new PolygonMPair[A2](arrayDoubleBuff(index), a2Buff(index))
}