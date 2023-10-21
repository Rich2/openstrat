/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pCiv
import Colour._

trait Faction extends TellSimple
{ override def typeStr: String = "Faction"
  def colour: Colour
}

object Faction
{ implicit val showEv: ShowTellSimple[Faction] = ShowTellSimple[Faction]("Faction")
}

object Uruk extends Faction
{ def str: String = "Uruk"
  val colour = Red
}

object Eridu extends Faction
{ def str: String = "Eridu"
  val colour = Blue
}