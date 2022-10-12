/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

trait PolygonLikePair[A1V, A1 <: PolygonLike[A1V], A2] extends ElemSeqDefPair[A1V, A1, A2]
{ def polygon: A1
}

trait PolygonLikePairArr[A1V, A1 <: PolygonLike[A1V], A2, A <: PolygonLikePair[A1V, A1, A2]] extends SeqDefPairArr[A1V, A1, A2, A]
{
  def polygonArr: SeqImut[A1]

  /** Maps this to a new [PolygonLikePairArr]] by mapping [[PolygonLike]]s to new [[PolygonLike]]s of type B1 leaving the second parts of the pairs
   * unchanged. */
  def polygonMapToPair[B1V <: ElemValueN, B1 <: PolygonLike[B1V], ArrB1 <: SeqImut[B1], B <: PolygonLikePair[B1V, B1, A2],
    ArrB <: PolygonLikePairArr[B1V, B1, A2, B]](f: A1V => B1V)(implicit build: PolygonLikePairArrBuilder[B1V, B1, ArrB1, A2, B, ArrB]): ArrB =
  { val polygons = polygonArr.map(p => p.map[B1V, B1](f)(build.b1Builder))(build.b1ArrBuilder)
    build.pairArrBuilder(polygons, a2Array)
  }
}

trait PolygonLikePairArrBuilder[B1V, B1 <: PolygonLike[B1V], ArrB1 <: SeqImut[B1], B2, B <: PolygonLikePair[B1V, B1, B2],
  ArrB <: PolygonLikePairArr[B1V, B1, B2, B]] extends SeqDefPairArrBuilder[B1V, B1, ArrB1, B2, B, ArrB]
{ /** Builder for the first element of the pair of type B1, in this case a [[PolygonLike]]. The return type has been narrowed as it is needed for the
   * polygonMapPair method on [[PolygonLikePairArr]]. */
  override def b1Builder: PolygonLikeBuilder[B1V, B1]
}

trait PolygonDblsPair[A1V <: ElemDblN, A1 <: PolygonLike[A1V], A2] extends PolygonLikePair[A1V, A1, A2]
{
  def unsafeArray: Array[Double]
}

trait PolygonDblsLikePairArr[A1V <: ElemDblN, A1 <: PolygonLike[A1V], A2, A <: PolygonDblsPair[A1V, A1, A2]] extends PolygonLikePairArr[A1V, A1, A2, A]
{
  def arrayArrayDbl: Array[Array[Double]]
}

trait PolygonDblsLikePairArrBuilder[B1V <: ElemDblN, B1 <: PolygonLike[B1V], ArrB1 <: SeqImut[B1], A2, B <: PolygonDblsPair[B1V, B1, A2],
  ArrB <: PolygonDblsLikePairArr[B1V, B1, A2, B]] extends PolygonLikePairArrBuilder[B1V, B1, ArrB1, A2, B, ArrB]
{
 // override def newArr(newPolygonArr: Arr[PB], a2Arr: Arr[A2]): ArrB = ???// fromArrayArrayDbl(newPolygonArr.arrayArrayDbl, a2Arr)
  def fromArrayArrayDbl(arrayArrayDbl: Array[Array[Double]], a2Arr: Arr[A2]): ArrB
}