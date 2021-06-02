/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package p305
import pGrid._, pEarth._

case class BcTileAncient(x: Int, y: Int, terr: WTile) extends ETileAncient
{ type FromT = WTile
  def fromT = terr
  var lunits: List[Legion] = Nil
}

object BcTileAncient
{  
  implicit object NTileIsType extends IsType[BcTileAncient]
  { override def isType(obj: AnyRef): Boolean = obj.isInstanceOf[BcTileAncient]
    override def asType(obj: AnyRef): BcTileAncient = obj.asInstanceOf[BcTileAncient]
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
{ def apply(polity: Polity, cood: Cood = Cood00): Legion = new Legion(polity, cood)
}