/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid
import geom._

/** A regular tile grid as opposed to an earth based tile grid or other grid that is not mapping a flat 2d surface */
trait TileGridReg[TileT <: GridElem, SideT <: GridElem] extends TileGrid[TileT, SideT]
{
   override def optTile(x: Int, y: Int): Option[TileT] = if (x >= xTileMin & x <= xTileMax & y >= yTileMin & y <= yTileMax )
      Some(getTile(x, y)) else None
   def coodToVec2(cood: Cood): Vec2    
}
