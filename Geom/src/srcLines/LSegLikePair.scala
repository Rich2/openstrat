/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A [[LSegBase]] object paired with an object of type A2.]] */
trait LSegLikePair[VT, A1 <: LSegBase[VT], A2] extends PairFinalA1Elem[A1, A2]

/** An [[Arr]] of [[LSegLikePair]]s stored efficiently allowing maping between different [[LSegBase]] types while keeping the A2 values unchanged. */
trait LSegLikePairArr[VT, A1 <: LSegBase[VT], ArrA1 <: Arr[A1], A2, A <: LSegLikePair[VT, A1, A2]] extends ArrPairFinalA1[A1, ArrA1, A2, A]
{ /** Maps this to a new [LineSegLikePairArr]] by mapping [[LSegBase]]s to new [[LSegBase]]s of type B1 leaving the second parts of the pairs unchanged. */
  def lineSegMapToPair[B1V <: ValueNElem, B1 <: LSegBase[B1V], ArrB1 <: Arr[B1], B <: LSegLikePair[B1V, B1, A2],
    ArrB <: LSegLikePairArr[B1V, B1, ArrB1, A2, B]](f: VT => B1V)(implicit build: LineSegLikePairArrBuilder[B1V, B1, ArrB1, A2, B, ArrB]): ArrB =
  { val lineSegs = a1Arr.map(p => p.map[B1V, B1](f)(using build.b1Builder))(using build.b1ArrBuilder)
    build.arrFromArrAndArray(lineSegs, a2Array)
  }
}

trait LineSegLikePairBuff[VT, B1 <: LSegBase[VT], B2, B <: LSegLikePair[VT, B1, B2]] extends BuffPair[B1, B2, B]

trait LineSegLikePairArrBuilder[B1V, B1 <: LSegBase[B1V], ArrB1 <: Arr[B1], B2, B <: LSegLikePair[B1V, B1, B2],
  ArrB <: LSegLikePairArr[B1V, B1, ArrB1, B2, B]] extends BuilderMapArrPair[B1, ArrB1, B2, B, ArrB]
{ type BuffT <: LineSegLikePairBuff[B1V, B1, B2, B]

  /** Builder for the first element of the pair of type B1, in this case a [[LSegBase]]. The return type has been narrowed as it is needed for the
   * polygonMapPair method on [[LSegLikePairArr]]. */
  def b1Builder: BuilderMapLSegBase[B1V, B1]
}

trait LSegLikeIntNPair[VT <: IntNElem, A1 <: LSegIntN[VT], A2] extends LSegLikePair[VT, A1, A2] with PairIntNElem[A1, A2]

trait LSegLikeIntNPairArr[VT <: IntNElem, A1 <: LSegIntN[VT], ArrA1 <: ArrIntN[A1], A2, A <: LSegLikeIntNPair[VT, A1, A2]] extends
  LSegLikePairArr[VT, A1, ArrA1, A2, A], ArrPairIntN[A1, ArrA1, A2, A]
{ type ThisT <: LSegLikeIntNPairArr[VT, A1, ArrA1, A2, A]
}

trait LSegLikeInt4Pair[VT <: Int2Elem, A1 <: LSegInt4[VT], A2] extends LSegLikeIntNPair[VT, A1, A2] with PairInt4Elem[A1, A2]

trait LSegLikeInt4PairArr[VT <: Int2Elem, A1 <: LSegInt4[VT], ArrA1 <: ArrInt4[A1], A2, A <: LSegLikeInt4Pair[VT, A1, A2]] extends
LSegLikeIntNPairArr[VT, A1, ArrA1, A2, A], ArrPairInt4[A1, ArrA1, A2, A]

trait LSegLikeDblNPair[VT <: DblNElem, A1 <: LSegDblN[VT], A2] extends LSegLikePair[VT, A1, A2], PairDblNElem[A1, A2]

trait LSegLikeDblNPairArr[VT <: DblNElem, A1 <: LSegDblN[VT], ArrA1 <: ArrDblN[A1], A2, A <: LSegLikeDblNPair[VT, A1, A2]] extends
  LSegLikePairArr[VT, A1, ArrA1, A2, A], ArrPairDblN[A1, ArrA1, A2, A]
{ type ThisT <: LSegLikeDblNPairArr[VT, A1, ArrA1, A2, A]
}

trait LSegLikeDbl4Pair[VT <: Dbl2Elem, A1 <: LSegDbl4[VT], A2] extends LSegLikeDblNPair[VT, A1, A2] with PairDbl4Elem[A1, A2]

trait LSegLikeDbl4PairArr[VT <: Dbl2Elem, A1 <: LSegDbl4[VT], ArrA1 <: ArrDbl4[A1], A2, A <: LSegLikeDbl4Pair[VT, A1, A2]] extends
  LSegLikeDblNPairArr[VT, A1, ArrA1, A2, A], ArrPairDbl4[A1, ArrA1, A2, A]