/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid
import geom._

trait OfGridElem[TileT <: GridElem, SideT <: GridElem, GridT <: TileGrid[TileT, SideT]]
{
   //def gGui: GridGui[TileT, SideT]
   def grid: GridT
   def cood: Cood
   def coodToDispVec2(inp: Cood): Vec2
   def xyStr = cood.xyStr
   def yxStr = cood.yxStr
}