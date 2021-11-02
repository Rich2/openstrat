/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth
import geom._, pglobe._, pGrid._

trait OfEElem[TileT <: TileAncient, SideT <: TileSideAncient] extends OfGridElem[TileT, SideT, EGridAncient[TileT, SideT]]
{
   val eg: EarthGuiOld
   val eGrid: EGridAncient[TileT, SideT]
   override def grid: EGridAncient[TileT, SideT]= eGrid
   def gridScale: Metres = eGrid.scale
   def focus: LatLong = eg.focus   
   override def coodToDispVec2(inp: Cood): Pt2 = eg.trans(eg.latLongToDist2(eGrid.getLL(inp)))
   def egScale: Metres = eg.scale
   override def psc = gridScale / egScale   
}

/** A stand OfTile maps from Grid Coordinates to map Vec2 and then to display Vec2. This maps from Grid Coordinate to Dist2 to Vec2 */
class OfETile[TileT <: TileAncient, SideT <: TileSideAncient](val eg: EarthGuiOld, val eGrid: EGridAncient[TileT, SideT], val tile: TileT) extends
OfHex[TileT, SideT, EGridAncient[TileT, SideT]] with OfEElem[TileT, SideT]
{
   def cenLL: LatLong = eGrid.getLL(cood)
   def cen: Pt2 = eg.latLongToXY(cenLL)
   def cenFacing: Boolean = focus.latLongFacing(cenLL)
   def vertLLs: PolygonLL = vertCoods.mapPolygon(eGrid.getLL)
   def vertDist2s: PtMetre2Arr = eg.polyToDist2s(vertLLs)
   override def vertDispVecs: Polygon = vertDist2s.mapPolygon(eg.trans)
}

class OfESide[TileT <: TileAncient, SideT <: TileSideAncient](val eg: EarthGuiOld, val eGrid: EGridAncient[TileT, SideT], val side: SideT) extends
OfHexSide[TileT, SideT, EGridAncient[TileT, SideT]] with OfEElem[TileT, SideT]
{
   def sideCenFacing: Boolean = focus.latLongFacing(sideCenLL)
   def sideCenLL: LatLong = eGrid.getLL(cood)   
}