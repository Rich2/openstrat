/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWW2
import pEarth._

class W2TileAncient(val x: Int, val y: Int, val terr: WTile) extends ETileAncient
{
  type FromT = WTile
  def fromT: WTile = terr
  var lunits: Arr[Army] = Arr()
  override def toString: String = W2TileAncient.persistImplicit.strT(this)
}

object W2TileAncient
{
  def apply(x: Int, y: Int, terr: WTile) = new W2TileAncient(x, y, terr)
  
  implicit object W2TileIsType extends IsType[W2TileAncient]
  { override def isType(obj: AnyRef): Boolean = obj.isInstanceOf[W2TileAncient]
    override def asType(obj: AnyRef): W2TileAncient = obj.asInstanceOf[W2TileAncient]
  }
  
  implicit val persistImplicit: PersistPrecision[W2TileAncient] = new Persist3[Int, Int, WTile, W2TileAncient]("W2Tile", "x", _.x , "y", _.y, "terr", _.terr, apply)
}