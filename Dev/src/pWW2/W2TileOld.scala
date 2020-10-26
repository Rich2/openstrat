/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pWW2
import pEarth._

class W2TileOld(val x: Int, val y: Int, val terr: WTile) extends ETileOld
{
  type FromT = WTile
  def fromT: WTile = terr
  var lunits: Arr[Army] = Arr()
  override def toString: String = W2TileOld.persistImplicit.show(this, 0)
}

object W2TileOld
{
  def apply(x: Int, y: Int, terr: WTile) = new W2TileOld(x, y, terr)
  
  implicit object W2TileIsType extends IsType[W2TileOld]
  { override def isType(obj: AnyRef): Boolean = obj.isInstanceOf[W2TileOld]
    override def asType(obj: AnyRef): W2TileOld = obj.asInstanceOf[W2TileOld]
  }
  
  implicit val persistImplicit: PersistEq[W2TileOld] = new Persist3[Int, Int, WTile, W2TileOld]("W2Tile", "x", _.x , "y", _.y, "terr", _.terr, apply)
}

