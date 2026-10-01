/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A [[LSegBase]] object paired with an object of type A2.]] */
trait LSegBasePair[VT, A1 <: LSegBase[VT], A2] extends PairFinalA1Elem[A1, A2]

/** An [[Arr]] of [[LSegBasePair]]s stored efficiently allowing mapping between different [[LSegBase]] types while keeping the A2 values unchanged. */
trait ArrLSegBasePair[VT, A1 <: LSegBase[VT], ArrA1 <: Arr[A1], A2, A <: LSegBasePair[VT, A1, A2]] extends ArrPairFinalA1[A1, ArrA1, A2, A]
{ /** Maps this to a new [LineSegLikePairArr]] by mapping [[LSegBase]]s to new [[LSegBase]]s of type B1 leaving the second parts of the pairs unchanged. */
  def lineSegMapToPair[B1V <: ValueNElem, B1 <: LSegBase[B1V], ArrB1 <: Arr[B1], B <: LSegBasePair[B1V, B1, A2],
    ArrB <: ArrLSegBasePair[B1V, B1, ArrB1, A2, B]](f: VT => B1V)(implicit build: BuilerArrLSegBasePair[B1V, B1, ArrB1, A2, B, ArrB]): ArrB =
  { val lineSegs = a1Arr.map(p => p.map[B1V, B1](f)(using build.b1Builder))(using build.b1ArrBuilder)
    build.arrFromArrAndArray(lineSegs, a2Array)
  }
}

/** classes for [[Buff]]s for pairs of class's descending from [[LineSegBase]]. */
trait BuffLSegBasePair[VT, B1 <: LSegBase[VT], B2, B <: LSegBasePair[VT, B1, B2]] extends BuffPair[B1, B2, B]

/** Builder of specialist [[Arr]]s of [[PairElem]] of classes descended from [[LSegBase]].  */
trait BuilerArrLSegBasePair[B1V, B1 <: LSegBase[B1V], ArrB1 <: Arr[B1], B2, B <: LSegBasePair[B1V, B1, B2], ArrB <: ArrLSegBasePair[B1V, B1, ArrB1, B2, B]]
  extends BuilderMapArrPair[B1, ArrB1, B2, B, ArrB]
{ type BuffT <: BuffLSegBasePair[B1V, B1, B2, B]

  /** Builder for the first element of the pair of type B1, in this case a [[LSegBase]]. The return type has been narrowed as it is needed for the
   * polygonMapPair method on [[ArrLSegBasePair]]. */
  def b1Builder: BuilderMapLSegBase[B1V, B1]
}

/** [[PairElem]]s of with first component descended from [[LSegInt]]. */
trait LSegIntNPair[VT <: IntNElem, A1 <: LSegIntN[VT], A2] extends LSegBasePair[VT, A1, A2], PairIntNElem[A1, A2]

trait ArrLSegIntNPair[VT <: IntNElem, A1 <: LSegIntN[VT], ArrA1 <: ArrIntN[A1], A2, A <: LSegIntNPair[VT, A1, A2]] extends
  ArrLSegBasePair[VT, A1, ArrA1, A2, A], ArrPairIntN[A1, ArrA1, A2, A]
{ type ThisT <: ArrLSegIntNPair[VT, A1, ArrA1, A2, A]
}

trait LSegInt4Pair[VT <: Int2Elem, A1 <: LSegInt4[VT], A2] extends LSegIntNPair[VT, A1, A2], PairInt4Elem[A1, A2]

trait ArrLSegInt4Pair[VT <: Int2Elem, A1 <: LSegInt4[VT], ArrA1 <: ArrInt4[A1], A2, A <: LSegInt4Pair[VT, A1, A2]] extends
  ArrLSegIntNPair[VT, A1, ArrA1, A2, A], ArrPairInt4[A1, ArrA1, A2, A]

trait LSegDblNPair[VT <: DblNElem, A1 <: LSegDblN[VT], A2] extends LSegBasePair[VT, A1, A2], PairDblNElem[A1, A2]

trait ArrLSegDblNPair[VT <: DblNElem, A1 <: LSegDblN[VT], ArrA1 <: ArrDblN[A1], A2, A <: LSegDblNPair[VT, A1, A2]] extends
  ArrLSegBasePair[VT, A1, ArrA1, A2, A], ArrPairDblN[A1, ArrA1, A2, A]
{ type ThisT <: ArrLSegDblNPair[VT, A1, ArrA1, A2, A]
}

trait LSegDbl4Pair[VT <: Dbl2Elem, A1 <: LSegDbl4[VT], A2] extends LSegDblNPair[VT, A1, A2], PairDbl4Elem[A1, A2]

trait ArrLSegDbl4Pair[VT <: Dbl2Elem, A1 <: LSegDbl4[VT], ArrA1 <: ArrDbl4[A1], A2, A <: LSegDbl4Pair[VT, A1, A2]] extends
  ArrLSegDblNPair[VT, A1, ArrA1, A2, A], ArrPairDbl4[A1, ArrA1, A2, A]