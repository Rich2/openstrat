/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation.unchecked.uncheckedVariance

/** Base trait for all immutable classes that use a backing Array for efficient storage. This includes immutable sequences [[SeqImut]], but also
 *  polygons and line paths that are specified by data sequences. */
trait SeqDefImut[+A] extends Any with SeqDef[A @uncheckedVariance]
{ type ThisT <: SeqDefImut[A]

  /** This method should rarely be needed to be used by end users, but returns a new uninitialised [[SeqDefImut]] of the this [[SeqImut]]'s final type. */
  def unsafeSameSize(length: Int): ThisT
}

trait RefsSeqDefImut[+A] extends Any with SeqDefImut[A]
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