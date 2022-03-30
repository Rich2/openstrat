/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import collection.mutable.ArrayBuffer

/** A class that can be constructed from a fixed number of [[Long]]s. It can be stored as an Array[Long] of primitive values. */
trait ElemLongN extends Any with ElemValueN

/** Base trait for Array[Long] based collections of Products of Longs. */
trait ArrLongNs[A <: ElemLongN] extends Any with ValueNArr[A]
{ def unsafeArray: Array[Long]
  def arrLen: Int = unsafeArray.length

  /** The number of Longs, that specify / construct an element of this immutable flat Array based collection class. */
  def elemProdSize: Int
}

/** Specialised flat ArrayBuffer[Double] based collection class. */
trait BuffLongNs[A <: ElemLongN] extends Any with ValueNBuff[A]
{ def unsafeBuffer: ArrayBuffer[Long]
  def toArray: Array[Long] = unsafeBuffer.toArray[Long]
//  def unBuff: M
  def grow(newElem: A): Unit
//  def addAll(newElems: M): Unit = { buffer.addAll(newElems.array); () }
}

/** Trait for creating the ArrTBuilder and ArrTFlatBuilder type class instances for [[ArrLongNs]] final classes. Instances for the [[ArrBuilder]] type
 *  class, for classes / traits you control, should go in the companion object of B. Instances for [[ArrFlatBuilder] should go in the companion
 *  object the ArrT final class. The first type parameter is called B, because to corresponds to the B in ```map(f: A => B): ArrB``` function. */
trait LongNsArrBuilders[B <: ElemLongN, ArrB <: ArrLongNs[B]] extends DataValueNsPersist[B, ArrB]
{ type VT = Long
  override def fromBuffer(buf: Buff[Long]): ArrB = fromArray(buf.toArray)
  override def newBuffer: Buff[Long] = Buff[Long](0)
}

/** Helper trait for Companion objects of [[ArrLongNs]] classes. */
trait ArrLongNsCompanion[A <: ElemLongN, ArrA <: ArrLongNs[A]] extends DataValueNsCompanion[A, ArrA]
{ def fromBuffer(buff: Buff[Long]): ArrA = fromArray(buff.toArray[Long])
  //val factory: Int => M
  def fromArray(array: Array[Long]): ArrA
}