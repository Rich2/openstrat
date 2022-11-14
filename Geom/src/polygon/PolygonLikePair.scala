/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A [[PolygonLike]] object paired with an object of type A2.]] */
trait PolygonLikePair[A1V, A1 <: PolygonLike[A1V], A2] extends SeqLikePairElem[A1V, A1, A2]

/** An [[Arr]] of [[PolygonLikePair]]s stored efficiently allowing maping between different [[PolygonLike]] types while keeping the A2 values unchanged. */
trait PolygonLikePairArr[A1V, A1 <: PolygonLike[A1V], A1Arr <: Arr[A1], A2, A <: PolygonLikePair[A1V, A1, A2]] extends
  SeqLikePairArr[A1V, A1, A1Arr, A2, A]
{
  /** Maps this to a new [PolygonLikePairArr]] by mapping [[PolygonLike]]s to new [[PolygonLike]]s of type B1 leaving the second parts of the pairs
   * unchanged. */
  def polygonMapToPair[B1V <: ElemValueN, B1 <: PolygonLike[B1V], ArrB1 <: Arr[B1], B <: PolygonLikePair[B1V, B1, A2],
    ArrB <: PolygonLikePairArr[B1V, B1, ArrB1, A2, B]](f: A1V => B1V)(implicit build: PolygonLikePairArrBuilder[B1V, B1, ArrB1, A2, B, ArrB]): ArrB =
  { val polygons: ArrB1 = a1Arr.map(p => p.map[B1V, B1](f)(build.b1Builder))(build.b1ArrBuilder)
    build.arrFromArrAndArray(polygons, a2Array)
  }
}

trait PolygonLikePairArrBuilder[B1V, B1 <: PolygonLike[B1V], ArrB1 <: Arr[B1], B2, B <: PolygonLikePair[B1V, B1, B2],
  ArrB <: PolygonLikePairArr[B1V, B1, ArrB1, B2, B]] extends SeqLikePairArrBuilder[B1V, B1, ArrB1, B2, B, ArrB]
{ /** Builder for the first element of the pair of type B1, in this case a [[PolygonLike]]. The return type has been narrowed as it is needed for the
   * polygonMapPair method on [[PolygonLikePairArr]]. */
  override def b1Builder: PolygonLikeMapBuilder[B1V, B1]
}

trait PolygonLikeDblNPair[A1V <: DblNElem, A1 <: PolygonLikeDblN[A1V], A2] extends PolygonLikePair[A1V, A1, A2] with SeqLikeDblNPairElem[A1V, A1, A2]
{ def a1ArrayDbl: Array[Double]
}

trait PolygonLikeDblNPairArr[A1V <: DblNElem, A1 <: PolygonLikeDblN[A1V], ArrA1 <: Arr[A1], A2, A <: PolygonLikeDblNPair[A1V, A1, A2]] extends
  PolygonLikePairArr[A1V, A1, ArrA1, A2, A] with SeqLikeDblNPairArr[A1V, A1, ArrA1, A2, A]
{ type ThisT <: PolygonLikeDblNPairArr[A1V, A1, ArrA1, A2, A]
}

trait PolygonLikeDblNPairArrBuilder[B1V <: DblNElem, B1 <: PolygonLikeDblN[B1V], ArrB1 <: Arr[B1], A2, B <: PolygonLikeDblNPair[B1V, B1, A2],
  ArrB <: PolygonLikeDblNPairArr[B1V, B1, ArrB1, A2, B]] extends PolygonLikePairArrBuilder[B1V, B1, ArrB1, A2, B, ArrB] with
  SeqLikeDblNPairArrBuilder[B1V, B1, ArrB1, A2, B, ArrB]

trait PolygonLikeDbl2Pair[A1V <: Dbl2Elem, A1 <: PolygonLikeDbl2[A1V], A2] extends PolygonLikeDblNPair[A1V, A1, A2] //with SeqSpecDbl2Pair[A1V, A1, A2]

trait PolygonLikeDbl2PairArr[A1V <: Dbl2Elem, A1 <: PolygonLikeDbl2[A1V], ArrA1 <: Arr[A1], A2, A <: PolygonLikeDbl2Pair[A1V, A1, A2]] extends
  PolygonLikeDblNPairArr[A1V, A1, ArrA1, A2, A]// with SeqSpecDbl2PairArr[A1V, A1, ArrA1, A2, A]

trait PolygonLikeIntNPair[A1V <: ElemIntN, A1 <: PolygonLikeIntN[A1V], A2] extends PolygonLikePair[A1V, A1, A2] with SeqLikeIntNPairElem[A1V, A1, A2]
{ def a1ArrayInt: Array[Int]
}

trait PolygonLikeIntNPairArr[A1V <: ElemIntN, A1 <: PolygonLikeIntN[A1V], ArrA1 <: Arr[A1], A2, A <: PolygonLikeIntNPair[A1V, A1, A2]] extends
  PolygonLikePairArr[A1V, A1, ArrA1, A2, A] with SeqLikeIntNPairArr[A1V, A1, ArrA1, A2, A]
{ type ThisT <: PolygonLikeIntNPairArr[A1V, A1, ArrA1, A2, A]
}

trait PolygonIntsLikePairArrBuilder[B1V <: ElemIntN, B1 <: PolygonLikeIntN[B1V], ArrB1 <: Arr[B1], A2, B <: PolygonLikeIntNPair[B1V, B1, A2],
  ArrB <: PolygonLikeIntNPairArr[B1V, B1, ArrB1, A2, B]] extends PolygonLikePairArrBuilder[B1V, B1, ArrB1, A2, B, ArrB]
{
  // override def newArr(newPolygonArr: Arr[PB], a2Arr: Arr[A2]): ArrB = ???// fromArrayArrayInt(newPolygonArr.arrayArrayInt, a2Arr)
  def fromArrayArrayInt(arrayArrayInt: Array[Array[Int]], a2Arr: RArr[A2]): ArrB
}