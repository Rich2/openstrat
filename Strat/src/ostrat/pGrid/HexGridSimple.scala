/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid
import geom._

/** A regular HexGrid containing only values for the tiles not for the boundaries between the tiles. */
abstract class HexGridSimple[TileT <: Tile](val xTileMin: Int, val xTileMax: Int, val yTileMin: Int, val yTileMax: Int)
   (implicit val evTile: IsType[TileT]) extends TileGridReg[TileT] with HexGrid[TileT]
{
  override def coodToVec2(cood: Cood): Vec2 = HexGrid.coodToVec2(cood)
}

