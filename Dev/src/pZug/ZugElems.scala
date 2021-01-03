/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package pZug
import geom._, pGrid._

case class Squad(val polity: Polity, val roord: Roord, var action: Action = NoAction)
{ def colour = polity.colour
  override def toString = "Squad" + (roord.ycStr + ", " + action.toString).enParenth
}

object Squad
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

trait Polity extends ShowSingleton
{ override def typeStr: String = "Polity"
  def colour: Colour
}

object Germany extends Polity
{ def str: String = "Germany"

  /** CadetBlue 60% shade. */
  def colour = Colour.fromInts(128, 177, 179)
}

object Britain extends Polity
{ def str: String = "Britain"
  def colour = Colour.fromInts(255, 232, 184)
}

object France extends Polity
{ def str: String = "France"
  def colour = Colour.fromInts(125, 255, 255)
}

sealed trait Action

case class Move(roords: Roords) extends Action
object Move
{ def apply(roords: Roord*): Action = new Move(Roords(roords:_*))
}

case class Fire(roord: Roord) extends Action
{
  override def toString: String = "Fire" + roord.ycStr.enParenth
}
object NoAction extends Action