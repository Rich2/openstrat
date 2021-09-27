/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pZug
import prid._, pGrid._

case class Squad(val polity: Polity, var action: Action = NoAction) extends WithColour
{
  def colour = polity.colour
}

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

class Move(val arrayUnsafe: Array[Int]) extends Action with HCenPathTr
object Move extends HCenPathCompanion[Move]
{ override def fromArray(array: Array[Int]): Move = new Move(array)
}

case class Fire(hCen: HCen) extends Action
{ override def toString: String = "Fire" + hCen.rcStr
}

object NoAction extends Action

sealed trait ActionOld

case class MoveOld(roords: Roords) extends ActionOld

object MoveOld
{ def apply(roords: Roord*): ActionOld = new MoveOld(Roords(roords:_*))
}

case class FireOld(roord: Roord) extends ActionOld
{ override def toString: String = "Fire" + roord.ycStr.enParenth
}

object NoActionOld extends ActionOld