/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid
import geom._

/** I am happy with the fundamental concept behind the OfTile traits, documentation later */
trait OfTile[TileT <: GridElem, SideT <: GridElem, GridT <: TileGrid[TileT, SideT]] extends OfGridElem[TileT, SideT, GridT]
{
   def tile: TileT    
   final def cood: Cood = tile.cood   
   def vertCoods: Coods = grid.tileVertCoods(cood)
   def vertDispVecs: Vec2s
   def cen: Vec2
   def ownSideLines: List[Line2]
}

trait OfTileReg[TileT <: GridElem, SideT <: GridElem, GridT <: TileGridReg[TileT, SideT]] extends OfTile[TileT, SideT, GridT] with
OfGridElemReg[TileT, SideT, GridT]
{
   
   override def vertDispVecs: Vec2s = vertCoods.pMap(coodToDispVec2)//.fTrans(gGui.fTrans)
   /** Tile centre posn on Grid */
   def cenRelGrid: Vec2 = grid.coodToVec2(cood)
   /** Tile centre posn on display */
   def cen: Vec2 = gGui.fTrans(cenRelGrid)   
}
