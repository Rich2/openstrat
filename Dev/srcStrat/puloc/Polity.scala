/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package puloc
import Colour._

trait Polity extends Coloured
{ def name: String
  override def toString: String = name
}

object Prussia extends Polity
{ override val name: String = "Prussia"
  override def colour: Colour = Colour.Navy
}

object Germany extends Polity
{ override def name: String = "Germany"
  override def colour: Colour = Gray
}
object France extends Polity
{ override def name: String = "France"
  override def colour: Colour = LightBlue
}