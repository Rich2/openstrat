/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pww2
import pStrat._

/** Military unit. */
trait Munit extends Coloured
{ /** The polity or nation to which this military unit belongs. */
  def polity: Polity
  override def colour = polity.colour
}

trait MunitSt
{
  def identity: Munit
}

trait Lunit extends Munit
{ def level: LunitLevel
  def levelStr: String
  override def toString: String = levelStr + polity.toString.enParenth
}

trait LunitSt extends MunitSt
{
  override def identity: Lunit
}

case class Army(polity: Polity, num: Int) extends Lunit
{
  override def level: LunitLevel = FieldArmy

  override def levelStr: String = "Army"
}

object BrAr8 extends Army(Britain, 8)