/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid
import geom._

trait OfGridElemReg[TileT <: GridElem, SideT <: GridElem, GridT <: TileGridReg[TileT, SideT]] extends OfGridElem[TileT, SideT, GridT]
{
   def gGui: TileGridGui[TileT, SideT, GridT]   
   @inline final override def coodToDispVec2(inp: Cood): Vec2 = gGui.fTrans(grid.coodToVec2(inp))   
   override def psc = gGui.pScale
   /** Should possibly be called CoodToMapVec2 */
   def coodToVec2(inp: Cood): Vec2 = grid.coodToVec2(inp)   
}

trait OfTileReg[TileT <: GridElem, SideT <: GridElem, GridT <: TileGridReg[TileT, SideT]] extends OfTile[TileT, SideT, GridT] with
OfGridElemReg[TileT, SideT, GridT]
{   
   override def vertDispVecs: Polygon = vertCoods.pMap(coodToDispVec2)//.fTrans(gGui.fTrans)
   /** Tile centre posn on Grid */
   def cenRelGrid: Vec2 = grid.coodToVec2(cood)
   /** Tile centre posn on display */
   def cen: Vec2 = gGui.fTrans(cenRelGrid)   
}

trait OfSideReg[TileT <: GridElem, SideT <: GridElem, GridT <: TileGridReg[TileT, SideT]] extends OfSide[TileT, SideT, GridT] with
OfGridElemReg[TileT, SideT, GridT]
{
   def sideCen: Vec2
}