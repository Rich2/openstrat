/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid
import geom._
import pDisp._

/** Class for displaying a single hex grid */
abstract class HexGridGui[TileT <: GridElem, SideT <: GridElem, GridT <: HexGrid[TileT, SideT]](val canv: CanvasPlatform, val grid: GridT) extends
   TileGridGui[TileT, SideT, GridT]
{
   override def ptScale = pScale / 4
   
   def ofHTilesFold[R](f: OfHexReg[TileT, SideT, GridT] => R, fSum: (R, R) => R, emptyVal: R) =
   {
      var acc: R = emptyVal
      grid.tileCoodForeach{ tileCood =>
         val newTog = OfHexReg(grid.getTile(tileCood), grid, this)
         val newRes: R = f(newTog)
         acc = fSum(acc, newRes)
      }
      acc
   }
   
   def ofHTilesDisplayFold(f: OfHexReg[TileT, SideT, GridT] => Disp2): Disp2 = ofHTilesFold[Disp2](f, _ ++ _, Disp2.empty)
//   def ofHexsFold[R](f: RegHexOfGrid[TileT] => R, fSum: (R, R) => R, emptyVal: R) =
//   {
//      var acc: R = emptyVal
//      grid.tileCoodForeach{ tileCood =>         
//         val newRes: R = f(RegHexOfGrid[TileT](this, grid, grid.getTile(tileCood)))//{val grid: TileGrid[TileT, SideT]=  thisGrid })
//         acc = fSum(acc, newRes)
//      }
//      acc
//   }
//   
//   def ofHexsDisplayFold(f: RegHexOfGrid[TileT] => Disp2): Disp2 = ofHexsFold[Disp2](f, _ ++ _, Disp2.empty)  
   //override def sideXYVertCoods(x: Int, y: Int): CoodLine = HexGrid.sideXYVertCoods(x, y)
   //override val yRatio: Double = HexCood.yRatio
//   val xRadius: Double = 2 
//   val yRadius: Double = HexCood.yDist2
// 
   //val scaleMin = gridScale / 1000
   //val scaleMax: Dist = gridScale / 10
  // var scale = scaleMax
//   mapFocus = mapCen  
}

//abstract class HexRegGui[TileT <: Tile, SideT <: TileSide](val canv: CanvasPlatform, val grid: HexGridReg[TileT, SideT]) extends GridGui[TileT, SideT]