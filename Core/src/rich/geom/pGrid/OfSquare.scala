/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package rich
package geom
package pGrid

trait OfSquare[TileT <: Tile, GridT <: SquareGrid[TileT]] extends OfTile[TileT, GridT]
{
   def sideUpLine: Line2 = CoodLine(cood.addXY(-1, 1), cood.addXY(1, 1)).toLine2(cood => coodToDispVec2(cood))
   def sideRightLine: Line2 = CoodLine(cood.addXY(1, 1), cood.addXY(1, - 1)).toLine2(cood => coodToDispVec2(cood))
   override def ownSideLines: List[Line2] = List(sideUpLine, sideRightLine)
}

case class OfSquareReg[TileT <: Tile, GridT <: SquareGrid[TileT]](tile: TileT, grid: GridT, gGui: TileGridGui[TileT, GridT]) extends
   OfSquare[TileT, GridT] with OfTileReg[TileT, GridT]

object OfSquareReg
{
   implicit def implicitBuilder[TileT <: Tile, GridT <: SquareGrid[TileT]](tile: TileT, grid: GridT, gGui: TileGridGui[TileT, GridT]) =
      apply(tile, grid, gGui)
}