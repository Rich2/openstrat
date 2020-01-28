/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid
import geom._

trait OfHex[TileT <: Tile, SideT <: TileSide, GridT <: HexGridOld[TileT, SideT]] extends OfTile[TileT, SideT, GridT]
{
   def sideURLine: Line2 = CoodLine(cood.addXY(0, 1), cood.addXY(2, 1)).toLine2(cood => coodToDispVec2(cood))
   def sideRightLine: Line2 = CoodLine(cood.addXY(2, 1), cood.addXY(2, - 1)).toLine2(cood => coodToDispVec2(cood))
   def sideDRLine: Line2 = CoodLine(cood.addXY(2, -1), cood.addXY(0, -1)).toLine2(cood => coodToDispVec2(cood))
   override def ownSideLines: ArrOld[Line2] = ArrOld(sideURLine, sideRightLine, sideDRLine)
}

object OfHex
{
  // implicit def apply[TileT <: Tile, SideT <: TileSide, GridT <: HexGridComplex[TileT, SideT]](tile: TileT, grid: GridT, gGui: TileGridGui[TileT, SideT, GridT]):
    //  OfHex[TileT, SideT, GridT] = new OfHex[TileT, SideT, GridT](tile,grid, gGui)     
}

case class OfHexReg[TileT <: Tile, SideT <: TileSide, GridT <: HexGridRegOld[TileT, SideT]](tile: TileT, grid: GridT,
    gGui: TileGridGui[TileT, SideT, GridT]) extends OfHex[TileT, SideT, GridT] with OfTileReg[TileT, SideT, GridT]

object OfHexReg
{
   implicit def implicitBuilder[TileT <: Tile, SideT <: TileSide, GridT <: HexGridRegOld[TileT, SideT]](tile: TileT, grid: GridT,
         gGui: TileGridGui[TileT, SideT, GridT]) = apply(tile, grid, gGui)
}

