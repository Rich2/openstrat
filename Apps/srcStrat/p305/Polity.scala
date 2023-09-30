/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package p305
import Colour._

trait Polity extends ShowSimpled
{ override def typeStr: String = "Polity"
  def colour: Colour
  def lg(num: Int): Legion = Legion(this, num)
}

object Rome extends Polity
{ def str: String = "Rome"
  def colour = Red
}

object Macedon extends Polity
{ def str: String = "Macedon"
  def colour = Blue
}

object Sparta extends Polity
{ def str: String = "Sparta"
  def colour = Gold
}