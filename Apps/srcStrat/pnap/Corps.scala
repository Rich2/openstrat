/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pnap

case class Corps(polity: Polity) extends Coloured
{
  def colour = polity.colour
  override def toString = "Corps" + polity.toString.enParenth

  override def equals(other: Any): Boolean = other match
  { case that: Corps => polity == that.polity
    case _ => false
  }
}