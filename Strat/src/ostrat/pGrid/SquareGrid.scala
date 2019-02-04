/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid

abstract class SquareGrid[TileT <: GridElem, SideT <: GridElem](xTileMin: Int, xTileMax: Int, yTileMin: Int, yTileMax: Int)
   (implicit evTile: IsType[TileT], evSide: IsType[SideT]) extends  TileGrid[TileT, SideT](xTileMin, xTileMax, yTileMin, yTileMax) with
   TileGridReg[TileT, SideT]
{
  override val yRatio = 1
  override def xToInd(x: Int): Int = (x - xTileMin)
}