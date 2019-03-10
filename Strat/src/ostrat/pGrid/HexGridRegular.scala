/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid

trait HexGridRegular[TileT <: Tile] extends HexGrid[TileT] with TileGridReg[TileT]
{
  def horrMargin: Double
  def vertMargin: Double
  final def left: Double = xTileMin - horrMargin
  final def right: Double = xTileMax + horrMargin
  final def bottom: Double = yTileMin  * yRatio - vertMargin
  final def top: Double = yTileMax * yRatio + vertMargin
  
  def tileNum: Int
} 