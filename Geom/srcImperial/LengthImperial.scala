/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom; package impunits

/** British Imperial system of measurement units. */
trait ImperialUnits extends Any

trait LengthImperial extends Any with Length with ImperialUnits
{ /** The number of yards in this length */
  def yardsNum: Double

  /** The number of miles in this length */
  def milesNum: Double

  /** The number of MegaMiles in this length */
  def megaMilesNum: Double

  override def +(operand: Length): LengthImperial
  override def -(operand: Length): LengthImperial
  override def *(operand: Double): LengthImperial
  override def /(operand: Double): LengthImperial
  override def unary_- : LengthImperial
  override def metresNum: Double = yardsNum * LengthImperial.yardsToMetres
  override def kilometresNum: Double = metresNum / 1000
  override def megametresNum: Double = metresNum / 1000000
  override def gigametresNum: Double = metresNum / 1000000000
}

object LengthImperial
{ /** Converts yards to metres. */
  val yardsToMetres: Double = 0.9144

  /** The number of yards in a mile. */
  val yardsInMile: Double = 1760

  /** Converts miles to metres. */
  val milesToMetres: Double = yardsInMile * yardsToMetres
}

/** [[Length]] measured in yards. Can be negative. */
final class Yards(val yardsNum: Double) extends AnyVal with LengthImperial
{ def typeStr: String = "Yards"
  override def compare(that: Length): Int = yardsNum.compare(that.yardsNum)
  override def +(operand: Length): Yards = Yards(yardsNum + operand.yardsNum)
  override def -(operand: Length): Yards = Yards(yardsNum - operand.yardsNum)
  override def unary_- : Yards = Yards(-yardsNum)
  override def *(operand: Double): Yards = Yards(yardsNum * operand)
  override def /(operand: Double): Yards = Yards(yardsNum / operand)
  override def divByLength(operand: Length): Double = yardsNum / operand.yardsNum
  override def milesNum: Double = yardsNum / LengthImperial.yardsInMile
  override def megaMilesNum: Double = yardsNum / (LengthImperial.yardsInMile * 1000000)
}

object Yards
{
  def apply(yardsNum: Double): Yards = new Yards(yardsNum)
}

/** Length can be negative. The underlying data is stored in metres. */
final class Miles(val milesNum: Double) extends AnyVal with LengthImperial
{ def typeStr: String = "Miles"
  override def compare(that: Length): Int = kilometresNum.compare(that.kilometresNum)
  override def +(operand: Length): Miles = Miles(milesNum + operand.milesNum)
  override def -(operand: Length): Miles = Miles(milesNum - operand.milesNum)
  override def unary_- : Miles = Miles(-milesNum)
  override def *(operand: Double): Miles = Miles(milesNum * operand)
  override def /(operand: Double): Miles = Miles(milesNum / operand)
  override def divByLength(operand: Length): Double = milesNum / operand.milesNum
  override def metresNum: Double = milesNum * LengthImperial.yardsToMetres/ LengthImperial.yardsInMile
  override def yardsNum: Double = milesNum * LengthImperial.yardsInMile
  override def megaMilesNum: Double = milesNum / 1000000
}

object Miles
{ /** Factory apply method for creating [[Miles]] [[Length]] instances. */
  def apply(milesNum: Double): Miles = new Miles(milesNum)
}

/** [[Length]] measured in millions of miles. Can be negative. */
final class MegaMiles(val megaMilesNum: Double) extends AnyVal with LengthImperial
{ def typeStr: String = "Miles"
  override def compare(that: Length): Int = kilometresNum.compare(that.kilometresNum)
  override def +(operand: Length): MegaMiles = MegaMiles(megaMilesNum + operand.megaMilesNum)
  override def -(operand: Length): MegaMiles = MegaMiles(megaMilesNum - operand.megaMilesNum)
  override def unary_- : MegaMiles = MegaMiles(-megaMilesNum)
  override def *(operand: Double): MegaMiles = MegaMiles(megaMilesNum * operand)
  override def /(operand: Double): MegaMiles = MegaMiles(megaMilesNum / operand)
  override def divByLength(operand: Length): Double = megaMilesNum / operand.megaMilesNum
  override def metresNum: Double = megaMilesNum * 1000000 * LengthImperial.yardsInMile * LengthImperial.yardsToMetres
  override def yardsNum: Double = megaMilesNum * LengthImperial.yardsInMile * 1000000
  override def milesNum: Double = megaMilesNum * 1000000
}

object MegaMiles
{
  def apply(megaMilesNum: Double): MegaMiles = new MegaMiles(megaMilesNum)
}