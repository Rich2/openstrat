/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid
import geom._, reflect.ClassTag

/** A regular square grid containing only values for the tiles not for the boundaries between the tiles. */
abstract class SquareGridSimple[TileT <: Tile](val xTileMin: Int, val xTileMax: Int, val yTileMin: Int, val yTileMax: Int)
   (implicit val evTile: ClassTag[TileT]) extends SquareGrid[TileT] with TileGridSimple[TileT]
{
  override val yArrLen: Int = yTileMax - yTileMin + 1
  override def xArrLen: Int = xTileMax - xTileMin + 1
  override val arr: Array[TileT] = new Array[TileT](arrLen)
  override def xStep: Int = 1
  override def coodIsTile(x: Int, y: Int): Unit = {}  
  //override def forallTilesXY(f: (Int, Int) => Unit): Unit = ??? 
  override def margin: Double = 0.6  
  override def xToInd(x: Int): Int = (x - xTileMin)
  
  override def tileRowLen = (xTileMax - xTileMin + 1).min(0)
  override def tileColumnLen = (yTileMax - yTileMin + 1).min(0)
  override def tileNum: Int =  tileRowLen * tileColumnLen
  override def allSideLines: Line2s = ???
  
  /** Sets a rectangle of tiles to the same tile value. */
  final override def setTilesRectangle[A](bottomLeft: Cood, topRight: Cood, tileValue: A)(implicit f: (Int, Int, A) => TileT): Unit = for {
    y <- bottomLeft.y to topRight.y
    x <- bottomLeft.x to topRight.x
  } fSetTile(x, y, tileValue)
}

object SquareGridSimple
{
   val vertCoodsOfTile00: Coods = Coods.xy(1,1,  1,-1,  -1,-1, -1,1)
   def vertCoodsOfTile(inp: Cood): Coods = vertCoodsOfTile00.pMap(inp + _)
   val adjTileCoodsOfTile00: Coods = Coods(0 cc 2, 2 cc 2, 2 cc 0, 2 cc -2, 0 cc -2, -2 cc -2, -2 cc 0)
   def adjTileCoodsOfTile(tileCood: Cood): Coods = adjTileCoodsOfTile00.pMap(tileCood + _)
}