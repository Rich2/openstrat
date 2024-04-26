/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** Quantity of area specified in [[MetricUnits]]. */
trait AreaMetric extends Any with Area with MetricUnits
{ override def + (operand: Area): Area
  override def - (operand: Area): AreaMetric
  override def * (operand: Double): AreaMetric
  override def / (operand: Double): AreaMetric
  override def milesSqNum: Double = kiloMetresSqNum * Area.sqKmToMiles
}

/** Square metres  a measure of [[Area]]. */
class MetresSq(val metresSqNum: Double) extends AnyVal with AreaMetric
{ override def + (operand: Area): MetresSq = new MetresSq(metresSqNum + operand.metresSqNum)
  override def - (operand: Area): MetresSq = new MetresSq(metresSqNum - operand.metresSqNum)
  override def * (operand: Double): MetresSq = new MetresSq(metresSqNum * operand)
  def / (operand: Double): MetresSq = new MetresSq(metresSqNum / operand)
  override def kiloMetresSqNum: Double = metresSqNum / 1000000
}

object MetresSq
{
  /** Factory apply method for constructing measurement of areas specified in square metres. */
  def apply(metresSqNum: Double): MetresSq = new MetresSq(metresSqNum)
}

/** Square kilometres a measure of [[Area]]. */
class KilometresSq(val kiloMetresSqNum: Double) extends AnyVal with AreaMetric
{ override def +(operand: Area): KilometresSq = KilometresSq(kiloMetresSqNum + operand.kiloMetresSqNum)
  override def -(operand: Area): KilometresSq = KilometresSq(kiloMetresSqNum - operand.kiloMetresSqNum)
  override def * (operand: Double): KilometresSq = new KilometresSq(kiloMetresSqNum * operand)
  override def / (operand: Double): KilometresSq = new KilometresSq(kiloMetresSqNum / operand)
  override def metresSqNum: Double = kiloMetresSqNum * 1000000
}

object KilometresSq
{ /** Factory apply method for creating units of square kilometres. */
  def apply(kMetresSqNum: Double): KilometresSq = new KilometresSq(kMetresSqNum)
}