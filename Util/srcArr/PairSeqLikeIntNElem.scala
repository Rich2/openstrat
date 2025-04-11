/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import collection.mutable.ArrayBuffer

/** A [[PairElem]] whose first component is a [[SeqLikeIntN]]. */
trait PairSeqLikeIntNElem[A1E <: IntNElem, A1 <: SeqLikeIntNImut[A1E], A2] extends PairSeqLikeElem[A1E, A1, A2] with ArrayIntBackedPair[A1, A2]

/** An [[Arr]] of [[PairElem]]s where the first component of each pair is a [[SeqLikeIntN]]. */
trait ArrPairSeqLikeIntN[A1E <: IntNElem, A1 <: SeqLikeIntNImut[A1E], ArrA1 <: Arr[A1], A2, A <: PairSeqLikeIntNElem[A1E, A1, A2]] extends
  ArrPairSeqLike[A1E, A1, ArrA1, A2, A], ArrayIntBackedPairArr[A1, ArrA1, A2, A]
{ type ThisT <: ArrPairSeqLikeIntN[A1E, A1, ArrA1, A2, A]
}

/** A [[Buff]] of [[PairElem]]s where the first component of each pair is a [[SeqLikeIntN]] */
trait BuffPairSeqLikeIntN[B1E <: IntNElem, B1 <: SeqLikeIntNImut[B1E], B2, B <: PairSeqLikeIntNElem[B1E, B1, B2]] extends BuffPairSeqLike[B1E, B1, B2, B]
{ def b1Buffer: ArrayBuffer[Array[Int]]
  final override def grow(newElem: B): Unit = { b1Buffer.append(newElem.a1ArrayInt); b2Buffer.append(newElem.a2) }

  final def growArr(newPairArr: ArrPairSeqLikeIntN[B1E, B1, ?, B2, B]): Unit = { newPairArr.a1ArrayArrayInts.foreach(b1Buffer.append(_))
    newPairArr.a2Array.foreach(b2Buffer.append(_)) }

  final override def pairGrow(b1: B1, b2: B2): Unit = { b1Buffer.append(b1.arrayUnsafe); b2Buffer.append(b2) }
}

/** [[BuilderMap]] for [[Arr]]s of [[PairElem]]s, where the first component of each pair is a [[SeqLikeIntN]]. */
trait BuilderMapArrPairSeqLikeIntN[B1E <: IntNElem, B1 <: SeqLikeIntNImut[B1E], ArrB1 <: Arr[B1], B2, B <: PairSeqLikeIntNElem[B1E, B1, B2],
  ArrB <: ArrPairFinalA1[B1, ArrB1, B2, B]] extends BuilderArrMapPairSeqLike[B1E, B1, ArrB1, B2, B, ArrB]
{ type BuffT <: BuffPairSeqLikeIntN[B1E, B1, B2, B]
  type B1BuffT <: ArrayIntBuff[B1]

  /** Construct the final target [[Arr]] type from an Array of Arrays of [[Int]]s and an Array of B2. */
  def fromArrays(arrayArrayInt: Array[Array[Int]], a2Array: Array[B2]): ArrB

  final override def b1BuffGrow(buff: B1BuffT, newElem: B1): Unit = buff.unsafeBuffer.append(newElem.arrayUnsafe)
  final override def arrFromBuffs(b1Buff: B1BuffT, b2Buffer: ArrayBuffer[B2]): ArrB = fromArrays(b1Buff.arrayArrayInt, b2Buffer.toArray)
  final override def buffGrow(buff: BuffT, newElem: B): Unit = { buff.b1Buffer.append(newElem.a1ArrayInt); buff.b2Buffer.append(newElem.a2) }
}