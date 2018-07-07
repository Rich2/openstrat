/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pEarth
import geom._
import pGrid._

class OfETile[TileT <: GridElem, SideT <: GridElem](val eg: EarthGui, val eGrid: EGrid[TileT, SideT], val tile: TileT) extends
OfHex[TileT, SideT, EGrid[TileT, SideT]]
{
   /** eGrid need to be got rid of */
   override def grid: EGrid[TileT, SideT]= eGrid
   def gridScale: Dist = eGrid.scale
   def focus: LatLong = eg.focus
   def coodToDispVec2(inp: Cood): Vec2 = eg.latLongToXY(eGrid.getLL(inp))
   def cenLL: LatLong = eGrid.getLL(cood)
   def cen: Vec2 = eg.latLongToXY(cenLL)
   def cenFacing: Boolean = focus.latLongFacing(cenLL)
   def latLongs: LatLongs = vertCoods.pMap(eGrid.getLL)
   def vertDists: Dist2s = eg.polyToDist2s(latLongs)
   override def vertVecs: Vec2s = vertDists.pMap(eg.trans) //  eg.transSeq(vertDists)
   def egScale: Dist = eg.scale
   override def psc = gridScale / egScale
}