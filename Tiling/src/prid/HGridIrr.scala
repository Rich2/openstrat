/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package prid

case class HGridIrr(unsafeArray: Array[Int]) extends HGrid
{
  override def numOfRow2s: Int = ???

  override def numOfRow0s: Int = ???

  /** The total number of Tiles in the tile Grid. */
  override def numOfTiles: Int = ???

  def rowForeachTile(r: Int)(f: HCen => Unit): Unit = ???
}