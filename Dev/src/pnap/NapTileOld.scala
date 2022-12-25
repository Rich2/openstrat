 /* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pnap
import pEarth._, pGrid._

case class NapTileOld(x: Int, y: Int, terr: WTile) extends ETileAncient
{ type FromT = WTile
  def fromT = terr
  var lunits: RArr[Corps] = RArr()
}

object NapTileOld
{
  implicit object NTileIsType extends IsType[NapTileOld]
  { override def isType(obj: AnyRef): Boolean = obj.isInstanceOf[NapTileOld]
    override def asType(obj: AnyRef): NapTileOld = obj.asInstanceOf[NapTileOld]
  }
}

case class Corps(tile: NapTileOld, polity: Polity)
{ val colour = polity.colour
  override def toString = "Corps" + (polity.toString).enParenth
}