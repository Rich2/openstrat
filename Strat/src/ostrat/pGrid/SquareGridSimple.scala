/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid
import geom._, reflect.ClassTag

/** A regular square grid containing only values for the tiles not for the boundaries between the tiles. */
abstract class SquareGridSimple[TileT <: Tile](val xTileMin: Int, val xTileMax: Int, val yTileMin: Int, val yTileMax: Int)
   (implicit val evTile: ClassTag[TileT]) extends SquareGrid[TileT] with TileGridReg[TileT]
{
  override val yArrLen: Int = yTileMax - yTileMin + 1
  override def xArrLen: Int = xTileMax - xTileMin + 1
  override val arr: Array[TileT] = new Array[TileT](arrLen)
  override def xStep: Int = 1
  override def coodIsTile(x: Int, y: Int): Unit = {}  
  override def foreachTileXY(f: (Int, Int) => Unit): Unit = ??? 
  override def margin: Double = 0.6  
  override def xToInd(x: Int): Int = (x - xTileMin)
  
  override def tileNum: Int = ???
  def sideLines: Line2s = ???
}

object SquareGridSimple
{
   val vertCoodsOfTile00: Coods = Coods.xy(1,1,  1,-1,  -1,-1, -1,1)
   def vertCoodsOfTile(inp: Cood): Coods = vertCoodsOfTile00.pMap(inp + _)
   val adjTileCoodsOfTile00: Coods = Coods(0 cc 2, 2 cc 2, 2 cc 0, 2 cc -2, 0 cc -2, -2 cc -2, -2 cc 0)
   def adjTileCoodsOfTile(tileCood: Cood): Coods = adjTileCoodsOfTile00.pMap(tileCood + _)
}