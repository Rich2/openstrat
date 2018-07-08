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
   def vertVecs: Vec2s
   def cen: Vec2
   /** The number of pixels grid unit. The number of pixels per X Cood. Called pSc so it doesn't class with pScale from another class when
    *  the OfTile object's members are imported */
   def psc: Double
   /** The number of pixels per tile, centre to centre */
   def tScale: Double = psc * grid.xStep
   def ifScaleCObjs(ifScale: Double, cObjs: => CanvObjs): CanvObjs = if (tScale > ifScale) cObjs else Nil
   def ifScaleCObj(ifScale: Double, cObj: CanvO *): CanvObjs = if (tScale > ifScale) cObj.toList else Nil
   def ifScaleIfCObjs(ifScale: Double, b: Boolean, cObjs: => CanvObjs): CanvObjs = if (tScale > ifScale && b) cObjs else Nil
   def ifScaleIfCObj(ifScale: Double, b: Boolean, cObjs: CanvO *): CanvObjs = if (tScale > ifScale && b) cObjs.toList else Nil
   def ownSideLines: List[Line2]
}

trait OfTileReg[TileT <: GridElem, SideT <: GridElem, GridT <: TileGrid[TileT, SideT]] extends OfTile[TileT, SideT, GridT]
{
   def gGui: TileGridGui[TileT, SideT, GridT]
   //@inline def coodToMapVec2(inp: Cood): Vec2 = grid.coodToVec2(inp)
   @inline def coodToDispVec2(inp: Cood): Vec2 = fTrans(grid.coodToVec2(inp)) 
   /* Transforms from grid position to display position */
   @inline def fTrans(inp: Vec2): Vec2 = gGui.fTrans(inp)
   def vertVecs: Vec2s = vertCoods.pMap(coodToDispVec2)//.fTrans(gGui.fTrans)
   /* Tile centre posn on Grid */
   def cenRelGrid: Vec2 = grid.coodToVec2(cood)
   /* Tile centre posn on display */
   def cen: Vec2 = fTrans(cenRelGrid)
   override def psc = gGui.pScale
}
