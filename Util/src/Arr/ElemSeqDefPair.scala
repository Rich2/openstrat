/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation._, unchecked.uncheckedVariance, reflect.ClassTag, collection.mutable.ArrayBuffer

/** An element that pairs a [[SeqDef]] with a second value. */
trait ElemSeqDefPair[A1 <: SeqDef[_], A2] extends SpecialT

/** A sequence of [[ElemSeqDefPair]]s stored in 2 [[Array]]s for efficiency. */
trait SeqDefPairArr[A1 <: SeqDef[_], A2, A <: ElemSeqDefPair[A1, A2]] extends SeqImut[A]
{ def a2Array: Array[A2]
  override def length: Int = a2Array.length
  override def sdLength: Int = a2Array.length
}

trait SeqDefPairBuff[A1 <: SeqDef[_], A2, A <: ElemSeqDefPair[A1, A2]] extends SeqGen[A]
{ def a2Buff: ArrayBuffer[A2]
  override def length: Int = a2Buff.length
  override def sdLength: Int = a2Buff.length
}

trait SeqDefPairArrBuilder[B1E, B1 <: SeqDefImut[B1E], ArrB1 <: SeqImut[B1], B2, B <: ElemSeqDefPair[B1, B2], ArrB <: SeqImut[B]] extends ArrBuilder[B, ArrB]
{ /** Builder for the first element of the pair of type B1. This method will need to be overwritten to a narrow type. */
  def b1Builder: SeqDefImutBuilder[B1E, B1]

  /** Builder for an Arr of the first element of the pair. */
  def b1ArrBuilder: ArrBuilder[B1, ArrB1]

  /** Builder for the sequence of pairs, takes the results of the other two builder methods to produce the end product. Pun intended */
  def pairArrBuilder(polygonArr: ArrB1, a2s: Array[B2]): ArrB
}