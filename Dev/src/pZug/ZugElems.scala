/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pZug
import pGrid._

case class Squad(val polity: Polity, val roord: Roord, var action: Action = NoAction)
{ def colour = polity.colour
  override def toString = "Squad" + (roord.ycStr + ", " + action.toString).enParenth
}

trait Polity extends PersistSingleton
{ def colour: Colour
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