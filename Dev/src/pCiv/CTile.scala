/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pCiv
import pEarth._, pGrid._

case class CTile(x: Int, y: Int, terr: WTile) extends Tile
{
  type FromT = WTile
  def fromT = terr
  def colour = terr.colour
  var settlement: Boolean = false
  var lunits: Refs[Warrior] = Refs()
}

object CTile
{
  implicit val tileMaker: (Int, Int, WTile) => CTile = apply

  implicit object CTileIsType extends IsType[CTile]
  { override def isType(obj: AnyRef): Boolean = obj.isInstanceOf[CTile]
    override def asType(obj: AnyRef): CTile = obj.asInstanceOf[CTile]
  }
}
