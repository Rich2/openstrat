/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import collection.mutable.ArrayBuffer, reflect.ClassTag

/** A [[PolygonLike]] object paired with an object of type A2.]] */
trait PolygonLikePair[A1V, A1 <: PolygonLike[A1V], A2] extends ElemSeqSpecPair[A1V, A1, A2]

/** An [[Arr]] of [[PolygonLikePair]]s stored efficiently allowing maping between different [[PolygonLike]] types while keeping the A2 values unchanged. */
trait PolygonLikePairArr[A1V, A1 <: PolygonLike[A1V], A1Arr <: Arr[A1], A2, A <: PolygonLikePair[A1V, A1, A2]] extends
  SeqSpecPairArr[A1V, A1, A1Arr, A2, A]
{
  /** Maps this to a new [PolygonLikePairArr]] by mapping [[PolygonLike]]s to new [[PolygonLike]]s of type B1 leaving the second parts of the pairs
   * unchanged. */
  def polygonMapToPair[B1V <: ElemValueN, B1 <: PolygonLike[B1V], ArrB1 <: Arr[B1], B <: PolygonLikePair[B1V, B1, A2],
    ArrB <: PolygonLikePairArr[B1V, B1, ArrB1, A2, B]](f: A1V => B1V)(implicit build: PolygonLikePairArrBuilder[B1V, B1, ArrB1, A2, B, ArrB]): ArrB =
  { val polygons = a1Arr.map(p => p.map[B1V, B1](f)(build.b1Builder))(build.b1ArrBuilder)
    build.pairArrBuilder(polygons, a2Array)
  }
}

trait PolygonLikePairArrBuilder[B1V, B1 <: PolygonLike[B1V], ArrB1 <: Arr[B1], B2, B <: PolygonLikePair[B1V, B1, B2],
  ArrB <: PolygonLikePairArr[B1V, B1, ArrB1, B2, B]] extends SeqSpecPairArrBuilder[B1V, B1, ArrB1, B2, B, ArrB]
{ /** Builder for the first element of the pair of type B1, in this case a [[PolygonLike]]. The return type has been narrowed as it is needed for the
   * polygonMapPair method on [[PolygonLikePairArr]]. */
  override def b1Builder: PolygonLikeBuilder[B1V, B1]
}

trait PolygonLikeDblNPair[A1V <: ElemDblN, A1 <: PolygonDblN[A1V], A2] extends PolygonLikePair[A1V, A1, A2] with SeqSpecDblNPair[A1V, A1, A2]
{ def a1ArrayDbl: Array[Double]
}

trait PolygonLikeDblNPairArr[A1V <: ElemDblN, A1 <: PolygonDblN[A1V], ArrA1 <: Arr[A1], A2, A <: PolygonLikeDblNPair[A1V, A1, A2]] extends
  PolygonLikePairArr[A1V, A1, ArrA1, A2, A] with SeqSpecDblNPairArr[A1V, A1, ArrA1, A2, A]
{
  type ThisT <: PolygonLikeDblNPairArr[A1V, A1, ArrA1, A2, A]
  def arrayArrayDbl: Array[Array[Double]]
  def fromArrays(array1: Array[Array[Double]], array2: Array[A2]): ThisT// = ???
  def a1Buff: ArrayDblBuff[A1]

  def filterOn1(f: A1 => Boolean)(implicit ct: ClassTag[A2]): ThisT =
  { val buff1 = a1Buff
    val buff2 = new ArrayBuffer[A2]()
    var i = 0
    a1Arr.foreach { a1 =>
      if (f(a1)) {
        buff1.grow(a1)
        buff2.append(a2Array(i))
      }
      i += 1
    }
    fromArrays(buff1.arrayArrayDbl, buff2.toArray)
  }
}

trait PolygonLikeDblNPairArrBuilder[B1V <: ElemDblN, B1 <: PolygonDblN[B1V], ArrB1 <: Arr[B1], A2, B <: PolygonLikeDblNPair[B1V, B1, A2],
  ArrB <: PolygonLikeDblNPairArr[B1V, B1, ArrB1, A2, B]] extends PolygonLikePairArrBuilder[B1V, B1, ArrB1, A2, B, ArrB]
{
  type B1BuffT <: ArrayDblBuff[B1]
  def fromArrayArrayDbl(arrayArrayDbl: Array[Array[Double]], a2Arr: RArr[A2]): ArrB

  override def b1BuffGrow(buff: B1BuffT, newElem: B1): Unit = buff.unsafeBuffer.append(newElem.unsafeArray)
}

trait PolygonLikeDbl2Pair[A1V <: ElemDbl2, A1 <: PolygonDbl2[A1V], A2] extends PolygonLikeDblNPair[A1V, A1, A2] //with SeqSpecDbl2Pair[A1V, A1, A2]

trait PolygonLikeDbl2PairArr[A1V <: ElemDbl2, A1 <: PolygonDbl2[A1V], ArrA1 <: Arr[A1], A2, A <: PolygonLikeDbl2Pair[A1V, A1, A2]] extends
  PolygonLikeDblNPairArr[A1V, A1, ArrA1, A2, A]// with SeqSpecDbl2PairArr[A1V, A1, ArrA1, A2, A]

trait PolygonLikeIntNPair[A1V <: ElemIntN, A1 <: PolygonIntN[A1V], A2] extends PolygonLikePair[A1V, A1, A2] with SeqSpecIntNPair[A1V, A1, A2]
{ def a1ArrayInt: Array[Int]
}

trait PolygonLikeIntNPairArr[A1V <: ElemIntN, A1 <: PolygonIntN[A1V], ArrA1 <: Arr[A1], A2, A <: PolygonLikeIntNPair[A1V, A1, A2]] extends
  PolygonLikePairArr[A1V, A1, ArrA1, A2, A] with SeqSpecIntNPairArr[A1V, A1, ArrA1, A2, A]
{
  type ThisT <: PolygonLikeIntNPairArr[A1V, A1, ArrA1, A2, A]
  def arrayArrayInt: Array[Array[Int]]
  def fromArrays(array1: Array[Array[Int]], array2: Array[A2]): ThisT// = ???
  def a1Buff: ArrayIntBuff[A1]

  def filterOn1(f: A1 => Boolean)(implicit ct: ClassTag[A2]): ThisT =
  { val buff1 = a1Buff
    val buff2 = new ArrayBuffer[A2]()
    var i = 0
    a1Arr.foreach { a1 =>
      if (f(a1)) {
        buff1.grow(a1)
        buff2.append(a2Array(i))
      }
      i += 1
    }
    fromArrays(buff1.arrayArrayInt, buff2.toArray)
  }
}

trait PolygonIntsLikePairArrBuilder[B1V <: ElemIntN, B1 <: PolygonIntN[B1V], ArrB1 <: Arr[B1], A2, B <: PolygonLikeIntNPair[B1V, B1, A2],
  ArrB <: PolygonLikeIntNPairArr[B1V, B1, ArrB1, A2, B]] extends PolygonLikePairArrBuilder[B1V, B1, ArrB1, A2, B, ArrB]
{
  // override def newArr(newPolygonArr: Arr[PB], a2Arr: Arr[A2]): ArrB = ???// fromArrayArrayInt(newPolygonArr.arrayArrayInt, a2Arr)
  def fromArrayArrayInt(arrayArrayInt: Array[Array[Int]], a2Arr: RArr[A2]): ArrB
}