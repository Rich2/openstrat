/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import annotation._, reflect.ClassTag, collection.mutable.ArrayBuffer

/** [[PairElem]] where the first component of the pair is a [[PolygonGen]].  */
final class PolygonGenPair[A2](val a1ArrayDbl: Array[Double], val a2: A2) extends PolygonLikeDbl2Pair[Pt2, PolygonGen, A2]
{ def a1: PolygonGen = new PolygonGen(a1ArrayDbl)
}

object PolygonGenPair
{ implicit def buildImplicit[A2](implicit ct: ClassTag[A2]): BuilderArrMap[PolygonGenPair[A2], PolygonGenPairArr[A2]] = new PolygonGenPairBuilder[A2]
}

final class PolygonGenPairArr[A2](val a1ArrayDbls: Array[Array[Double]], val a2Array: Array[A2]) extends
  PolygonLikeDbl2PairArr[Pt2, PolygonGen, PolygonGenArr, A2, PolygonGenPair[A2]]
{ override type ThisT = PolygonGenPairArr[A2]
  override def setElemUnsafe(i: Int, newElem: PolygonGenPair[A2]): Unit = { a1ArrayDbls(i) = newElem.a1ArrayDbl; a2Array(i) = newElem.a2 }
  override def fElemStr: PolygonGenPair[A2] => String = _.toString
  override def typeStr: String = "PolygonPairArray"
  override def apply(index: Int): PolygonGenPair[A2] = new PolygonGenPair[A2](a1ArrayDbls(index), a2Array(index))
  override def a1Arr: PolygonGenArr = new PolygonGenArr(a1ArrayDbls)
  override def newFromArrays(array1: Array[Array[Double]], array2: Array[A2]): PolygonGenPairArr[A2] = new PolygonGenPairArr[A2](array1, array2)
 // override def a1Buff: PolygonBuff = PolygonBuff()
  override def a1FromArrayDbl(array: Array[Double]): PolygonGen = new PolygonGen(array)
}

final class PolygonGenPairBuilder[A2](implicit val b2ClassTag: ClassTag[A2], @unused notB: Not[SpecialT]#L[A2]) extends
  PolygonLikeDblNPairArrBuilder[Pt2, PolygonGen, PolygonGenArr, A2, PolygonGenPair[A2], PolygonGenPairArr[A2]]
{ override type BuffT = PolygonGenPairBuff[A2]
  override type B1BuffT = PolygonGenBuff
  override def uninitialised(length: Int): PolygonGenPairArr[A2] = new PolygonGenPairArr[A2](new Array[Array[Double]](length), new Array[A2](length))

  override def indexSet(seqLike: PolygonGenPairArr[A2], index: Int, newElem: PolygonGenPair[A2]): Unit =
  { seqLike.a1ArrayDbls(index) = newElem.a1ArrayDbl ; seqLike.a2Array(index) = newElem.a2 }
  override def newBuff(length: Int): PolygonGenPairBuff[A2] = PolygonGenPairBuff(length)
  override def buffToSeqLike(buff: PolygonGenPairBuff[A2]): PolygonGenPairArr[A2] = new PolygonGenPairArr[A2](buff.b1Buffer.toArray, buff.b2Buffer.toArray)
  override def b1Builder: PolygonLikeMapBuilder[Pt2, PolygonGen] = Pt2.polygonMapBuildEv
  override def b1ArrBuilder: BuilderArrMap[PolygonGen, PolygonGenArr] = PolygonGen.buildArrMapEv
  override def arrFromArrAndArray(b1Arr: PolygonGenArr, b2s: Array[A2]): PolygonGenPairArr[A2] = new PolygonGenPairArr[A2](b1Arr.unsafeArrayOfArrays, b2s)
  override def newB1Buff(): PolygonGenBuff = PolygonGenBuff()
  override def fromArrays(arrayArrayDbl: Array[Array[Double]], a2Array: Array[A2]): PolygonGenPairArr[A2] = new PolygonGenPairArr[A2](arrayArrayDbl, a2Array)
}

class PolygonGenPairBuff[A2](val b1Buffer: ArrayBuffer[Array[Double]], val b2Buffer: ArrayBuffer[A2]) extends SeqLikeDblNPairBuff[Pt2, PolygonGen, A2, PolygonGenPair[A2]]
{ override type ThisT = PolygonGenPairBuff[A2]
  override def setElemUnsafe(i: Int, newElem: PolygonGenPair[A2]): Unit = { b1Buffer(i) = newElem.a1ArrayDbl; b2Buffer(i) = newElem.a2 }
  override def fElemStr: PolygonGenPair[A2] => String = _.toString
  override def typeStr: String = "PolygonPairBuff"
  override def apply(index: Int): PolygonGenPair[A2] = new PolygonGenPair[A2](b1Buffer(index), b2Buffer(index))
}

object PolygonGenPairBuff
{ def apply[A2](n: Int): PolygonGenPairBuff[A2] = new PolygonGenPairBuff[A2](new ArrayBuffer[Array[Double]](n * 8), new ArrayBuffer[A2](n))
}