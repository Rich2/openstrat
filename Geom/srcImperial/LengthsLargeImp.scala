/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom; package impunits
import ImperialLength._

/** [[Length]] measured in yards. Can be negative. */
final class Yards(val yardsNum: Double) extends AnyVal with ImperialLength
{ def typeStr: String = "Yards"

  override def compare(that: Length): Int = yardsNum.compare(that.yardsNum)
  override def +(operand: ImperialLength): Yards = Yards(yardsNum + operand.yardsNum)
  override def addLength(operand: Length): Yards = Yards(yardsNum + operand.yardsNum)
  override def -(operand: ImperialLength): Yards = Yards(yardsNum - operand.yardsNum)
  override def unary_- : Yards = Yards(-yardsNum)

  override def metresNum: Double = yardsNum * yardsToMetres
  override def milesNum: Double = yardsNum / yardsInMile
  override def megaMilesNum: Double = yardsNum / (yardsInMile * 1000000)
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

  override def metresNum: Double = milesNum * yardsToMetres/ yardsInMile
  override def yardsNum: Double = milesNum * yardsInMile
  override def megaMilesNum: Double = milesNum / 1000000
}

object Miles
{
  def apply(milesNum: Double): Miles = new Miles(milesNum)
}

/** Length can be negative. The underlying data is stored in metres. */
final class MegaMiles(val megaMilesNum: Double) extends AnyVal with ImperialLength
{ def typeStr: String = "Miles"
  override def compare(that: Length): Int = kMetresNum.compare(that.kMetresNum)
  override def +(operand: ImperialLength): MegaMiles = MegaMiles(megaMilesNum + operand.megaMilesNum)
  override def addLength(operand: Length): Miles = Miles(megaMilesNum + operand.milesNum)
  override def -(operand: ImperialLength): MegaMiles = MegaMiles(megaMilesNum - operand.megaMilesNum)
  override def unary_- : MegaMiles = MegaMiles(-megaMilesNum)

  override def metresNum: Double = milesNum * yardsToMetres/ yardsInMile
  override def yardsNum: Double = milesNum * yardsInMile
  override def milesNum: Double = megaMilesNum / 1000000
}

object MegaMiles
{
  def apply(megaMilesNum: Double): MegaMiles = new MegaMiles(megaMilesNum)
}