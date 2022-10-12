/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation.unchecked.uncheckedVariance

/** Base trait for all immutable classes that use a backing Array for efficient storage. This includes immutable sequences [[SeqImut]], but also
 *  polygons and line paths that are specified by data sequences. */
trait SeqDef[+A] extends Any with SeqLike[A @uncheckedVariance]
{ type ThisT <: SeqDef[A]

  /** This method should rarely be needed to be used by end users, but returns a new uninitialised [[SeqDef]] of the this [[SeqImut]]'s final type. */
  def unsafeSameSize(length: Int): ThisT

  /** Performs a side effecting function on each element of the defining-sequence in reverse order. The function may return Unit. If it does return a
   *  non Unit value it is discarded. The [U] type parameter is there just to avoid warnings about discarded values and can be ignored by method
   *  users. */
  def dsReverseForeach[U](f: A => U): Unit = {
    var count = sdLength
    while (count > 0) {
      count -= 1; f(sdIndex(count))
    }
  }

  /** Last element of the defining sequence. */
  def dsLast: A = sdIndex(sdLength - 1)

  /** FoldLeft over the tail of the defining sequence. */
  def dsTailfold[B](initial: B)(f: (B, A) => B) = {
    var acc: B = initial
    dataTailForeach(a => acc = f(acc, a))
    acc
  }
}

trait RefsSeqDefImut[+A] extends Any with SeqDef[A]
{ type ThisT <: RefsSeqDefImut[A]
  def unsafeArray: Array[A] @uncheckedVariance

  def fromArray(array: Array[A] @uncheckedVariance): ThisT

  /** Copy's the backing Array[[AnyRef]] to a new Array[AnyRef]. End users should rarely have to use this method. */
  override def unsafeSameSize(length: Int): ThisT = fromArray(new Array[AnyRef](length).asInstanceOf[Array[A]])

  override final def sdLength: Int = unsafeArray.length
  override final def fElemStr: A @uncheckedVariance => String = _.toString
  override final def unsafeSetElem(i: Int, value: A @uncheckedVariance): Unit = unsafeArray(i) = value
  override final def sdIndex(index: Int): A = unsafeArray(index)
}