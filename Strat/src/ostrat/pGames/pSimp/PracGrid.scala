/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames.pSimp
import pGrid._

case class SimpTile(x: Int, y: Int, mem: Option[Boolean]) extends Tile

object SimpTile
{
  implicit object SimpTileIsType extends IsType[SimpTile]
  {
    override def isType(obj: AnyRef): Boolean = obj.isInstanceOf[SimpTile]
    override def asType(obj: AnyRef): SimpTile = obj.asInstanceOf[SimpTile]   
  }
}

class SimpGrid(xTileMin: Int, xTileMax: Int, yTileMin: Int, yTileMax: Int) extends HexGridSimple[SimpTile](xTileMin, xTileMax, yTileMin, yTileMax)
{
  
}