/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation._, unchecked.uncheckedVariance, reflect.ClassTag, collection.mutable.ArrayBuffer

/** An element that pairs a [[SeqSpec]] with a second value. */
trait ElemSeqSpecPair[A1E, A1 <: SeqSpec[A1E], A2] extends SpecialT
{ def a1: A1
  def a2: A2
}

/** A sequence of [[ElemSeqSpecPair]]s stored in 2 [[Array]]s for efficiency. */
trait SeqSpecPairArr[A1E, A1 <: SeqSpec[A1E], A1Arr <: Arr[A1], A2, A <: ElemSeqSpecPair[A1E, A1, A2]] extends Arr[A]
{ //def a1Arr: A1Arr = ???
  def a2Array: Array[A2]
  override def length: Int = a2Array.length

  /** Needs rewriting. */
  def pairMap[B, ArrB <: Arr[B]](f: (A1, A2) => B)(implicit builder: ArrBuilder[B, ArrB]): ArrB = map(p => f(p.a1, p.a2))
 //def newA1Buff: Buff

  /** Builder for the first element of the pair of type B1. This method will need to be overwritten to a narrow type. */
  //def a1ArrBuilder: ArrBuilder[A1, A1Arr] = ???
  /** Maps this to a new [PolygonLikePairArr]] by mapping a1s to new [[SeqSpec]]s of type B1 leaving the second parts of the pairs
   * unchanged. */

  /*def a1MapToPair[B1V <: ElemValueN, B1 <: SeqDef[B1V], ArrB1 <: SeqImut[B1], B <: ElemSeqDefPair[B1V, B1, A2],
    ArrB <: SeqDefPairArr[B1V, B1, A2, B]](f: A1E => B1V)(implicit build: SeqDefPairArrBuilder[B1V, B1, ArrB1, A2, B, ArrB]): ArrB = {
    val seqDefs = a1Arr.map(p => p.map[B1V, B1](f)(build.b1Builder))(build.b1ArrBuilder)
    build.pairArrBuilder(seqDefs, a2Array)
  }*/

  /*def filter(f: A1 => Boolean): SeqSpecPairArr[A1E, A1, A1Arr, A2, A] =
  { val buff1 = a1ArrBuilder.newBuff()
    val buff2 = new ArrayBuffer[A2]()
    var i = 0
    a1Arr.foreach{(a1: A1) =>
      if (f(a1)){
      //  a1ArrBuilder.buffGrow(buff1, a1)
      }
      i += 1
    }
    ???
  }*/
}

/** A buffer of [[ElemSeqSpecPair]]s stored in 2 [[ArrayBuffer]]s for efficiency. */
trait SeqSpecPairBuff[A1E, A1 <: SeqSpec[A1E], A2, A <: ElemSeqSpecPair[A1E, A1, A2]] extends Sequ[A]
{ def a2Buff: ArrayBuffer[A2]
  override def length: Int = a2Buff.length

  /** This method should rarely be needed to be used by end users, but returns a new uninitialised [[SeqSpec]] of the this [[Arr]]'s final type. */
  override def unsafeSameSize(length: Int): ThisT = ???
}

trait SeqSpecPairArrBuilder[B1E, B1 <: SeqSpec[B1E], ArrB1 <: Arr[B1], B2, B <: ElemSeqSpecPair[B1E, B1, B2], ArrB <: Arr[B]] extends ArrBuilder[B, ArrB]
{ /** Builder for the first element of the pair of type B1. This method will need to be overwritten to a narrow type. */
  def b1Builder: SeqLikeImutBuilder[B1E, B1]

  /** Builder for an Arr of the first element of the pair. */
  def b1ArrBuilder: ArrBuilder[B1, ArrB1]

  /** Builder for the sequence of pairs, takes the results of the other two builder methods to produce the end product. Pun intended */
  def pairArrBuilder(polygonArr: ArrB1, a2s: Array[B2]): ArrB
}