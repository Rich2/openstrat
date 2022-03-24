/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation.unchecked.uncheckedVariance

/** Base trait for all immutable classes that use a backing Array for efficient storage. This includes immutable sequences [[SeqImut]], but also
 *  polygons and line paths that are specified by data sequences. */
trait ImutSeqDef[+A] extends Any with SeqDefGen[A @uncheckedVariance]
{ type ThisT <: ImutSeqDef[A]

  /** This method should rarely be needed to be used by end users, but returns a new uninitialised [[ImutSeqDef]] of the this [[SeqImut]]'s final type. */
  def unsafeSameSize(length: Int): ThisT
}