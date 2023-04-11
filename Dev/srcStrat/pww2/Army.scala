/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pww2

case class Army(polity: Polity, num: Int) extends Coloured
{
  def colour = polity.colour
  override def toString = "Army" + polity.toString.enParenth

  override def equals(other: Any): Boolean = other match
  { case that: Army => polity == that.polity
    case _ => false
  }
}