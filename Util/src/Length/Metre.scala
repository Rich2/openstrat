/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat;
import math._

/** Length in metres. */
final class Metre(val metres: Double) extends AnyVal with Length with Ordered[Metre]
{ def typeStr: String = "Dist"
  //def str = persistD1(metres)
  override def +(operand: Length): Metre = Metre(metres + operand.metres)
  override def -(operand: Length): Metre = Metre(metres - operand.metres)
  override def unary_- : Metre = Metre(-metres)
  override def *(operand: Double): Metre = Metre(metres * operand)
  override def /(operand: Double): Metre = Metre(metres / operand)
  def max(operand: Metre): Metre = ife(metres > operand.metres, this, operand)
  def min(operand: Metre): Metre = ife(metres < operand.metres, this, operand)
  def kmStr2 = (metres / 1000).str2 + "km"
  override def compare(that: Metre): Int = metres.match3(_ == that.metres, 0, _ > that.metres, 1,-1)

  def pos: Boolean = metres >= 0
  def neg: Boolean = metres < 0

  def toKm: KMetres = new KMetres(metres / 1000)
}

/** Companion object for the [[Metre] class. */
object Metre
{ def apply(metres: Double): Metre = new Metre(metres)

  implicit class MetreExtensions(thisDist: Metre)
  { def * (operand: Metre): Area = new Area(thisDist.metres * operand.metres)
  }

  //implicit object DistPersist extends PersistDbl1[Metres]("Dist", "metres",_.metres, new Metres(_))
}
