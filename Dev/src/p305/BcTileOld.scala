/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package p305
import pGrid._, pEarth._

case class BcTileOld(x: Int, y: Int, terr: WTile) extends ETileOld
{ type FromT = WTile
  def fromT = terr
  var lunits: List[Legion] = Nil
}

object BcTileOld
{  
  implicit object NTileIsType extends IsType[BcTileOld]
  { override def isType(obj: AnyRef): Boolean = obj.isInstanceOf[BcTileOld]
    override def asType(obj: AnyRef): BcTileOld = obj.asInstanceOf[BcTileOld]
  }
}

class Legion(val polity: Polity, var cood: Cood)
{ val colour = polity.colour
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