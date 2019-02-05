/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid
import geom._
/** A Gui for a single regular SquareGridComplex. Currently there are no irregular SquareGrids */
abstract class SquareGridGui[TileT <: Tile, SideT <: GridElem, GridT <: SquareGridComplex[TileT, SideT]](val canv: pCanv.CanvasPlatform,
    var grid: GridT, title: String) extends TileGridGui[TileT, SideT, GridT](title)
{
   /** A tile measures 2 coordinates in both x and y direction. */
   override def ptScale = pScale / 2
   
   def ofSTilesFold[R](f: OfSquareReg[TileT, SideT, GridT] => R, fSum: (R, R) => R, emptyVal: R) =
   {
      var acc: R = emptyVal
      grid.tileCoodForeach{ tileCood =>
         val newTog = OfSquareReg(grid.getTile(tileCood), grid, this)
         val newRes: R = f(newTog)
         acc = fSum(acc, newRes)
      }
      acc
   }
   
   def ofSTilesDisplayFold(f: OfSquareReg[TileT, SideT, GridT] => GraphicElems): GraphicElems = ofSTilesFold[GraphicElems](f, _ ++ _, Nil)
} 