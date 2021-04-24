/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid
import geom._

trait OfHex[TileT <: TileAncient, SideT <: TileSideAncient, GridT <: HexGridAncient[TileT, SideT]] extends OfTile[TileT, SideT, GridT]
{
   def sideURLine: LineSeg = CoodLine(cood.addXY(0, 1), cood.addXY(2, 1)).toLine2(cood => coodToDispVec2(cood))
   def sideRightLine: LineSeg = CoodLine(cood.addXY(2, 1), cood.addXY(2, - 1)).toLine2(cood => coodToDispVec2(cood))
   def sideDRLine: LineSeg = CoodLine(cood.addXY(2, -1), cood.addXY(0, -1)).toLine2(cood => coodToDispVec2(cood))
   override def ownSideLines: LineSegs = LineSegs(sideURLine, sideRightLine, sideDRLine)
}

object OfHex
{
  // implicit def apply[TileT <: Tile, SideT <: TileSide, GridT <: HexGridComplex[TileT, SideT]](tile: TileT, grid: GridT, gGui: TileGridGui[TileT, SideT, GridT]):
    //  OfHex[TileT, SideT, GridT] = new OfHex[TileT, SideT, GridT](tile,grid, gGui)     
}