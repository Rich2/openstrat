 /* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package p1783
import pEarth._

case class NTileAncient(x: Int, y: Int, terr: WTile) extends ETileAncient
{ type FromT = WTile
  def fromT = terr
  var lunits: RArr[Corps] = RArr()
}

object NTileAncient
{
  implicit object NTileIsType extends IsType[NTileAncient]
  { override def isType(obj: AnyRef): Boolean = obj.isInstanceOf[NTileAncient]
    override def asType(obj: AnyRef): NTileAncient = obj.asInstanceOf[NTileAncient]
  }
}

case class Corps(tile: NTileAncient, polity: Polity)
{ val colour = polity.colour
  override def toString = "Corps" + (polity.toString).enParenth
}