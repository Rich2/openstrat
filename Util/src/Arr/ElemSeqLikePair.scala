/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import collection.mutable.ArrayBuffer

/** An element that pairs a [[SeqSpec]] with a second value. */
trait ElemSeqLikePair[A1E, A1 <: SeqLike[A1E], A2] extends ElemPair[A1, A2] with SpecialT
{ def a1: A1
  def a2: A2
}

/** A sequence of [[ElemSeqLikePair]]s stored in 2 [[Array]]s for efficiency. */
trait SeqSpecPairArr[A1E, A1 <: SeqLike[A1E], A1Arr <: Arr[A1], A2, A <: ElemSeqLikePair[A1E, A1, A2]] extends PairArr[A1, A1Arr, A2, A]

/** A buffer of [[ElemSeqLikePair]]s stored in 2 [[ArrayBuffer]]s for efficiency. */
trait SeqLikePairBuff[A1E, A1 <: SeqLike[A1E], A2, A <: ElemSeqLikePair[A1E, A1, A2]] extends PairBuff[A1, A2, A]
{ def a2Buffer: ArrayBuffer[A2]
  override def length: Int = a2Buffer.length
}

trait SeqLikePairArrBuilder[B1E, B1 <: SeqLike[B1E], ArrB1 <: Arr[B1], B2, B <: ElemSeqLikePair[B1E, B1, B2], ArrB <: Arr[B]] extends
  PairArrBuilder[B1, ArrB1, B2, B, ArrB]
{ /** Builder for the first element of the pair of type B1. This method will need to be overwritten to a narrow type. */
  def b1Builder: SeqLikeMapBuilder[B1E, B1]
}

trait SeqLikeDblNPair[A1E <: ElemDblN, A1 <: DblNSeqLike[A1E], A2] extends ElemSeqLikePair[A1E, A1, A2]
{ /** The backing Array of Doubles for the A1 [[DblNSeqLike]]. */
  def a1ArrayDbl: Array[Double]
}

trait SeqLikeDblNPairArr[A1E <: ElemDblN, A1 <: DblNSeqLike[A1E], A1Arr <: Arr[A1], A2, A <: ElemSeqLikePair[A1E, A1, A2]] extends
  SeqSpecPairArr[A1E, A1, A1Arr, A2, A]
{ type ThisT <: SeqLikeDblNPairArr[A1E, A1, A1Arr, A2, A]
  def a1FromArrayDbl(array: Array[Double]): A1
  def a1ArrayArrayDbl: Array[Array[Double]]
  def fromArrays(array1: Array[Array[Double]], array2: Array[A2]): ThisT
  override def a1Index(index: Int): A1 = a1FromArrayDbl(a1ArrayArrayDbl(index))
}

trait SeqLikeDblNPairBuff[B1E <: ElemDblN, B1 <: DblNSeqLike[B1E], B2, B <: SeqLikeDblNPair[B1E, B1, B2]] extends SeqLikePairBuff[B1E, B1, B2, B]
{ def a1Buffer: ArrayBuffer[Array[Double]]
  final override def grow(newElem: B): Unit = { a1Buffer.append(newElem.a1ArrayDbl); a2Buffer.append(newElem.a2) }
  final override def grows(newElems: Arr[B]): Unit = newElems.foreach(grow)
}

trait SeqLikeDblNPairArrBuilder[B1E <: ElemDblN, B1 <: DblNSeqLike[B1E], ArrB1 <: Arr[B1], B2, B <: SeqLikeDblNPair[B1E, B1, B2], ArrB <: Arr[B]] extends
  SeqLikePairArrBuilder[B1E, B1, ArrB1, B2, B, ArrB]
{ type BuffT <: SeqLikeDblNPairBuff[B1E, B1, B2, B]
  type B1BuffT <: ArrayDblBuff[B1]

  /** Construct the final target [[Arr]] type from an Array of Arrays of Doubles and an Array of B2. */
  def fromArrays(arrayArrayDbl: Array[Array[Double]], a2Array: Array[B2]): ArrB

  final override def b1BuffGrow(buff: B1BuffT, newElem: B1): Unit = buff.unsafeBuffer.append(newElem.unsafeArray)
  final override def fromBuffs(a1Buff: B1BuffT, b2s: ArrayBuffer[B2]): ArrB = fromArrays(a1Buff.arrayArrayDbl, b2s.toArray)
  final override def buffGrow(buff: BuffT, value: B): Unit = { buff.a1Buffer.append(value.a1ArrayDbl); buff.a2Buffer.append(value.a2) }
}

trait SeqLikeIntNPair[A1E <: ElemIntN, A1 <: IntNSeqLike[A1E], A2] extends ElemSeqLikePair[A1E, A1, A2]
{/** The backing Array of Ints for the A1 [[IntNSeqLike]]. */
  def a1ArrayInt: Array[Int]
}

trait SeqLikeIntNPairArr[A1E <: ElemIntN, A1 <: IntNSeqSpec[A1E], A1Arr <: Arr[A1], A2, A <: ElemSeqLikePair[A1E, A1, A2]] extends
  SeqSpecPairArr[A1E, A1, A1Arr, A2, A]
{ type ThisT <: SeqLikeIntNPairArr[A1E, A1, A1Arr, A2, A]
  def a1FromArrayInt(array: Array[Int]): A1
  def arrayArrayInt: Array[Array[Int]]
  def fromArrays(array1: Array[Array[Int]], array2: Array[A2]): ThisT
  override def a1Index(index: Int): A1 = a1FromArrayInt(arrayArrayInt(index))
}

trait SeqLikeIntNPairBuff[B1E <: ElemIntN, B1 <: IntNSeqLike[B1E], B2, B <: SeqLikeIntNPair[B1E, B1, B2]] extends SeqLikePairBuff[B1E, B1, B2, B]
{ def a1Buffer: ArrayBuffer[Array[Int]]
  final override def grow(newElem: B): Unit = { a1Buffer.append(newElem.a1ArrayInt); a2Buffer.append(newElem.a2) }
  final override def grows(newElems: Arr[B]): Unit = newElems.foreach(grow)
}

trait SeqLikeIntNPairArrBuilder[B1E <: ElemIntN, B1 <: IntNSeqLike[B1E], ArrB1 <: Arr[B1], B2, B <: SeqLikeIntNPair[B1E, B1, B2], ArrB <: Arr[B]] extends
  SeqLikePairArrBuilder[B1E, B1, ArrB1, B2, B, ArrB]
{ type BuffT <: SeqLikeIntNPairBuff[B1E, B1, B2, B]
  type B1BuffT <: ArrayIntBuff[B1]

  /** Construct the final target [[Arr]] type from an Array of Arrays of [[Int]]s and an Array of B2. */
  def fromArrays(arrayArrayInt: Array[Array[Int]], a2Array: Array[B2]): ArrB

  final override def b1BuffGrow(buff: B1BuffT, newElem: B1): Unit = buff.unsafeBuffer.append(newElem.unsafeArray)
  final override def fromBuffs(a1Buff: B1BuffT, b2s: ArrayBuffer[B2]): ArrB = fromArrays(a1Buff.arrayArrayInt, b2s.toArray)
  final override def buffGrow(buff: BuffT, value: B): Unit = { buff.a1Buffer.append(value.a1ArrayInt); buff.a2Buffer.append(value.a2) }
}