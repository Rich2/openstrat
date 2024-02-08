/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pind
import Colour._

trait Polity extends TellSimple
{ override def typeStr: String = "Polity"
  def colour: Colour
}

object Britain extends Polity
{ def str: String = "Britain"
  def colour = Red
}

 object France extends Polity
{ def str: String = "France"
  def colour = Colour.fromInts(0, 38, 84)
}
