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

  override def kiloMetresSqNum: Double = milesSqNum * Area.sqMileToKm
}

/** Square yards a measure of [[Area]]. */
class YardsSq(val yardsSqNum: Double) extends AnyVal with AreaImperial
{ import YardsSq.{ fromArea => ysfa }
  override def + (operand: Area): YardsSq = new YardsSq(yardsSqNum + ysfa(operand))
  override def - (operand: Area): YardsSq = new YardsSq(yardsSqNum - ysfa(operand))
  override def * (operand: Double): YardsSq = new YardsSq(yardsSqNum * operand)
  def / (operand: Double): YardsSq = new YardsSq(yardsSqNum / operand)
  override def milesSqNum: Double = yardsSqNum / (1760 * 1760 * 9)
}

object YardsSq
{
  def apply(squareYardsNum: Double): YardsSq = new YardsSq(squareYardsNum)

  /** Conversion factor from metres to yards. */
  val fromMetres: Double = 1.19599

  /** Number of square yards in the given area. */
  def fromArea(input: Area): Double = input match
  { case ai: AreaImperial => ai.yardsSqNum
    case ar => ar.metresSqNum * YardsSq.fromMetres
  }
}

/** Square miles a measure of [[Area]]. */
class MilesSq(val milesSqNum: Double) extends AnyVal with AreaImperial
{ override def + (operand: Area): MilesSq = new MilesSq(milesSqNum + operand.milesSqNum)
  override def - (operand: Area): MilesSq = new MilesSq(milesSqNum - operand.milesSqNum)
  override def * (operand: Double): MilesSq = new MilesSq(milesSqNum * operand)
  def / (operand: Double): MilesSq = new MilesSq(milesSqNum / operand)
  override def yardsSqNum: Double = milesSqNum * MilesSq.yardsSqNum
}
object MilesSq
{
  def apply(milesSqNum: Double): MilesSq = new MilesSq(milesSqNum)

  /** The number of square yards in a square mile  */
  val yardsSqNum: Double = 1760 * 1760
}