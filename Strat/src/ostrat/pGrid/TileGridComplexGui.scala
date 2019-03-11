/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid
import geom._

/** Gui for display of a single regular complex TileGrid */
abstract class TileGridComplexGui[TileT <: Tile, SideT <: GridElem, GridT <: TileGridComplexReg[TileT, SideT]](title: String) extends
  TileGridGui[TileT, GridT](title)
{  
  def ofTilesFold[OfT <: OfTile[TileT, SideT, GridT], R](f: OfT => R, fSum: (R, R) => R, emptyVal: R)(implicit oftFactory: (TileT, GridT,
      TileGridComplexGui[TileT, SideT, GridT]) => OfT) =
  {
    var acc: R = emptyVal
    foreachTileCood{ tileCood =>
      val newOft = oftFactory(grid.getTile(tileCood), grid, this)
      val newRes: R = f(newOft)
      acc = fSum(acc, newRes)
    }
    acc
  }
   
  def ofSidesFold[OfS <: OfSide[TileT, SideT, GridT], R](f: OfS => R, fSum: (R, R) => R, emptyVal: R)(implicit ofsFactory: (SideT, GridT,
      TileGridComplexGui[TileT, SideT, GridT]) => OfS) =
  {
    var acc: R = emptyVal
    grid.sideCoodForeach{ tileCood =>
      val newOfs = ofsFactory(grid.getSide(tileCood), grid, this)
      val newRes: R = f(newOfs)
      acc = fSum(acc, newRes)
    }
    acc
  }
   
  def ofTilesDisplayFold[OfT <: OfTile[TileT, SideT, GridT]](f: OfT => GraphicElems)(implicit oftFactory: (TileT, GridT,
      TileGridComplexGui[TileT, SideT, GridT]) => OfT): GraphicElems = ofTilesFold[OfT, GraphicElems](f, _ ++ _, Nil)(oftFactory)
         
  def ofSidesDisplayFold[OfT <: OfSide[TileT, SideT, GridT]](f: OfT => GraphicElems)(implicit ofsFactory: (SideT, GridT,
      TileGridComplexGui[TileT, SideT, GridT]) => OfT): GraphicElems = ofSidesFold[OfT, GraphicElems](f, _ ++ _, Nil)(ofsFactory)
 
  @inline def adjTileCoodsOfTile(tileCood: Cood): Coods = grid.adjTileCoodsOfTile(tileCood)
}