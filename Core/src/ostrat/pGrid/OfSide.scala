/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid

trait OfSide[TileT <: GridElem, SideT <: GridElem, GridT <: TileGrid[TileT, SideT]] extends OfGridElem[TileT, SideT, GridT]
{
   def side: SideT    
   final def cood: Cood = side.cood
}