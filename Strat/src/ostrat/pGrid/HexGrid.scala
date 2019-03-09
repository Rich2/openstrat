/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid
import math.sqrt

/** A square regular tile grid. Square grids can be simple with just tile values or complex with tile and side values, such as a wall which 
 *  corresponds to the boundary between two tiles rather to a tile. */
trait HexGrid[TileT <: Tile] extends TileGrid[TileT]  
{
  override val yRatio: Double = HexGrid.yRatio
  def coodIsTile(x: Int, y: Int): Unit = Unit match
  { case _ if x.isEven & y.isEven =>
    case _ if x.isOdd & y.isOdd =>
    case _ => excep(x.toString.commaAppend(y.toString) -- "is an invalid Hex tile coordinate")   
  }
}

object HexGrid
{
  val yRatio = sqrt(3)
}

trait HexGridReg[TileT <: Tile] extends HexGrid[TileT] with TileGridReg[TileT]
{
  def horrMargin: Double
  def vertMargin: Double
  final def left: Double = xTileMin - horrMargin
  final def right: Double = xTileMax + horrMargin
  final def bottom: Double = yTileMin  * yRatio - vertMargin
  final def top: Double = yTileMax * yRatio + vertMargin
}


