/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom
import math._

/** Length in metres. */
final class Metres(val metres: Double) extends AnyVal with Length with Ordered[Metres]
{ def typeStr: String = "Dist"
  //def str = persistD1(metres)
  override def +(operand: Length): Metres = Metres(metres + operand.metres)
  override def -(operand: Length): Metres = Metres(metres - operand.metres)
  override def unary_- : Metres = Metres(-metres)
  override def *(operand: Double): Metres = Metres(metres * operand)
  override def /(operand: Double): Metres = Metres(metres / operand)
  def max(operand: Metres): Metres = ife(metres > operand.metres, this, operand)
  def min(operand: Metres): Metres = ife(metres < operand.metres, this, operand)
  def kmStr2 = (metres / 1000).str2 + "km"
  override def compare(that: Metres): Int = metres.match3(
    _ == that.metres, 0,
    _ > that.metres, 1,
    -1)

  def pos: Boolean = metres >= 0
  def neg: Boolean = metres < 0
}

/** Companion object for the Distance class. */
object Metres
{ def apply(metres: Double): Metres = new Metres(metres)

  implicit class DistImplicit(thisDist: Metres)
  { def * (operand: Metres): Area = new Area(thisDist.metres * operand.metres)
  }

  implicit object DistPersist extends PersistDbl1[Metres]("Dist", "metres",_.metres, new Metres(_))
}
