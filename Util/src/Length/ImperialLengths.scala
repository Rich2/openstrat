/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat;
import math._

/** Common trait for metric units of length. */
trait ImperialLength extends Any with Length
{ override def kMetres: Double = 1000
}

/** Length in miles. */
final class Miles(val miles: Double) extends AnyVal with ImperialLength with Ordered[Length]
{ def typeStr: String = "Dist"
  //def str = persistD1(miles)
  override def metres: Double = 1609.34 * miles
  override def +(operand: Length): Miles = Miles(miles + operand.miles)
  override def -(operand: Length): Miles = Miles(miles - operand.miles)
  override def unary_- : Miles = Miles(-miles)
  override def *(operand: Double): Miles = Miles(miles * operand)
  override def /(operand: Double): Metres = Metres(miles / operand)
  //def max(operand: Metre): Metre = ife(miles > operand.miles, this, operand)
  //def min(operand: Metre): Metre = ife(miles < operand.miles, this, operand)
  //def kmStr2 = (miles / 1000).str2 + "km"
  override def compare(that: Length): Int = ??? //miles.match3(_ == that.miles, 0, _ > that.miles, 1,-1)

  def pos: Boolean = miles >= 0
  def neg: Boolean = miles < 0

  //def toKm: Kmiles = new KMetres(metres / 1000)
}

/** Companion object for the [[Metres] class. */
object Miles
{ def apply(miles: Double): Miles = new Miles(miles)

  implicit val summableImplicit: Sumable[Miles] = new Sumable[Miles]{
    override def identity: Miles = Miles(0)

    override def sum(a1: Miles, a2: Miles): Miles = ???
  }
  /*implicit class MetreExtensions(thisDist: Metre)
  { def * (operand: Metre): Area = new Area(thisDist.miles * operand.miles)
  }*/

  //implicit object DistPersist extends PersistDbl1[miles]("Dist", "miles",_.miles, new miles(_))
}
