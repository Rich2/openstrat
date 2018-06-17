/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package rich
package geom
package pEarth
import pGrid._

class OfETile[TileT <: Tile](val eg: EarthGui, val eGrid: EGrid[TileT], val tile: TileT) extends OfHex[TileT, EGrid[TileT]]
{
   /** eGrid need to be got rid of */
   override def grid: EGrid[TileT]= eGrid
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