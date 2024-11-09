/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom; package impunits

/** Quantity of area specified in [[MetricUnits]]. */
trait AreaImperial extends Any with Area with ImperialUnits
{ override def + (operand: Area): AreaImperial
  override def - (operand: Area): AreaImperial
  override def * (operand: Double): AreaImperial
  override def / (operand: Double): AreaImperial

  def yardsSqNum: Double
  def milesSqNum: Double
  override def metresSqNum: Double = ???

  override def kilaresNum: Double = milesSqNum * Area.sqMileToKm
  override def hectaresNum: Double = ???
}

/** Square yards a measure of [[Area]]. Follows the same naming convention as Hectares. */
class Yardare(val yardsSqNum: Double) extends AnyVal with AreaImperial
{ import Yardare.{ fromArea => ysfa }
  override def + (operand: Area): Yardare = new Yardare(yardsSqNum + ysfa(operand))
  override def - (operand: Area): Yardare = new Yardare(yardsSqNum - ysfa(operand))
  override def * (operand: Double): Yardare = new Yardare(yardsSqNum * operand)
  def / (operand: Double): Yardare = new Yardare(yardsSqNum / operand)
  override def milesSqNum: Double = yardsSqNum / (1760 * 1760 * 9)
}

object Yardare
{
  def apply(squareYardsNum: Double): Yardare = new Yardare(squareYardsNum)

  /** Conversion factor from metres to yards. */
  val fromMetres: Double = 1.19599

  /** Number of square yards in the given area. */
  def fromArea(input: Area): Double = input match
  { case ai: AreaImperial => ai.yardsSqNum
    case ar => ar.metresSqNum * Yardare.fromMetres
  }
}

/** Square miles a measure of [[Area]]. Follows the same naming convention as Hectares. */
class Mileare(val milesSqNum: Double) extends AnyVal with AreaImperial
{ override def + (operand: Area): Mileare = new Mileare(milesSqNum + operand.milesSqNum)
  override def - (operand: Area): Mileare = new Mileare(milesSqNum - operand.milesSqNum)
  override def * (operand: Double): Mileare = new Mileare(milesSqNum * operand)
  def / (operand: Double): Mileare = new Mileare(milesSqNum / operand)
  override def yardsSqNum: Double = milesSqNum * Mileare.yardsSqNum
}
object Mileare
{
  def apply(milesSqNum: Double): Mileare = new Mileare(milesSqNum)

  /** The number of square yards in a square mile  */
  val yardsSqNum: Double = 1760 * 1760
}