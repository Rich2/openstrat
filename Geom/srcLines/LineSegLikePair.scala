/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A [[LineSegLike]] object paired with an object of type A2.]] */
trait LineSegLikePair[VT, A1 <: LineSegLike[VT], A2] extends PairFinalA1Elem[A1, A2]

/** An [[Arr]] of [[LineSegLikePair]]s stored efficiently allowing maping between different [[LineSegLike]] types while keeping the A2 values unchanged. */
trait LineSegLikePairArr[VT, A1 <: LineSegLike[VT], ArrA1 <: Arr[A1], A2, A <: LineSegLikePair[VT, A1, A2]] extends ArrPairFinalA1[A1, ArrA1, A2, A]
{ /** Maps this to a new [LineSegLikePairArr]] by mapping [[LineSegLike]]s to new [[LineSegLike]]s of type B1 leaving the second parts of the pairs
   * unchanged. */
  def lineSegMapToPair[B1V <: ValueNElem, B1 <: LineSegLike[B1V], ArrB1 <: Arr[B1], B <: LineSegLikePair[B1V, B1, A2],
    ArrB <: LineSegLikePairArr[B1V, B1, ArrB1, A2, B]](f: VT => B1V)(implicit build: LineSegLikePairArrBuilder[B1V, B1, ArrB1, A2, B, ArrB]): ArrB =
  { val lineSegs = a1Arr.map(p => p.map[B1V, B1](f)(build.b1Builder))(build.b1ArrBuilder)
    build.arrFromArrAndArray(lineSegs, a2Array)
  }
}

trait LineSegLikePairBuff[VT, B1 <: LineSegLike[VT], B2, B <: LineSegLikePair[VT, B1, B2]] extends BuffPair[B1, B2, B]

trait LineSegLikePairArrBuilder[B1V, B1 <: LineSegLike[B1V], ArrB1 <: Arr[B1], B2, B <: LineSegLikePair[B1V, B1, B2],
  ArrB <: LineSegLikePairArr[B1V, B1, ArrB1, B2, B]] extends BuilderMapArrPair[B1, ArrB1, B2, B, ArrB]
{ type BuffT <: LineSegLikePairBuff[B1V, B1, B2, B]

  /** Builder for the first element of the pair of type B1, in this case a [[LineSegLike]]. The return type has been narrowed as it is needed for the
   * polygonMapPair method on [[LineSegLikePairArr]]. */
  def b1Builder: LineSegLikeBuilderMap[B1V, B1]
}

trait LineSegLikeIntNPair[VT <: IntNElem, A1 <: LineSegLikeIntN[VT], A2] extends LineSegLikePair[VT, A1, A2] with PairIntNElem[A1, A2]

trait LineSegLikeIntNPairArr[VT <: IntNElem, A1 <: LineSegLikeIntN[VT], ArrA1 <: ArrIntN[A1], A2, A <: LineSegLikeIntNPair[VT, A1, A2]] extends
  LineSegLikePairArr[VT, A1, ArrA1, A2, A] with ArrPairIntN[A1, ArrA1, A2, A]
{ type ThisT <: LineSegLikeIntNPairArr[VT, A1, ArrA1, A2, A]
}

trait LineSegLikeInt4Pair[VT <: Int2Elem, A1 <: LineSegLikeInt4[VT], A2] extends LineSegLikeIntNPair[VT, A1, A2] with PairInt4Elem[A1, A2]

trait LineSegLikeInt4PairArr[VT <: Int2Elem, A1 <: LineSegLikeInt4[VT], ArrA1 <: ArrInt4[A1], A2, A <: LineSegLikeInt4Pair[VT, A1, A2]] extends
LineSegLikeIntNPairArr[VT, A1, ArrA1, A2, A] with ArrPairInt4[A1, ArrA1, A2, A]

trait LineSegLikeDblNPair[VT <: DblNElem, A1 <: LineSegLikeDblN[VT], A2] extends LineSegLikePair[VT, A1, A2] with PairDblNElem[A1, A2]

trait LineSegLikeDblNPairArr[VT <: DblNElem, A1 <: LineSegLikeDblN[VT], ArrA1 <: ArrDblN[A1], A2, A <: LineSegLikeDblNPair[VT, A1, A2]] extends
  LineSegLikePairArr[VT, A1, ArrA1, A2, A] with ArrPairDblN[A1, ArrA1, A2, A]
{ type ThisT <: LineSegLikeDblNPairArr[VT, A1, ArrA1, A2, A]
}

trait LineSegLikeDbl4Pair[VT <: Dbl2Elem, A1 <: LineSegLikeDbl4[VT], A2] extends LineSegLikeDblNPair[VT, A1, A2] with PairDbl4Elem[A1, A2]

trait LineSegLikeDbl4PairArr[VT <: Dbl2Elem, A1 <: LineSegLikeDbl4[VT], ArrA1 <: ArrDbl4[A1], A2, A <: LineSegLikeDbl4Pair[VT, A1, A2]] extends
  LineSegLikeDblNPairArr[VT, A1, ArrA1, A2, A] with ArrPairDbl4[A1, ArrA1, A2, A]