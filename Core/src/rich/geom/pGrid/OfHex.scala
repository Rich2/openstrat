/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package rich
package geom
package pGrid

trait OfHex[TileT <: Tile, GridT <: HexGrid[TileT]] extends OfTile[TileT, GridT]
{
   def sideURLine: Line2 = CoodLine(cood.addXY(0, 1), cood.addXY(2, 1)).toLine2(cood => coodToDispVec2(cood))
   def sideRightLine: Line2 = CoodLine(cood.addXY(2, 1), cood.addXY(2, - 1)).toLine2(cood => coodToDispVec2(cood))
   def sideDRLine: Line2 = CoodLine(cood.addXY(2, -1), cood.addXY(0, -1)).toLine2(cood => coodToDispVec2(cood))
   override def ownSideLines: List[Line2] = List(sideURLine, sideRightLine, sideDRLine)
}

object OfHex
{
   implicit def apply[TileT <: Tile, GridT <: HexGrid[TileT]](tile: TileT, grid: GridT, gGui: TileGridGui[TileT, GridT]):
      OfHex[TileT, GridT] = new OfHexReg[TileT, GridT](tile,grid, gGui)     
}

case class OfHexReg[TileT <: Tile, GridT <: HexGrid[TileT]](tile: TileT, grid: GridT, gGui: TileGridGui[TileT, GridT]) extends
   OfHex[TileT, GridT] with OfTileReg[TileT, GridT]

object OfHexReg
{
   implicit def implicitBuilder[TileT <: Tile, GridT <: HexGrid[TileT]](tile: TileT, grid: GridT, gGui: TileGridGui[TileT, GridT]) =
      apply(tile, grid, gGui)
}

