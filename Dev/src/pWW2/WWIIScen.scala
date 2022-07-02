/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWW2
import ostrat.eg80._

/** World War 2 scenario. */
trait WW2Scen
{
  implicit val grid: EGrid80Warm = EGrid80.l0b446
}

case class Army(polity: Polity) extends Coloured
{
  def colour = polity.colour
  override def toString = "Army" + (polity.toString).enParenth

  override def equals(other: Any): Boolean = other match
  { case that: Army => polity == that.polity
    case _ => false
  }
}