/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package prid

case class HGridIrr(unsafeArray: Array[Int]) extends HGrid
{
  override def rTileMin: Int = ???
  override def rTileMax: Int = ???

  /** Minimum c or column value. This is not called x because in some grids there is not a 1 to 1 ratio from column coordinate to x. */
  override def cTileMin: Int = ???

  /** Maximum c or column value. This is not called x because in some grids there is not a 1 to 1 ratio from column coordinate to x. */
  override def cTileMax: Int = ???

  override def numOfRow2s: Int = ???

  override def numOfRow0s: Int = ???

  /** The total number of Tiles in the tile Grid. */
  override def numOfTiles: Int = ???

  def rowForeachTile(r: Int)(f: HCen => Unit): Unit = ???

  override def rowIForeachTile(r: Int, count: Int)(f: (HCen, Int) => Unit): Int = ???

  override def width: Double = ???
  override def height: Double = ???

  override def rowForeachSide(r: Int)(f: HSide => Unit): Unit = ???
}