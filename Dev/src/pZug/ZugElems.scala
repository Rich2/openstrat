/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pZug
import prid._, phex._

case class Squad(polity: Polity, var action: ZugAction = NoAction) extends Coloured
{ def colour = polity.colour
}

/** Companion object for Player case class contains implicit instance for Persist. */
object Squad
{ /* Implicit [[ShowT]] instance / evidence for [[Squad]]. */
  implicit val showTEv: Show2T[Polity, Colour, Squad] = Show2T[Polity, Colour, Squad]("Squad", "polity", _.polity, "colour", _.colour)
}

/** A class identifying a Player and a hex coordinate position. */
case class HSquad(hc: HCen, value: Squad) extends HexMemShow[Squad]
{ override def typeStr: String = "HPlayer"
  override def name2: String = "player"
  override implicit def showT2: ShowT[Squad] = Squad.showTEv
  override def syntaxDepth: Int = 2
}

trait Polity extends ShowSimple
{ override def typeStr: String = "Polity"
  def colour: Colour
}

object Polity{
  implicit val showEv: ShowT[Polity] = new ShowT[Polity] {
    override def strT(obj: Polity): String = obj.str
    override def syntaxDepthT(obj: Polity): Int = 1
    override def showDecT(obj: Polity, style: ShowStyle, maxPlaces: Int, minPlaces: Int): String = "Polity"
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

type Action = HSteps | ZugAction

trait ZugAction

case class Fire(hCen: HCen) extends ZugAction
{ override def toString: String = "Fire" + hCen.rcStr
}

case class Move(hCens: HCens) extends ZugAction

object Move
{ def apply(hCens: HCen*): ZugAction = new Move(HCens(hCens:_*))
}

object NoAction extends ZugAction