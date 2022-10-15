/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation._, unchecked.uncheckedVariance, reflect.ClassTag, collection.mutable.ArrayBuffer

/** An element that pairs a [[SeqSpec]] with a second value. */
trait ElemSeqSpecPair[A1E, A1 <: SeqSpec[A1E], A2] extends SpecialT

/** A sequence of [[ElemSeqSpecPair]]s stored in 2 [[Array]]s for efficiency. */
trait SeqSpecPairArr[A1E, A1 <: SeqSpec[A1E], A1Arr <: Sequ[A1], A2, A <: ElemSeqSpecPair[A1E, A1, A2]] extends SeqImut[A]
{ //def a1Arr: SeqImut[A1] = ???
  def a2Array: Array[A2]
  override def length: Int = a2Array.length

  /** Builder for the first element of the pair of type B1. This method will need to be overwritten to a narrow type. */
  def a1Builder: SeqLikeImutBuilder[A1E, A1] = ???
  /** Maps this to a new [PolygonLikePairArr]] by mapping a1s to new [[SeqSpec]]s of type B1 leaving the second parts of the pairs
   * unchanged. */

  /*def a1MapToPair[B1V <: ElemValueN, B1 <: SeqDef[B1V], ArrB1 <: SeqImut[B1], B <: ElemSeqDefPair[B1V, B1, A2],
    ArrB <: SeqDefPairArr[B1V, B1, A2, B]](f: A1E => B1V)(implicit build: SeqDefPairArrBuilder[B1V, B1, ArrB1, A2, B, ArrB]): ArrB = {
    val seqDefs = a1Arr.map(p => p.map[B1V, B1](f)(build.b1Builder))(build.b1ArrBuilder)
    build.pairArrBuilder(seqDefs, a2Array)
  }*/

  def filter(): SeqSpecPairArr[A1E, A1, A1Arr, A2, A] ={
    val new1s = a1Builder.newBuff()
    ???
  }
}

/** A buffer of [[ElemSeqSpecPair]]s stored in 2 [[ArrayBuffer]]s for efficiency. */
trait SeqSpecPairBuff[A1E, A1 <: SeqSpec[A1E], A2, A <: ElemSeqSpecPair[A1E, A1, A2]] extends Sequ[A]
{ def a2Buff: ArrayBuffer[A2]
  override def length: Int = a2Buff.length

  /** This method should rarely be needed to be used by end users, but returns a new uninitialised [[SeqSpec]] of the this [[SeqImut]]'s final type. */
  override def unsafeSameSize(length: Int): ThisT = ???
}

trait SeqSpecPairArrBuilder[B1E, B1 <: SeqSpec[B1E], ArrB1 <: SeqImut[B1], B2, B <: ElemSeqSpecPair[B1E, B1, B2], ArrB <: SeqImut[B]] extends ArrBuilder[B, ArrB]
{ /** Builder for the first element of the pair of type B1. This method will need to be overwritten to a narrow type. */
  def b1Builder: SeqLikeImutBuilder[B1E, B1]

  /** Builder for an Arr of the first element of the pair. */
  def b1ArrBuilder: ArrBuilder[B1, ArrB1]

  /** Builder for the sequence of pairs, takes the results of the other two builder methods to produce the end product. Pun intended */
  def pairArrBuilder(polygonArr: ArrB1, a2s: Array[B2]): ArrB
}