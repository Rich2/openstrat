/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pww1
import pStrat._

/** Unit Category */
trait UnitCat
{ def str: String
  def counter: UnitCounter
}

/** Land unit. */
trait Lunit extends Coloured
{ def polity: Polity
  def cat: UnitCat
  def num: Int
  def colour: Colour = polity.colour
  def counter: UnitCounter = cat.counter

  override def equals(other: Any): Boolean = other match
  { case that: Lunit => polity == that.polity & num == that.num
    case _ => false
  }
}

case class Army(polity: Polity, num: Int) extends Lunit
{ override def cat: UnitCat = Army
  override def toString: String = "Army".appendParenth(polity.toString -- num.adjective)
}

object Army extends UnitCat
{ override def str: String = "Army"
  override def counter: UnitCounter = InfantryCounter
}

case class CavalryCorps(polity: Polity, num: Int) extends Lunit
{ override def cat: UnitCat = CavalryCorps
  override def toString: String = "Cavalry Corps".appendParenth(polity.toString -- num.adjective)
}

object CavalryCorps extends UnitCat
{ override def str: String = "Cavalry Corps"
  override def counter: UnitCounter = CavalryCounter
}