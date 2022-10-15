/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import annotation._, reflect.ClassTag, collection.mutable.ArrayBuffer

class PolygonM3Pair[A2](val unsafeArray: Array[Double], val a2: A2) extends PolygonLikeDblsPair[PtM3, PolygonM3, A2] with SpecialT {
  override def polygon: PolygonM3 = new PolygonM3(unsafeArray)
}

object PolygonM3Pair
{
  def apply[A2](poly: PolygonM3, a2: A2): PolygonM3Pair[A2] = new PolygonM3Pair[A2](poly.unsafeArray, a2)
}

final class PolygonM3PairArr[A2](val arrayArrayDbl: Array[Array[Double]], val a2Array: Array[A2]) extends
  PolygonDblsLikePairArr[PtM3, PolygonM3, PolygonM3Arr, A2, PolygonM3Pair[A2]]
{ override type ThisT = PolygonM3PairArr[A2]
  override def unsafeSameSize(length: Int): PolygonM3PairArr[A2] = new PolygonM3PairArr[A2](new Array[Array[Double]](arrayArrayDbl.length), a2Array)
  override def unsafeSetElem(i: Int, value: PolygonM3Pair[A2]): Unit = { arrayArrayDbl(i) = value.unsafeArray; a2Array(i) = value.a2 }
  override def fElemStr: PolygonM3Pair[A2] => String = _.toString
  override def typeStr: String = "PolygonM3PairArray"
  override def apply(index: Int): PolygonM3Pair[A2] = new PolygonM3Pair[A2](arrayArrayDbl(index), a2Array(index))
  override def a1Arr: PolygonM3Arr = new PolygonM3Arr(arrayArrayDbl)
  override def a1Buff: ArrayDblBuff[PolygonM3] = PolygonM3Buff()
  override def fromArrays(array1: Array[Array[Double]], array2: Array[A2]): PolygonM3PairArr[A2] = new PolygonM3PairArr[A2](array1, array2)

  override  def filterOn1(f: PolygonM3 => Boolean)(implicit ct: ClassTag[A2]): PolygonM3PairArr[A2] =
  { val buff1 = PolygonM3Buff()
    val buff2 = new ArrayBuffer[A2]()
    var i = 0
    a1Arr.foreach{ (a1: PolygonM3) =>
      if (f(a1)){
        buff1.grow(a1)
        buff2.append(a2Array(i))
      }
      i += 1
    }
    new PolygonM3PairArr[A2](buff1.arrayArrayDbl, buff2.toArray)
  }
}

final class PolygonM3PairBuild[A2](implicit ct: ClassTag[A2], @unused notB: Not[SpecialT]#L[A2]) extends
  PolygonLikePairArrBuilder[PtM3, PolygonM3, PolygonM3Arr, A2, PolygonM3Pair[A2], PolygonM3PairArr[A2]]
{ override type BuffT = PolygonM3PairBuff[A2]
  override def newArr(length: Int): PolygonM3PairArr[A2] = new PolygonM3PairArr[A2](new Array[Array[Double]](length), new Array[A2](length))

  override def arrSet(arr: PolygonM3PairArr[A2], index: Int, value: PolygonM3Pair[A2]): Unit =
  { arr.arrayArrayDbl(index) = value.unsafeArray ; arr.a2Array(index) = value.a2 }

  override def buffGrow(buff: PolygonM3PairBuff[A2], value: PolygonM3Pair[A2]): Unit = ???
  override def buffGrowArr(buff: PolygonM3PairBuff[A2], arr: PolygonM3PairArr[A2]): Unit = ???
  override def newBuff(length: Int): PolygonM3PairBuff[A2] = new PolygonM3PairBuff[A2](new ArrayBuffer[Array[Double]](4), new ArrayBuffer[A2](4))
  override def buffToBB(buff: PolygonM3PairBuff[A2]): PolygonM3PairArr[A2] = new PolygonM3PairArr[A2](buff.arrayDoubleBuff.toArray, buff.a2Buff.toArray)

  /** Builder for the first element of the pair of type B1, in this case a [[PolygonLike]]. The return type has been narrowed as it is needed for the
   * polygonMapPair method on [[PolygonLikePairArr]]. */
  override def b1Builder: PolygonLikeBuilder[PtM3, PolygonM3] = PtM3.polygonBuildImplicit

  /** Builder for an Arr of the first element of the pair. */
  override def b1ArrBuilder: ArrBuilder[PolygonM3, PolygonM3Arr] = PolygonM3.arrBuildImplicit

  /** Builder for the sequence of pairs, takes the results of the other two builder methods to produce the end product. Pun intended */
  override def pairArrBuilder(polygonArr: PolygonM3Arr, a2s: Array[A2]): PolygonM3PairArr[A2] = new PolygonM3PairArr[A2](polygonArr.unsafeArrayOfArrays, a2s)
}

class PolygonM3PairBuff[A2](val arrayDoubleBuff: ArrayBuffer[Array[Double]], val a2Buff: ArrayBuffer[A2]) extends
  SeqSpecPairBuff[PtM3, PolygonM3, A2, PolygonM3Pair[A2]]
{ override type ThisT = PolygonM3PairBuff[A2]
  override def unsafeSetElem(i: Int, value: PolygonM3Pair[A2]): Unit = { arrayDoubleBuff(i) = value.unsafeArray; a2Buff(i) = value.a2 }
  override def fElemStr: PolygonM3Pair[A2] => String = _.toString
  override def typeStr: String = "PolygonM3PairBuff"
  override def apply(index: Int): PolygonM3Pair[A2] = new PolygonM3Pair[A2](arrayDoubleBuff(index), a2Buff(index))
}