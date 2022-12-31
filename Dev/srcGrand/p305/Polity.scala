/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package p305
import geom._, Colour._

trait Polity extends ShowSimple
{ override def typeStr: String = "Polity"
  def colour: Colour
}

object Rome extends Polity
{ def str: String = "Rome"
  def colour = Red
}

object Macedon extends Polity
{ def str: String = "Macedon"
  def colour = Blue
}