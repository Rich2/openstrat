/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package prid

class SqGrid(val rTileMin: Int, val rTileMax: Int, val cTileMin: Int, val cTileMax: Int) extends TGrid
{
  /** Number of rows of tiles. */
  override def numOfTileRows: Int = (rTileMax - rTileMin + 2).atLeast0 / 2

  /** The number of tiles in each tile row. */
  def tileRowLen: Int = (cTileMax - cTileMin + 2).atLeast0 / 2

  /** The total number of Tiles in the tile Grid. */
  override def numOfTiles: Int = numOfTileRows * tileRowLen

  override def xRatio: Double = 1

  override def width: Double = ???
  override def height: Double = ???
}