/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom; package impunits

/** British Imperial system of measurement units. */
trait ImperialUnits extends Any

trait LengthImperial extends Any, LengthNotMetric, ImperialUnits, LengthImperialBased
{ /** The number of yards in this length */
  def yardsNum: Double

  /** The number of miles in this length */
  def milesNum: Double

  /** The number of MegaMiles in this length */
  def megaMilesNum: Double

  override def +(operand: Length): LengthImperial
  override def -(operand: Length): LengthImperial
  override def *(operand: Double): LengthImperial
  override def toRectArea(operand: Length): AreaImperial
  override def /(operand: Double): LengthImperial
  override def unary_- : LengthImperial
  @inline override def metresNum: Double = yardsNum * Yards.toMetres
}

/** [[Length]] measured in yards. Can be negative. */
final class Yards(val yardsNum: Double) extends AnyVal, LengthImperial, YardBased
{ def typeStr: String = "Yards"
  import Yards.{ numFromLength => nfl }
  override def compare(that: Length): Int = yardsNum.compare(nfl(that))
  override def +(operand: Length): Yards = Yards(yardsNum + nfl(operand))
  override def -(operand: Length): Yards = Yards(yardsNum - nfl(operand))
  override def unary_- : Yards = Yards(-yardsNum)
  override def *(operand: Double): Yards = Yards(yardsNum * operand)
  override def toRectArea(operand: Length): Yardares = Yardares(yardsNum * nfl(operand))
  override def /(operand: Double): Yards = Yards(yardsNum / operand)
  override def divByLength(operand: Length): Double = yardsNum / nfl(operand)
  override def milesNum: Double = yardsNum / Miles.toYards
  override def megaMilesNum: Double = yardsNum / (Miles.toYards * 1000000)
  override def nonNeg: Boolean = yardsNum >= 0
  override def pos: Boolean = yardsNum > 0
  override def neg: Boolean = yardsNum < 0
}

object Yards
{ /** Factory apply method to create [[Yards]] from a [[Double]] */
  def apply(yardsNum: Double): Yards = new Yards(yardsNum)

  /** Converts yards to metres. */
  val toMetres: Double = 0.9144

  /** Converts metres to yards. */
  val fromMetres: Double = 1.09361

  def numFromLength(input: Length): Double = input match
  { case li: LengthImperial => li.yardsNum
    case l => l.metresNum * fromMetres
  }
}

/** Length can be negative. The underlying data is stored in metres. */
final class Miles(val milesNum: Double) extends AnyVal, LengthImperial, MileBased
{ def typeStr: String = "Miles"
  import Miles.{ numFromLength => nfl}
  override def compare(that: Length): Int = milesNum.compare(nfl(that))
  override def +(operand: Length): Miles = Miles(milesNum + nfl(operand))
  override def -(operand: Length): Miles = Miles(milesNum - nfl(operand))
  override def unary_- : Miles = Miles(-milesNum)
  override def *(operand: Double): Miles = Miles(milesNum * operand)
  override def toRectArea(operand: Length): Mileares = Mileares(milesNum * nfl(operand))
  override def /(operand: Double): Miles = Miles(milesNum / operand)
  override def divByLength(operand: Length): Double = milesNum / nfl(operand)
  override def metresNum: Double = milesNum * Yards.toMetres * toMetresFactor
  override def yardsNum: Double = milesNum * Miles.toYards
  override def megaMilesNum: Double = milesNum / 1000000
  override def nonNeg: Boolean = milesNum >= 0
  override def pos: Boolean = milesNum > 0
  override def neg: Boolean = megaMilesNum < 0
}

object Miles
{ /** Factory apply method for creating [[Miles]] [[Length]] instances. */
  def apply(milesNum: Double): Miles = new Miles(milesNum)

  /** The number of yards in a mile. */
  val toYards: Int = 1760

  /** Converts miles to metres. */
  val toMetres: Double = toYards * Yards.toMetres

  val fromKilometres: Double = 0.621371

  def numFromLength(input: Length): Double = input match
  { case li: LengthImperial => li.milesNum
    case l => l.kilometresNum * fromKilometres
  }
}

/** [[Length]] measured in millions of miles. Can be negative. */
final class MegaMiles(val megaMilesNum: Double) extends AnyVal, LengthImperial, MegamileBased
{ def typeStr: String = "Miles"
  import MegaMiles.{ numFromLength => mmfl }
  override def compare(that: Length): Int = megaMilesNum.compare(mmfl(that))
  override def +(operand: Length): MegaMiles = MegaMiles(megaMilesNum + mmfl(operand))
  override def -(operand: Length): MegaMiles = MegaMiles(megaMilesNum - mmfl(operand))
  override def unary_- : MegaMiles = MegaMiles(-megaMilesNum)
  override def *(operand: Double): MegaMiles = MegaMiles(megaMilesNum * operand)
  override def toRectArea(operand: Length): Mileares = Mileares(milesNum * mmfl(operand) * 1000000.squared)
  override def /(operand: Double): MegaMiles = MegaMiles(megaMilesNum / operand)
  override def divByLength(operand: Length): Double = megaMilesNum / mmfl(operand)
  override def metresNum: Double = megaMilesNum * 1000000 * Miles.toYards * Yards.toMetres
  override def yardsNum: Double = megaMilesNum * Miles.toYards * 1000000
  override def milesNum: Double = megaMilesNum * 1000000
  override def nonNeg: Boolean = megaMilesNum >= 0
  override def pos: Boolean = megaMilesNum > 0
  override def neg: Boolean = megaMilesNum < 0
}

object MegaMiles
{ /** Factory apply method for [[MegaMiles]]. */
  def apply(megaMilesNum: Double): MegaMiles = new MegaMiles(megaMilesNum)

  /** The number of [[Gigametres]] in a megamile. */
  val fromGigaMetres: Double = 0.621371

  def numFromLength(input: Length): Double = input match
  { case li: LengthImperial => li.megaMilesNum
    case l => l.gigametresNum * fromGigaMetres
  }
}