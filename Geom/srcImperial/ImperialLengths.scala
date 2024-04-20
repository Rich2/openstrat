/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom; package impunits

trait ImperialUnits extends Any

trait ImperialLength extends Any with Length with ImperialUnits
{
  def +(operand: ImperialLength): ImperialLength
  override def addLength(operand: Length): ImperialLength
  def -(operand: ImperialLength): ImperialLength

  def yardsNum: Double
  def milesNum: Double

  override def unary_- : ImperialLength
  override def kMetresNum: Double = metresNum / 1000
}

object ImperialLength
{ /** Converts yards to metres. */
  val yardsToMetres: Double = 0.9144

  /** The number of yards in a mile. */
  val yardsInMile: Double = 1760000

  /** Converts miles to metres. */
  val milesToMetres: Double = yardsInMile * yardsToMetres
}

/** [[Length]] measured in yards. Can be negative. */
final class Yards(val yardsNum: Double) extends AnyVal with ImperialLength
{ def typeStr: String = "Yards"

  override def compare(that: Length): Int = yardsNum.compare(that.yardsNum)
  override def +(operand: ImperialLength): Yards = Yards(yardsNum + operand.yardsNum)
  override def addLength(operand: Length): Yards = Yards(yardsNum + operand.yardsNum)
  override def -(operand: ImperialLength): Yards = Yards(yardsNum - operand.yardsNum)
  override def unary_- : Yards = Yards(-yardsNum)

  import ImperialLength._
  override def metresNum: Double = yardsNum * yardsToMetres
  override def milesNum: Double = yardsNum / yardsInMile
}

object Yards
{
  def apply(yardsNum: Double): Yards = new Yards(yardsNum)
}

/** Length can be negative. The underlying data is stored in metres. */
final class Miles(val milesNum: Double) extends AnyVal with ImperialLength
{ def typeStr: String = "Miles"
  override def compare(that: Length): Int = kMetresNum.compare(that.kMetresNum)
  override def +(operand: ImperialLength): Miles = Miles(milesNum + operand.milesNum)

  override def addLength(operand: Length): Miles = Miles(milesNum + operand.milesNum)

  override def -(operand: ImperialLength): Miles = Miles(milesNum - operand.milesNum)
  override def unary_- : Miles = Miles(-milesNum)

  import ImperialLength._
  override def metresNum: Double = milesNum * yardsToMetres/ yardsInMile
  override def yardsNum: Double = milesNum * yardsInMile
}

object Miles
{
  def apply(milesNum: Double): Miles = new Miles(milesNum)
}