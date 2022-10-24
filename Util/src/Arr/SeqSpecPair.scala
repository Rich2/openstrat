/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation._, collection.mutable.ArrayBuffer, reflect.ClassTag

/** An element that pairs a [[SeqSpec]] with a second value. */
trait ElemSeqSpecPair[A1E, A1 <: SeqSpec[A1E], A2] extends ElemPair[A1, A2] with SpecialT
{ def a1: A1
  def a2: A2
}

/** A sequence of [[ElemSeqSpecPair]]s stored in 2 [[Array]]s for efficiency. */
trait SeqSpecPairArr[A1E, A1 <: SeqSpec[A1E], A1Arr <: Arr[A1], A2, A <: ElemSeqSpecPair[A1E, A1, A2]] extends PairArr[A1, A1Arr, A2, A]

/** A buffer of [[ElemSeqSpecPair]]s stored in 2 [[ArrayBuffer]]s for efficiency. */
trait SeqSpecPairBuff[A1E, A1 <: SeqSpec[A1E], A2, A <: ElemSeqSpecPair[A1E, A1, A2]] extends PairBuff[A1, A2, A]
{ def a2Buffer: ArrayBuffer[A2]
  override def length: Int = a2Buffer.length
}

trait SeqSpecPairArrBuilder[B1E, B1 <: SeqSpec[B1E], ArrB1 <: Arr[B1], B2, B <: ElemSeqSpecPair[B1E, B1, B2], ArrB <: Arr[B]] extends
  PairArrBuilder[B1, ArrB1, B2, B, ArrB]
{ /** Builder for the first element of the pair of type B1. This method will need to be overwritten to a narrow type. */
  def b1Builder: SeqLikeMapBuilder[B1E, B1]
}

trait SeqSpecDblNPair[A1E <: ElemDblN, A1 <: DblNSeqSpec[A1E], A2] extends ElemSeqSpecPair[A1E, A1, A2]

trait SeqSpecDblNPairArr[A1E <: ElemDblN, A1 <: DblNSeqSpec[A1E], A1Arr <: Arr[A1], A2, A <: ElemSeqSpecPair[A1E, A1, A2]] extends
  SeqSpecPairArr[A1E, A1, A1Arr, A2, A]
{
  def a1FromArrayDbl(array: Array[Double]): A1
  def arrayArrayDbl: Array[Array[Double]]
  override def a1Index(index: Int): A1 = a1FromArrayDbl(arrayArrayDbl(index))
}