/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom; package impunits

trait ImperialUnits extends Any

trait ImperialLength extends Any with Length with ImperialUnits
{
  def +(operand: ImperialLength): ImperialLength

  def -(operand: ImperialLength): ImperialLength

  def milesNum: Double

  override def unary_- : ImperialLength
  override def kMetresNum: Double = ???

}

/** Length can be negative. The underlying data is stored in metres. */
final class Miles(val milesNum: Double) extends AnyVal with ImperialLength
{ def typeStr: String = "Miles"

  override def compare(that: Length): Int = kMetresNum.compare(that.kMetresNum)

  override def metresNum: Double = ???///kMetresNum * 1000

  override def +(operand: ImperialLength): Miles = Miles(milesNum + operand.milesNum)
  override def -(operand: ImperialLength): Miles = Miles(milesNum - operand.milesNum)
  override def unary_- : Miles = Miles(-milesNum)
}

object Miles
{
  def apply(milesNum: Double): Miles = new Miles(milesNum)
}