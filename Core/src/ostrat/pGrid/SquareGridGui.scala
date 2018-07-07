/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid

/** A Gui for a single regular SquareGrid. Currently there are no irregular SquareGrids */
abstract class SquareGridGui[TileT <: GridElem, SideT <: GridElem, GridT <: SquareGrid[TileT, SideT]](
      val canv: pDisp.CanvasPlatform, val grid: GridT) extends TileGridGui[TileT, SideT, GridT]
{
   /** A tile measures 2 coordinates in both x and y direction. */
   override def ptScale = pScale / 2
} 