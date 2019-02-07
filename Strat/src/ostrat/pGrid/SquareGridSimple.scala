/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid

/** A regular square grid containing only values for the tiles not for the boundaries between the tiles. */
abstract class SquareGridSimple[TileT <: Tile](val xTileMin: Int, val xTileMax: Int, val yTileMin: Int, val yTileMax: Int)
   (implicit val evTile: IsType[TileT]) extends SquareGrid[TileT] with TileGridReg[TileT]
{
  override val yArrLen: Int = yTileMax - yTileMin + 1
  override def xArrLen: Int = xTileMax - xTileMin + 1
  override val arr: Array[AnyRef] = new Array[AnyRef](arrLen)
  override def xStep: Int = 1
  def setTile(x: Int, y: Int, tile: TileT): Unit = arr(xyToInd(x, y)) = tile
  def setTile(tc: Cood, tile: TileT): Unit = arr(xyToInd(tc.x, tc.y)) = tile
  //These need changing
  def left: Double = xTileMin - 1.1
  def right: Double = xTileMax + 1.1
  def bottom: Double = yTileMin - 1.1
  def top: Double = yTileMax + 1.1 
  
  def yToInd(y: Int): Int = (y  - yTileMin + 1)
  override def xToInd(x: Int): Int = (x - xTileMin)
  
  def getTile(x: Int, y: Int): TileT = evTile.asType(arr(xyToInd(x, y)))    
  def getTile(tc: Cood): TileT = evTile.asType(arr(xyToInd(tc.x, tc.y)))
}

object SquareGridSimple
{
   val vertCoodsOfTile00: Coods = Coods.xy(1,1,  1,-1,  -1,-1, -1,1)
   def vertCoodsOfTile(inp: Cood): Coods = vertCoodsOfTile00.pMap(inp + _)
   val adjTileCoodsOfTile00: Coods = Coods(0 cc 2, 2 cc 2, 2 cc 0, 2 cc -2, 0 cc -2, -2 cc -2, -2 cc 0)
   def adjTileCoodsOfTile(tileCood: Cood): Coods = adjTileCoodsOfTile00.pMap(tileCood + _)
}