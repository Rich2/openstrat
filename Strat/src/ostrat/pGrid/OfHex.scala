/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid
import geom._

trait OfHex[TileT <: Tile, SideT <: GridElem, GridT <: HexGrid[TileT, SideT]] extends OfTile[TileT, SideT, GridT]
{
   def sideURLine: Line2 = CoodLine(cood.addXY(0, 1), cood.addXY(2, 1)).toLine2(cood => coodToDispVec2(cood))
   def sideRightLine: Line2 = CoodLine(cood.addXY(2, 1), cood.addXY(2, - 1)).toLine2(cood => coodToDispVec2(cood))
   def sideDRLine: Line2 = CoodLine(cood.addXY(2, -1), cood.addXY(0, -1)).toLine2(cood => coodToDispVec2(cood))
   override def ownSideLines: List[Line2] = List(sideURLine, sideRightLine, sideDRLine)
}

object OfHex
{
  // implicit def apply[TileT <: GridElem, SideT <: GridElem, GridT <: HexGridComplex[TileT, SideT]](tile: TileT, grid: GridT, gGui: TileGridGui[TileT, SideT, GridT]):
    //  OfHex[TileT, SideT, GridT] = new OfHex[TileT, SideT, GridT](tile,grid, gGui)     
}

case class OfHexReg[TileT <: Tile, SideT <: GridElem, GridT <: HexGridReg[TileT, SideT]](tile: TileT, grid: GridT,
    gGui: TileGridComplexGui[TileT, SideT, GridT]) extends
   OfHex[TileT, SideT, GridT] with OfTileReg[TileT, SideT, GridT]
{
   
}

object OfHexReg
{
   implicit def implicitBuilder[TileT <: Tile, SideT <: GridElem, GridT <: HexGridReg[TileT, SideT]](tile: TileT, grid: GridT,
         gGui: TileGridComplexGui[TileT, SideT, GridT]) = apply(tile, grid, gGui)
}

