/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid

/** A tile step, move or addition, or no move. */
trait TStep
{/** Row coordinate delta. */
  def r: Int

  /** Column coordinate delta */
  def c: Int
}