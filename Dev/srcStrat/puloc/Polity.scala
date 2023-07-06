/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package puloc
import Colour._

/** Historical nation state or other polity such as Kingdom or the Soviet Union. */
trait Polity extends Coloured
{ def name: String
  override def toString: String = name
}

object Prussia extends Polity
{ override val name: String = "Preußen"
  override def colour: Colour = Navy
}

object Deutch extends Polity
{ override def name: String = "Deutsch"
  override def colour: Colour = Gray
}
object France extends Polity
{ override def name: String = "France"
  override def colour: Colour = LightBlue
}