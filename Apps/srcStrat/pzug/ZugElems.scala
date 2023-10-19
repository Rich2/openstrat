/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pzug
import prid._, phex._

case class Squad(polity: Polity, var action: ZugAction = NoAction) extends Coloured
{ def colour = polity.colour
}

/** Companion object for Player case class contains implicit instance for Persist. */
object Squad
{ /* Implicit [[ShowT]] instance / evidence for [[Squad]]. */
  implicit val showTEv: Show2[Polity, Colour, Squad] = Show2[Polity, Colour, Squad]("Squad", "polity", _.polity, "colour", _.colour)
}

/** A class identifying a Player and a hex coordinate position. */
case class HSquad(hc: HCen, value: Squad) extends HexMemShow[Squad]
{ override def typeStr: String = "HPlayer"
  override def name2: String = "player"
  override implicit def show2: Show[Squad] = Squad.showTEv
  override def syntaxDepth: Int = 2
}

trait Polity extends TellSimple
{ override def typeStr: String = "Polity"
  def colour: Colour
}

object Polity
{
  implicit val showEv: Show[Polity] = new Show[Polity] {
    override def strT(obj: Polity): String = obj.str
    override def syntaxDepth(obj: Polity): Int = 1
    override def showDec(obj: Polity, style: ShowStyle, maxPlaces: Int, minPlaces: Int): String = "Polity"
    override def typeStr: String = "Polity"
  }
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

type Action = HStepArr | ZugAction

trait ZugAction

case class Fire(hCen: HCen) extends ZugAction
{ override def toString: String = "Fire" + hCen.rcStr
}

case class Move(dirns: HStepArr) extends ZugAction

object Move
{ def apply(dirns: HStep*): ZugAction = new Move(HStepArr(dirns:_*))
}

object NoAction extends ZugAction