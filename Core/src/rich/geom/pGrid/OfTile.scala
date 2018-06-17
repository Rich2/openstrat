/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package rich
package geom
package pGrid

/** I am happy with the fundamental concept behind the OfTile traits, documentation later */
trait OfTile[TileT <: Tile, GridT <: TileGrid[TileT]]
{
   //def gGui: GridGui[TileT, SideT]
   def grid: GridT//ileGrid[TileT]
   def tile: TileT    
   def cood = tile.cood
   def coodToDispVec2(inp: Cood): Vec2
   def xyStr = cood.xyStr
   def yxStr = cood.yxStr
   def vertCoods: Coods = grid.tileVertCoods(cood)
   def vertVecs: Vec2s
   def cen: Vec2
   /** The number of pixels grid unit. The number of pixels per X Cood. Called pSc so it doesn't class with pScale from another class when
    *  the OfTile object's members are imported */
   def psc: Double
   /** The number of pixels per tile, centre to centre */
   def tScale: Double = psc * grid.xStep
   def ifScaleCObjs(ifScale: Double, cObjs: => CanvObjs): CanvObjs = if (tScale > ifScale) cObjs else Nil
   def ifScaleCObj(ifScale: Double, cObj: CanvO *): CanvObjs = if (tScale > ifScale) cObj else Nil
   def ifScaleIfCObjs(ifScale: Double, b: Boolean, cObjs: => CanvObjs): CanvObjs = if (tScale > ifScale && b) cObjs else Nil
   def ifScaleIfCObj(ifScale: Double, b: Boolean, cObjs: CanvO *): CanvObjs = if (tScale > ifScale && b) cObjs else Nil
   def ownSideLines: List[Line2]
}

trait OfTileReg[TileT <: Tile, GridT <: TileGrid[TileT]] extends OfTile[TileT, GridT]
{
   def gGui: TileGridGui[TileT, GridT]
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
