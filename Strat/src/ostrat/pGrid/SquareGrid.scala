/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid
import geom._

/** A square regular tile grid. Square grids can be simple with just tile values or complex with tile and side values, such as a wall which 
 *  corresponds to the boundary between two tiles rather to a tile. */
trait SquareGrid[TileT <: Tile] extends TileGrid[TileT] with TileGridReg[TileT]   
{
  override val yRatio = 1
  override def xToInd(x: Int): Int = (x - xTileMin)
  def coodToVec2(cood: Cood): Vec2 = Vec2(cood.x, cood.y)
}