/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package psq

class SqGridStack extends SqGridMulti
{
 // override def gridMans: Arr[SqGridMan] = ???

  /** The grids of this tile grid system. */
  override def grids: Arr[SqGrid] = ???

  override def arrIndex(sc: SqCen): Int = ???

  /** The top most point in the grid where the value of y is maximum. */
  override def top: Double = ???

  /** The bottom most point in the grid where the value of y is minimum. */
  override def bottom: Double = ???

  /** The left most point in the grid where x is minimum. */
  override def left: Double = ???

  /** The right most point in the grid where the value of x is maximum. */
  override def right: Double = ???
}
