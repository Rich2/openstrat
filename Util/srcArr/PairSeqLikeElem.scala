/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import collection.mutable.ArrayBuffer

/** A [[PairElem]] whose first component is a [[SeqLike]]. */
trait PairSeqLikeElem[A1E, A1 <: SeqLike[A1E], A2] extends PairFinalA1Elem[A1, A2] with SpecialT
{ def a1: A1
  def a2: A2
}

/** An [[Arr]] of [[PairElem]]s where the first component of each [[PairElem]] is a [[SeqLike]]. Stored in 2 [[Array]]s for efficiency. */
trait ArrPairSeqLike[A1E, A1 <: SeqLike[A1E], A1Arr <: Arr[A1], A2, A <: PairSeqLikeElem[A1E, A1, A2]] extends ArrPairFinalA1[A1, A1Arr, A2, A]

/** A [[Buff]] of [[PairElem]]s where the first component of each [[PairElem]] is a [[SeqLike]], stored in 2 [[ArrayBuffer]]s for efficiency. */
trait BuffPairSeqLike[A1E, A1 <: SeqLike[A1E], A2, A <: PairSeqLikeElem[A1E, A1, A2]] extends BuffPair[A1, A2, A]
{ def b2Buffer: ArrayBuffer[A2]  
}

/** A [[BuilderMap]] for an [[Arr]] of [[PairSeqLike]]s. */
trait BuilderArrMapPairSeqLike[B1E, B1 <: SeqLikeImut[B1E], ArrB1 <: Arr[B1], B2, B <: PairSeqLikeElem[B1E, B1, B2], ArrB <: ArrPairFinalA1[B1, ArrB1, B2, B]]
  extends BuilderMapArrPair[B1, ArrB1, B2, B, ArrB]
{ /** Builder for the first element of the pair of type B1. This method will need to be overwritten to a narrow type. */
  def b1Builder: BuilderMapSeqLike[B1E, B1]
}