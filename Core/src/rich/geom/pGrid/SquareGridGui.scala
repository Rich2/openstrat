/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */package rich
package geom
package pGrid

/** A Gui for a single regular SquareGrid. Currently there are no irregular SquareGrids */
abstract class SquareGridGui[TileT <: Tile, GridT <: SquareGrid[TileT]](val canv: pDisp.CanvDisp, val grid: GridT) extends
   TileGridGui[TileT, GridT]
{
   /** A tile measures 2 coordinates in both x and y direction. */
   override def ptScale = pScale / 2
     
//   def ofSquaresFold[GridT <: SquareGrid[TileT], R](f: RegSquareOfGrid[TileT] => R, fSum: (R, R) => R, emptyVal: R) =
//   {
//      var acc: R = emptyVal
//      grid.tileCoodForeach{ tileCood =>         
//         val newRes: R = f(RegSquareOfGrid[TileT](this, grid, grid.getTile(tileCood)))//{val grid: TileGrid[TileT, SideT]=  thisGrid })
//         acc = fSum(acc, newRes)
//      }
//      acc
//   }
//   
//   def ofSquaresDisplayFold(f: RegSquareOfGrid[TileT] => Disp2): Disp2 = ofSquaresFold[Disp2](f, _ ++ _, Disp2.empty)   

} 