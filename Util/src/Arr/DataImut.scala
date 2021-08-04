/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation.unchecked.uncheckedVariance

trait DataImut[+A] extends Any with ArrayLikeBacked[A @uncheckedVariance]
{ type ThisT <: DataImut[A]

  /** String specifying the type of this object. */
  def typeStr: String

  /** This method should rarely be needed to be used by end users, but returns a new uninitialised ArrT of the this [[SeqImut]]'s final type. */
  def unsafeSameSize(length: Int): ThisT
}
