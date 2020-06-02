 /* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package p1783
import pEarth._

case class NTileOld(x: Int, y: Int, terr: WTile) extends ETileOld
{ type FromT = WTile
  def fromT = terr
  var lunits: Arr[Corps] = Arr()
}

object NTileOld
{
  implicit object NTileIsType extends IsType[NTileOld]
  { override def isType(obj: AnyRef): Boolean = obj.isInstanceOf[NTileOld]
    override def asType(obj: AnyRef): NTileOld = obj.asInstanceOf[NTileOld]
  }
}

case class Corps(tile: NTileOld, polity: Polity)
{ val colour = polity.colour
  override def toString = "Corps" + (polity.toString).enParenth
}