/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid
import geom._

trait OfSide[TileT <: GridElem, SideT <: GridElem, GridT <: TileGrid[TileT, SideT]] extends OfGridElem[TileT, SideT, GridT]
{
   def side: SideT    
   final def cood: Cood = side.cood
   def sideCen: Vec2
   def coodsLine: CoodLine = grid.sideVertCoods(cood)
   def vertLine: Line2 = coodsLine.toLine2(coodToDispVec2)
}