/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom; package impunits

/** Quantity of area specified in [[MetricUnits]]. */
trait AreaImperial extends Any with Area with ImperialUnits
{ override def + (operand: Area): AreaImperial
  override def - (operand: Area): AreaImperial
  override def * (operand: Double): AreaImperial
  override def / (operand: Double): AreaImperial
  override def picaresNum: Double = metraresNum * 1e24
  override def millaresNum: Double = metraresNum * 1e6
  override def metraresNum: Double = yardaresNum * 0.836127
  override def hectaresNum: Double = acresNum * 0.404686
  override def kilaresNum: Double = milearesNum * Area.milearesToKilares
}

/** Square yards a measure of [[Area]]. Follows the same naming convention as Hectares. */
class Yardares(val yardaresNum: Double) extends AnyVal with AreaImperial
{ import Yardares.{ fromArea => ysfa }
  override def + (operand: Area): Yardares = new Yardares(yardaresNum + ysfa(operand))
  override def - (operand: Area): Yardares = new Yardares(yardaresNum - ysfa(operand))
  override def * (operand: Double): Yardares = new Yardares(yardaresNum * operand)
  override def / (operand: Double): Yardares = new Yardares(yardaresNum / operand)
  override def acresNum: Double = yardaresNum * 4840
  override def milearesNum: Double = yardaresNum / (1760 * 1760 * 9)
  override def compare(that: Area): Int = if(kilaresNum > that.kilaresNum) 1 else if (kilaresNum < that.kilaresNum) -1 else 0
}

object Yardares
{ /** Factory apply method to construct square yards. */
  def apply(yardaresNum: Double): Yardares = new Yardares(yardaresNum)

  /** Conversion factor from metres to yards. */
  val fromMetreares: Double = 1.19599

  /** Number of square yards in the given area. */
  def fromArea(input: Area): Double = input match
  { case ai: AreaImperial => ai.yardaresNum
    case ar => ar.metraresNum * Yardares.fromMetreares
  }
}

/** Square yards a measure of [[Area]]. Follows the same naming convention as Hectares. */
class Acres(val acresNum: Double) extends AnyVal with AreaImperial
{ import Acres.{ fromArea => afa }
  override def +(operand: Area): Acres = new Acres(acresNum + afa(operand))
  override def -(operand: Area): Acres = new Acres(acresNum - afa(operand))
  override def *(operand: Double): Acres = new Acres(acresNum * operand)
  override def /(operand: Double): Acres = new Acres(acresNum / operand)
  override def yardaresNum: Double = acresNum * 4840
  override def milearesNum: Double = acresNum / 640
  override def compare(that: Area): Int = if(kilaresNum > that.kilaresNum) 1 else if (kilaresNum < that.kilaresNum) -1 else 0
}

object Acres
{ /** Factory apply method to construct square yards. */
  def apply(acresNum: Double): Acres = new Acres(acresNum)

  /** Conversion factor from hectares to acres. */
  val fromHectares: Double = 2.47105

  /** Number of square yards in the given area. */
  def fromArea(input: Area): Double = input match
  { case ai: AreaImperial => ai.acresNum
    case ar => ar.hectaresNum * Acres.fromHectares
  }
}

/** Square miles a measure of [[Area]]. Follows the same naming convention as [[Hectares]]. */
class Mileares(val milearesNum: Double) extends AnyVal with AreaImperial
{ override def + (operand: Area): Mileares = new Mileares(milearesNum + operand.milearesNum)
  override def - (operand: Area): Mileares = new Mileares(milearesNum - operand.milearesNum)
  override def * (operand: Double): Mileares = new Mileares(milearesNum * operand)
  def / (operand: Double): Mileares = new Mileares(milearesNum / operand)
  override def yardaresNum: Double = milearesNum * Mileares.yardsSqNum
  override def acresNum: Double = milearesNum / 640
  override def compare(that: Area): Int = if(kilaresNum > that.kilaresNum) 1 else if (kilaresNum < that.kilaresNum) -1 else 0
}

object Mileares
{ /** Factory apply method to construct square miles. */
  def apply(milesSqNum: Double): Mileares = new Mileares(milesSqNum)

  /** The number of square yards in a square mile  */
  val yardsSqNum: Double = 1760 * 1760
}