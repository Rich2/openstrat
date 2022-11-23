/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import collection.mutable.ArrayBuffer

/** A class that can be constructed from a fixed number of [[Long]]s. It can be stored as an Array[Long] of primitive values. */
trait LongNElem extends Any with ValueNElem

trait LongNSeqLike[A <: LongNElem] extends Any with ValueNSeqLike[A] //with ArrayDblBacked
{ type ThisT <: LongNSeqLike[A]
  def unsafeArray: Array[Long]

  def fromArray(array: Array[Long]): ThisT

  /** Utility method to append element on to an [[ArrayBuffer]][Long]. End users should rarely need to use this method. */
  def longBufferAppend(buffer: ArrayBuffer[Long], elem: A): Unit

  def unsafeSameSize(length: Int): ThisT = fromArray(new Array[Long](length * elemProdSize))
  @inline final def unsafeLength: Int = unsafeArray.length
}

trait LongNSeqSpec[A <: LongNElem] extends Any with LongNSeqLike[A] with ValueNSeqSpec[A]

/** Base trait for Array[Long] based collections of Products of Longs. */
trait LongNArr[A <: LongNElem] extends Any with LongNSeqLike[A] with ValueNArr[A]
{
  final override def tail: ThisT =
  { val newArray = new Array[Long](unsafeLength - elemProdSize)
    iUntilForeach(unsafeLength - elemProdSize) { i => newArray(i) = unsafeArray(i + elemProdSize) }
    fromArray(newArray)
  }
}

/** Specialised flat ArrayBuffer[Double] based collection class. */
trait LongNBuff[A <: LongNElem] extends Any with ValueNBuff[A]
{ def unsafeBuffer: ArrayBuffer[Long]
  def toArray: Array[Long] = unsafeBuffer.toArray[Long]
//  def unBuff: M
  def grow(newElem: A): Unit
//  def addAll(newElems: M): Unit = { buffer.addAll(newElems.array); () }
}

/** Trait for creating the ArrTBuilder and ArrTFlatBuilder type class instances for [[LongNArr]] final classes. Instances for the [[ArrMapBuilder]] type
 *  class, for classes / traits you control, should go in the companion object of B. Instances for [[ArrFlatBuilder] should go in the companion
 *  object the ArrT final class. The first type parameter is called B, because to corresponds to the B in ```map(f: A => B): ArrB``` function. */
trait LongNSeqDefPersist[B <: LongNElem, ArrB <: LongNArr[B]] extends ValueNSeqLikePersist[B, ArrB]
{ type VT = Long
  override def fromBuffer(buf: ArrayBuffer[Long]): ArrB = fromArray(buf.toArray)
  override def newBuffer: ArrayBuffer[Long] = BufferLong(0)
}

/** Helper trait for Companion objects of [[LongNArr]] classes. */
trait LongNSeqDefCompanion[A <: LongNElem, ArrA <: LongNSeqSpec[A]]// extends SeqLikeCompanion[A, ArrA]
{ def fromBuffer(buff: ArrayBuffer[Long]): ArrA = fromArray(buff.toArray[Long])
  def fromArray(array: Array[Long]): ArrA

  /** returns a [[SeqLike]] collection class of type AA, whose backing Array(s) is / are uninitialised. */
  def uninitialised(length: Int): ArrA
}