/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package p305
import pGrid._, pEarth._

case class BcTile(x: Int, y: Int, terr: WTile) extends ETile
{
  type FromT = WTile
  def fromT = terr
  var lunits: List[Legion] = Nil
}

object BcTile
{  
  implicit object NTileIsType extends IsType[BcTile]
  { override def isType(obj: AnyRef): Boolean = obj.isInstanceOf[BcTile]
    override def asType(obj: AnyRef): BcTile = obj.asInstanceOf[BcTile]
  }
}

class Legion(val polity: Polity, var cood: Cood)
{
  val colour = polity.colour
  override def toString = "Legions" + (polity.toString).enParenth
  override def equals(other: Any): Boolean = other match
  { case that: Legion => polity == that.polity
    case _ => false
  }
}

object Legion
{
  def apply(polity: Polity, cood: Cood = Cood00): Legion = new Legion(polity, cood)
}