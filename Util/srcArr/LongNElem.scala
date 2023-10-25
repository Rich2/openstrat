/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import collection.mutable.ArrayBuffer

/** A class that can be constructed from a fixed number of [[Long]]s. It can be stored as an Array[Long] of primitive values. */
trait LongNElem extends Any with ValueNElem

trait LongNSeqLike[A <: LongNElem] extends Any with SeqLikeValueN[A] //with ArrayDblBacked
{ type ThisT <: LongNSeqLike[A]
  def unsafeArray: Array[Long]

  def fromArray(array: Array[Long]): ThisT

  /** Utility method to append element on to an [[ArrayBuffer]][Long]. End users should rarely need to use this method. */
  def longBufferAppend(buffer: ArrayBuffer[Long], elem: A): Unit

  def unsafeSameSize(length: Int): ThisT = fromArray(new Array[Long](length * elemProdSize))
  @inline final def unsafeLength: Int = unsafeArray.length
}

trait LongNSeqSpec[A <: LongNElem] extends Any with LongNSeqLike[A] with SeqSpecValueN[A]

/** Base trait for Array[Long] based collections of Products of Longs. */
trait LongNArr[A <: LongNElem] extends Any with LongNSeqLike[A] with ArrValueN[A]
{
  final override def drop(n: Int): ThisT =
  { val nn = n.max0
    val newArray = new Array[Long]((unsafeLength - elemProdSize * nn).max0)
    iUntilForeach(unsafeLength - elemProdSize * nn) { i => newArray(i) = unsafeArray(i + elemProdSize * nn) }
    fromArray(newArray)
  }
}

/** Specialised flat ArrayBuffer[Double] based collection class. */
trait LongNBuff[A <: LongNElem] extends Any with BuffValueN[A]
{ def unsafeBuffer: ArrayBuffer[Long]
  def toArray: Array[Long] = unsafeBuffer.toArray[Long]
//  def unBuff: M
  def grow(newElem: A): Unit
//  def addAll(newElems: M): Unit = { buffer.addAll(newElems.array); () }
}

/** Helper trait for Companion objects of [[LongNArr]] classes. */
trait LongNSeqDefCompanion[A <: LongNElem, ArrA <: LongNSeqSpec[A]]// extends SeqLikeCompanion[A, ArrA]
{ def fromBuffer(buff: ArrayBuffer[Long]): ArrA = fromArray(buff.toArray[Long])
  def fromArray(array: Array[Long]): ArrA

  /** returns a [[SeqLike]] collection class of type AA, whose backing Array(s) is / are uninitialised. */
  def uninitialised(length: Int): ArrA
}