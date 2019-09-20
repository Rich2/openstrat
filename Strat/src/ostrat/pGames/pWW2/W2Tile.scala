/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames
package pWW2
import pEarth._

class W2Tile(val x: Int, val y: Int, val terr: WTile) extends ETile
{
  type FromT = WTile
  def fromT: WTile = terr
  var lunits: Arr[Army] = Arr()
  override def toString: String = W2Tile.persistImplicit.show(this)
}

object W2Tile
{
  def apply(x: Int, y: Int, terr: WTile) = new W2Tile(x, y, terr)
  
  implicit object W2TileIsType extends IsType[W2Tile]
  { override def isType(obj: AnyRef): Boolean = obj.isInstanceOf[W2Tile]
    override def asType(obj: AnyRef): W2Tile = obj.asInstanceOf[W2Tile]
  }
  
  implicit val persistImplicit: PersistEq[W2Tile] = new Persist3[Int, Int, WTile, W2Tile]("W2Tile", "x", _.x , "y", _.y, "terr", _.terr, apply)
}

