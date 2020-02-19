 /* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package p1783
import pEarth._

case class NTile(x: Int, y: Int, terr: WTile) extends ETile
{
  type FromT = WTile
  def fromT = terr
  var lunits: Refs[Corps] = Refs()
}

object NTile
{
  implicit object NTileIsType extends IsType[NTile]
  { override def isType(obj: AnyRef): Boolean = obj.isInstanceOf[NTile]
    override def asType(obj: AnyRef): NTile = obj.asInstanceOf[NTile]
  }
}

case class Corps(tile: NTile, polity: Polity)
{ val colour = polity.colour
  override def toString = "Corps" + (polity.toString).enParenth
}
