/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid
import geom._

/** Although including the cood could be considered to break the principle of encapsulation, A tile should not need to know where it is in a grid. I
 *   think it is necessary. Although the cood is determined by its position in the array there is just no good way for this data to be recovered by
 *    the Grid for random access. I think also it might be better to change to a var */
trait OfGridElem[TileT <: GridElem, SideT <: GridElem, GridT <: TileGrid[TileT, SideT]]
{
   //def gGui: GridGui[TileT, SideT]
   def grid: GridT
   def cood: Cood
   def coodToVec2(inp: Cood): Vec2
   def coodToDispVec2(inp: Cood): Vec2
   def xyStr = cood.xyStr
   def yxStr = cood.yxStr
   /** The number of pixels grid unit. The number of pixels per X Cood. Called pSc so it doesn't class with pScale from another class when
    *  the OfTile object's members are imported */
   def psc: Double
   /** The number of pixels per tile, centre to centre */
   def tScale: Double = psc * grid.xStep
   def ifScaleCObjs(ifScale: Double, cObjs: => CanvObjs): CanvObjs = if (tScale > ifScale) cObjs else Nil
   def ifScaleCObj(ifScale: Double, cObj: CanvO *): CanvObjs = if (tScale > ifScale) cObj.toList else Nil
   def ifScaleIfCObjs(ifScale: Double, b: Boolean, cObjs: => CanvObjs): CanvObjs = if (tScale > ifScale && b) cObjs else Nil
   def ifScaleIfCObj(ifScale: Double, b: Boolean, cObjs: CanvO *): CanvObjs = if (tScale > ifScale && b) cObjs.toList else Nil
}

trait OfGridElemReg[TileT <: GridElem, SideT <: GridElem, GridT <: TileGridReg[TileT, SideT]] extends OfGridElem[TileT, SideT, GridT]
{
   def gGui: TileGridGui[TileT, SideT, GridT]
   //@inline def coodToMapVec2(inp: Cood): Vec2 = grid.coodToVec2(inp)
   @inline def coodToDispVec2(inp: Cood): Vec2 = fTrans(grid.coodToVec2(inp)) 
   /* Transforms from grid position to display position */
   @inline def fTrans(inp: Vec2): Vec2 = gGui.fTrans(inp)
   override def psc = gGui.pScale
   override def coodToVec2(inp: Cood): Vec2 = grid.coodToVec2(inp)
}