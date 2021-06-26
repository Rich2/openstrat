/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat;
import math._

/** Length in miles. */
final class Mile(val miles: Double) extends AnyVal with Length with Ordered[Mile]
{ def typeStr: String = "Dist"
  //def str = persistD1(miles)
  override def metres: Double = ???
  override def +(operand: Length): Mile = ???//Metre(miles + operand.miles)
  override def -(operand: Length): Mile = ??? //Metre(miles - operand.miles)
  override def unary_- : Mile = Mile(-miles)
  override def *(operand: Double): Mile = Mile(miles * operand)
  override def /(operand: Double): Metre = Metre(miles / operand)
  //def max(operand: Metre): Metre = ife(miles > operand.miles, this, operand)
  //def min(operand: Metre): Metre = ife(miles < operand.miles, this, operand)
  //def kmStr2 = (miles / 1000).str2 + "km"
  override def compare(that: Mile): Int = ??? //miles.match3(_ == that.miles, 0, _ > that.miles, 1,-1)

  def pos: Boolean = miles >= 0
  def neg: Boolean = miles < 0

  //def toKm: Kmiles = new KMetres(metres / 1000)
}

/** Companion object for the [[Metre] class. */
object Mile
{ def apply(miles: Double): Mile = new Mile(miles)

  /*implicit class MetreExtensions(thisDist: Metre)
  { def * (operand: Metre): Area = new Area(thisDist.miles * operand.miles)
  }*/

  //implicit object DistPersist extends PersistDbl1[miles]("Dist", "miles",_.miles, new miles(_))
}
