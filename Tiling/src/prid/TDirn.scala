/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid

/** A tile direction can be used for a tile step. */
trait TDirn
{/** Row coordinate delta. */
  def r: Int

  /** Column coordinate delta */
  def c: Int
}