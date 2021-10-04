/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pZug
import pGrid._

case class SquadOld(val polity: Polity, val roord: Roord, var action: ActionOld = NoActionOld)
{ def colour = polity.colour
  override def toString = "Squad" + (roord.ycStr + ", " + action.toString).enParenth
}

object SquadOld
{
  def canMove(tile: ZugTerr): Boolean = tile != Lake

  def terrCost(tile: ZugTerr): OptInt = tile match
  { case Lake => NoInt
    case Plain => SomeInt(4)
    case WheatField => SomeInt(6)
    case StoneBuilding => SomeInt(10)
    case Hill => SomeInt(6)
    case _ => SomeInt(4)
  }
}

sealed trait ActionOld

case class MoveOld(roords: Roords) extends ActionOld

object MoveOld
{ def apply(roords: Roord*): ActionOld = new MoveOld(Roords(roords:_*))
}

case class FireOld(roord: Roord) extends ActionOld
{ override def toString: String = "Fire" + roord.ycStr.enParenth
}

object NoActionOld extends ActionOld