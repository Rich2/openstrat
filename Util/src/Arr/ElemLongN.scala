/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import collection.mutable.ArrayBuffer

/** A class that can be constructed from a fixed number of [[Long]]s. It can be stored as an Array[Long] of primitive values. */
trait ElemLongN extends Any with ElemValueN

trait LongNSeqDef[A <: ElemLongN] extends Any with ValueNSeqDef[A]
{ def unsafeArray: Array[Long]
  final def dsLen: Int = unsafeArray.length
}

/** Base trait for Array[Long] based collections of Products of Longs. */
trait LongNArr[A <: ElemLongN] extends Any with LongNSeqDef[A] with ValueNArr[A]
{
}

/** Specialised flat ArrayBuffer[Double] based collection class. */
trait LongNBuff[A <: ElemLongN] extends Any with ValueNBuff[A]
{ def unsafeBuffer: ArrayBuffer[Long]
  def toArray: Array[Long] = unsafeBuffer.toArray[Long]
//  def unBuff: M
  def grow(newElem: A): Unit
//  def addAll(newElems: M): Unit = { buffer.addAll(newElems.array); () }
}

/** Trait for creating the ArrTBuilder and ArrTFlatBuilder type class instances for [[LongNArr]] final classes. Instances for the [[ArrBuilder]] type
 *  class, for classes / traits you control, should go in the companion object of B. Instances for [[ArrFlatBuilder] should go in the companion
 *  object the ArrT final class. The first type parameter is called B, because to corresponds to the B in ```map(f: A => B): ArrB``` function. */
trait LongNSeqDefPersist[B <: ElemLongN, ArrB <: LongNArr[B]] extends ValueNSeqLikePersist[B, ArrB]
{ type VT = Long
  override def fromBuffer(buf: ArrayBuffer[Long]): ArrB = fromArray(buf.toArray)
  override def newBuffer: ArrayBuffer[Long] = BuffLong(0)
}

/** Helper trait for Companion objects of [[LongNArr]] classes. */
trait LongNSeqDefCompanion[A <: ElemLongN, ArrA <: LongNSeqDef[A]] extends ValueNSeqLikeCompanion[A, ArrA]
{ def fromBuffer(buff: ArrayBuffer[Long]): ArrA = fromArray(buff.toArray[Long])
  def fromArray(array: Array[Long]): ArrA
}