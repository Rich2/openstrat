/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pEarth
import geom._
import pGrid._

/** A stand OfTile maps from Grid Coordinates to map Vec2 and then to display Vec2. This maps from Grid Coordinate to Dist2 to Vec2 */
class OfETile[TileT <: GridElem, SideT <: GridElem](val eg: EarthGui, val eGrid: EGrid[TileT, SideT], val tile: TileT) extends
OfHex[TileT, SideT, EGrid[TileT, SideT]] with OfEElem[TileT, SideT]
{   
   
   def cenLL: LatLong = eGrid.getLL(cood)
   def cen: Vec2 = eg.latLongToXY(cenLL)
   def cenFacing: Boolean = focus.latLongFacing(cenLL)
   def vertLLs: LatLongs = vertCoods.pMap(eGrid.getLL)
   def vertDist2s: Dist2s = eg.polyToDist2s(vertLLs)
   override def vertDispVecs: Vec2s = vertDist2s.pMap(eg.trans)   
}

class OfESide[TileT <: GridElem, SideT <: GridElem](val eg: EarthGui, val eGrid: EGrid[TileT, SideT], val side: SideT) extends
OfHexSide[TileT, SideT, EGrid[TileT, SideT]] with OfEElem[TileT, SideT]
{
   def sideCenFacing: Boolean = focus.latLongFacing(sideCenLL)
}

trait OfEElem[TileT <: GridElem, SideT <: GridElem] extends OfGridElem[TileT, SideT, EGrid[TileT, SideT]]
{
   val eg: EarthGui
   val eGrid: EGrid[TileT, SideT]
   override def grid: EGrid[TileT, SideT]= eGrid
   def gridScale: Dist = eGrid.scale
   def focus: LatLong = eg.focus
   //def coodToVec2(inp: Cood): Vec2 = eg.latLongToXY(eGrid.getLL(inp))
   /** Temp fix */
   def coodToDispVec2(inp: Cood): Vec2 = eg.trans(eg.latLongToDist2(eGrid.getLL(inp)))
   def egScale: Dist = eg.scale
   override def psc = gridScale / egScale
   def sideCenLL: LatLong = eGrid.getLL(cood)
   def sideCen: Vec2 = eg.latLongToXY(sideCenLL)
}