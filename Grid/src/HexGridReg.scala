/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid

class HexGridReg(yTileMin: Int, yTileMax: Int, cTileMin: Int, cTileMax: Int) extends HexGridRegSimple(yTileMin, yTileMax, cTileMin, cTileMax) with
  TileGrid
{

}

object HexGridReg
{
  def apply(yTileMin: Int, yTileMax: Int, cTileMin: Int, cTileMax: Int): HexGridReg = new HexGridReg(yTileMin, yTileMax, cTileMin, cTileMax)
}