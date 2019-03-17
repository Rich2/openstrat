/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid

trait TileGridSimple[TileT <: Tile] extends TileGridReg[TileT]
{
  final override def yStep: Int = 1
}