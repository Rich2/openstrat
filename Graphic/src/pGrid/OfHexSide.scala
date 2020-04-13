/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid
import geom._

trait OfHexSide[TileT <: TileOld, SideT <: TileSideOld, GridT <: HexGridOld[TileT, SideT]] extends OfSide[TileT, SideT, GridT]

/*
case class OfHexSideReg[TileT <: TileOld, SideT <: TileSideOld, GridT <: HexGridRegOld[TileT, SideT]](side: SideT, grid: GridT,
  gGui: TileGridGui[TileT, SideT, GridT]) extends OfSide[TileT, SideT, GridT] with OfGridElemReg[TileT, SideT, GridT]
{
  def sideCenRelGrid: Vec2 = grid.coodToVec2(cood)
  def sideCen: Vec2 = gGui.fTrans(sideCenRelGrid)
}

object OfHexSideReg
{
  implicit def implicitBuilder[TileT <: TileOld, SideT <: TileSideOld, GridT <: HexGridRegOld[TileT, SideT]](side: SideT, grid: GridT,
    gGui: TileGridGui[TileT, SideT, GridT]) = apply(side, grid, gGui)
}*/
