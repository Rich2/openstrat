/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid


trait TileGridReg[TileT <: GridElem, SideT <: GridElem] extends TileGrid[TileT, SideT]
{
   override def optTile(x: Int, y: Int): Option[TileT] = if (x >= xTileMin & x <= xTileMax & y >= yTileMin & y <= yTileMax )
      Some(getTile(x, y)) else None
}