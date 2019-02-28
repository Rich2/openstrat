/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames.pPrac
import pGrid._

case class PracTile(x: Int, y: Int, mem: Option[Boolean]) extends Tile

object PracTile
{
  implicit object PracTileIsType extends IsType[PracTile]
  {
    override def isType(obj: AnyRef): Boolean = obj.isInstanceOf[PracTile]
    override def asType(obj: AnyRef): PracTile = obj.asInstanceOf[PracTile]   
  }
}

class PracGrid(xTileMin: Int, xTileMax: Int, yTileMin: Int, yTileMax: Int) extends HexGridSimple[PracTile](xTileMin, xTileMax, yTileMin, yTileMax)
{
  
}