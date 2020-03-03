/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pCiv
import pEarth._, pGrid._

case class CTileOld(x: Int, y: Int, terr: WTile) extends TileOld
{
  type FromT = WTile
  def fromT = terr
  def colour = terr.colour
  var settlement: Boolean = false
  var lunits: Refs[Warrior] = Refs()
}

object CTileOld
{
  implicit val tileMaker: (Int, Int, WTile) => CTileOld = apply

  implicit object CTileIsType extends IsType[CTileOld]
  { override def isType(obj: AnyRef): Boolean = obj.isInstanceOf[CTileOld]
    override def asType(obj: AnyRef): CTileOld = obj.asInstanceOf[CTileOld]
  }
}
