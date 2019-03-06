/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid
import geom._

/** A regular HexGrid containing only values for the tiles not for the boundaries between the tiles. */
abstract class HexGridSimple[TileT <: Tile](val xTileMin: Int, val xTileMax: Int, val yTileMin: Int, val yTileMax: Int)
   (implicit val evTile: IsType[TileT]) extends TileGridReg[TileT] with HexGrid[TileT]
{
  override def coodToVec2(cood: Cood): Vec2 = HexGrid.coodToVec2(cood)
  def vertMargin = 0.7
  def horrMargin = 2.2
  override def left: Double = xTileMin - horrMargin
  override def right: Double = xTileMax + horrMargin
  def bottom: Double = (yTileMin - 2) * yRatio - vertMargin
  def top: Double = (yTileMax + 2) * yRatio + vertMargin
  override def xStep: Int = 2
  override def xArrLen: Int = xTileMax / 2 - xTileMin / 2 + 2 //+1 for zeroth tile, +1 for right side
  override val yArrLen: Int = yTileMax - yTileMin + 1//for zeroth tile, + 1 for upper side(s)
  override val arr: Array[AnyRef] = new Array[AnyRef](arrLen)
  override def xToInd(x: Int): Int = x / 2 - xTileMin / 2
  override def yToInd(y: Int): Int = (y  - yTileMin + 1)
  override def tileXYForeach(f: (Int, Int) => Unit): Unit = ???
}

