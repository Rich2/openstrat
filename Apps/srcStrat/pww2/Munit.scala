/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pww2
import pStrat._

/** Military unit. */
trait Munit extends Coloured
{ /** The polity or nation to which this military unit belongs. */
  def polity: Polity
  override def colour = polity.colour
}

trait MunitSt extends Coloured
{
  def identity: Munit
  override def colour: Colour = identity.colour
}

trait Lunit extends Munit
{ def level: LunitLevel
  def levelStr: String
  override def toString: String = levelStr + polity.toString.enParenth
  def num: Int
}

case class LunitSt(override val identity: Lunit) extends MunitSt
{
  def num: Int = identity.num
}

case class BrArmy(num: Int, polity: Polity = Britain) extends Lunit
{
  override def level: LunitLevel = FieldArmy

  override def levelStr: String = "Army"
}

/** British 8th Army. */
object BrAr8 extends BrArmy(8)

case class PzArmy(num: Int) extends Lunit
{ override def level: LunitLevel = FieldArmy
  override def levelStr: String = "Panzer Armee"
  override def polity: Polity = Germany
}

/** Panzer Armee North Africa. */
object PzAr5 extends PzArmy(5)