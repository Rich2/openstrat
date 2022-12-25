/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pnap
import Colour._

trait Polity extends ShowSimple
{ override def typeStr: String = "Polity"
  def colour: Colour
}

object Britain extends Polity
{ def str: String = "Britain"
  def colour = Red
}

 object France extends Polity
{ def str: String = "France"
  def colour = Blue
}
