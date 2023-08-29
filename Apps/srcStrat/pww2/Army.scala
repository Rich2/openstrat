/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pww2
import pStrat._

trait Lunit extends Coloured
{
  def polity: Polity
  def level: LunitLevel
  def levelStr: String
  override def colour = polity.colour

  override def toString: String = levelStr + polity.toString.enParenth
}

case class Army(polity: Polity, num: Int) extends Lunit
{

  override def level: LunitLevel = FieldArmy

  override def levelStr: String = "Army"


  /*override def equals(other: Any): Boolean = other match
  { case that: Army => polity == that.polity
    case _ => false
  }*/
}