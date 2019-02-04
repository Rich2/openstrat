/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid
import geom._

abstract class SquareGridSimple[TileT <: GridElem, SideT <: GridElem](xTileMin: Int, xTileMax: Int, yTileMin: Int, yTileMax: Int)
   (implicit evTile: IsType[TileT], evSide: IsType[SideT]) extends  SquareGrid[TileT, SideT](xTileMin, xTileMax, yTileMin, yTileMax)
{
  override def xStep: Int = 1
}

object SquareGridSimple
{
   val vertCoodsOfTile00: Coods = Coods.xy(1,1,  1,-1,  -1,-1, -1,1)
   def vertCoodsOfTile(inp: Cood): Coods = vertCoodsOfTile00.pMap(inp + _)
   val adjTileCoodsOfTile00: Coods = Coods(0 cc 2, 2 cc 2, 2 cc 0, 2 cc -2, 0 cc -2, -2 cc -2, -2 cc 0)
   def adjTileCoodsOfTile(tileCood: Cood): Coods = adjTileCoodsOfTile00.pMap(tileCood + _)
}