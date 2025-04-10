/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** Pair element where the first component is an [[ValueNElem]]. */
trait PairValueNElem[A1 <: ValueNElem, A2] extends PairFinalA1Elem[A1, A2]

trait SeqLikePairValueN[A1 <: ValueNElem, A2, A <: PairValueNElem[A1, A2]] extends SeqLike[A]

trait ArrPairValueN[A1 <: ValueNElem, A1Arr <: Arr[A1], A2, A <: PairValueNElem[A1, A2]] extends ArrPairFinalA1[A1, A1Arr, A2, A]