/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid
import geom._


trait OfHexSide[TileT <: GridElem, SideT <: GridElem, GridT <: HexGrid[TileT, SideT]] extends OfSide[TileT, SideT, GridT]

case class OfHexSideReg[TileT <: GridElem, SideT <: GridElem, GridT <: HexGridReg[TileT, SideT]](
      side: SideT, grid: GridT, gGui: TileGridGui[TileT, SideT, GridT]) extends OfSide[TileT, SideT, GridT] with OfGridElemReg[TileT, SideT, GridT]
{
   def sideCenRelGrid: Vec2 = grid.coodToVec2(cood)
   def sideCen: Vec2 = fTrans(sideCenRelGrid)
   //def vertLine: Line2 = vertCoods.toLine2()
}

object OfHexSideReg
{
   implicit def implicitBuilder[TileT <: GridElem, SideT <: GridElem, GridT <: HexGridReg[TileT, SideT]](side: SideT, grid: GridT,
         gGui: TileGridGui[TileT, SideT, GridT]) = apply(side, grid, gGui)
}