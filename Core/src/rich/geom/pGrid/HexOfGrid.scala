/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */package rich
package geom
package pGrid

trait HexOfGrid[TileT <: Tile, GridT <: HexGrid[TileT]] extends TileOfGrid[TileT, GridT]
{
   def sideURLine: Line2 = CoodLine(cood.addXY(0, 1), cood.addXY(2, 1)).toLine2(cood => coodToDispVec2(cood))
   def sideRightLine: Line2 = CoodLine(cood.addXY(2, 1), cood.addXY(2, - 1)).toLine2(cood => coodToDispVec2(cood))
   def sideDRLine: Line2 = CoodLine(cood.addXY(2, -1), cood.addXY(0, -1)).toLine2(cood => coodToDispVec2(cood))
   override def ownSideLines: List[Line2] = List(sideURLine, sideRightLine, sideDRLine)
}

object HexOfGrid
{
   implicit def apply[TileT <: Tile, GridT <: HexGrid[TileT]](tile: TileT, grid: GridT, gGui: TileGridGui[TileT, GridT]):
      HexOfGrid[TileT, GridT] = new RegHexOfGrid[TileT, GridT](tile,grid, gGui)
     {
      
     }
}

trait SquareOfGrid[TileT <: Tile, GridT <: SquareGrid[TileT]] extends TileOfGrid[TileT, GridT]
{
   def sideUpLine: Line2 = CoodLine(cood.addXY(-1, 1), cood.addXY(1, 1)).toLine2(cood => coodToDispVec2(cood))
   def sideRightLine: Line2 = CoodLine(cood.addXY(1, 1), cood.addXY(1, - 1)).toLine2(cood => coodToDispVec2(cood))
   override def ownSideLines: List[Line2] = List(sideUpLine, sideRightLine)
}

case class RegHexOfGrid[TileT <: Tile, GridT <: HexGrid[TileT]](tile: TileT, grid: GridT, gGui: TileGridGui[TileT, GridT]) extends
   HexOfGrid[TileT, GridT] with TileOfGridReg[TileT, GridT]

object RegHexOfGrid
{
   implicit def implicitBuilder[TileT <: Tile, GridT <: HexGrid[TileT]](tile: TileT, grid: GridT, gGui: TileGridGui[TileT, GridT]) =
      apply(tile, grid, gGui)
}

case class RegSquareOfGrid[TileT <: Tile, GridT <: SquareGrid[TileT]](tile: TileT, grid: GridT, gGui: TileGridGui[TileT, GridT]) extends
   SquareOfGrid[TileT, GridT] with TileOfGridReg[TileT, GridT]

object RegSquareOfGrid
{
   implicit def implicitBuilder[TileT <: Tile, GridT <: SquareGrid[TileT]](tile: TileT, grid: GridT, gGui: TileGridGui[TileT, GridT]) =
      apply(tile, grid, gGui)
}