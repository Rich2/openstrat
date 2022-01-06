/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pZug
import prid._, phex._

case class Squad(polity: Polity, var action: Action = NoAction) extends Coloured
{ def colour = polity.colour
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

type Action = HSteps | ZugAction

trait ZugAction

case class Fire(hCen: HCen) extends ZugAction
{ override def toString: String = "Fire" + hCen.rcStr
}

object NoAction extends ZugAction