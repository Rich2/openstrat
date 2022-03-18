/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation.unchecked.uncheckedVariance

/** Base trait for all immutable classes that use a backing Array for efficient storage. This includes immutable sequences [[SeqImut]], but also
 *  polygons and line paths that are specified by data sequences. */
trait DataImut[+A] extends Any with DataGen[A @uncheckedVariance]
{ type ThisT <: DataImut[A]

  /** This method should rarely be needed to be used by end users, but returns a new uninitialised [[DataImut]] of the this [[SeqImut]]'s final type. */
  def unsafeSameSize(length: Int): ThisT
}