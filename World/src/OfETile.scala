/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pEarth
import geom._, pGrid._

trait OfEElem[TileT <: TileOld, SideT <: TileSideOld] extends OfGridElem[TileT, SideT, EGridOld[TileT, SideT]]
{
   val eg: EarthGuiOld
   val eGrid: EGridOld[TileT, SideT]
   override def grid: EGridOld[TileT, SideT]= eGrid
   def gridScale: Dist = eGrid.scale
   def focus: LatLong = eg.focus   
   override def coodToDispVec2(inp: Cood): Vec2 = eg.trans(eg.latLongToDist2(eGrid.getLL(inp)))
   def egScale: Dist = eg.scale
   override def psc = gridScale / egScale   
}

/** A stand OfTile maps from Grid Coordinates to map Vec2 and then to display Vec2. This maps from Grid Coordinate to Dist2 to Vec2 */
class OfETile[TileT <: TileOld, SideT <: TileSideOld](val eg: EarthGuiOld, val eGrid: EGridOld[TileT, SideT], val tile: TileT) extends
OfHex[TileT, SideT, EGridOld[TileT, SideT]] with OfEElem[TileT, SideT]
{
   def cenLL: LatLong = eGrid.getLL(cood)
   def cen: Vec2 = eg.latLongToXY(cenLL)
   def cenFacing: Boolean = focus.latLongFacing(cenLL)
   def vertLLs: LatLongs = vertCoods.pMap(eGrid.getLL)
   def vertDist2s: Dist2s = eg.polyToDist2s(vertLLs)
   override def vertDispVecs: PolygonGen = vertDist2s.pMap(eg.trans)
}

class OfESide[TileT <: TileOld, SideT <: TileSideOld](val eg: EarthGuiOld, val eGrid: EGridOld[TileT, SideT], val side: SideT) extends
OfHexSide[TileT, SideT, EGridOld[TileT, SideT]] with OfEElem[TileT, SideT]
{
   def sideCenFacing: Boolean = focus.latLongFacing(sideCenLL)
   def sideCenLL: LatLong = eGrid.getLL(cood)   
}
