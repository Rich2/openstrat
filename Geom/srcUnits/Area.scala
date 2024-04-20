/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** Measurement of area. */
trait Area extends Any
{ /** the number of metres square in this area. */
  def metresSqNum: Double

  /** the number of kilometres square in this area. */
  def kMetresSqNum: Double
}

/** Quantity of area specified in [[MetricUnits]]. */
trait AreaMetric extends Any with Area with MetricUnits
{ /** Add an [[Area]] defined in [[MetricLength]] units. Use addArea method if you wish to mix units from different unit measurement systems. */
  def + (op: AreaMetric): AreaMetric

  /** Subtract an [[Area]] defined in [[MetricLength]] units. Use subArea method if you wish to mix units from different unit measurement systems. */
  def - (op: AreaMetric): AreaMetric
}

/** Square metres  a measure of [[Area]]. */
class MetresSq(val metresSqNum: Double) extends AnyVal with AreaMetric
{
  override def + (op: AreaMetric): MetresSq = new MetresSq(metresSqNum + op.metresSqNum)
  override def - (op: AreaMetric): MetresSq = new MetresSq(metresSqNum - op.metresSqNum)
  def * (operand: Double): MetresSq = new MetresSq(metresSqNum * operand)
  def / (operand: Double): MetresSq = new MetresSq(metresSqNum / operand)

  override def kMetresSqNum: Double = metresSqNum / 1000000
}

/** Square kilometres a measure of [[Area]]. */
class KiloMetresSq(val kMetresSqNum: Double) extends AnyVal with AreaMetric
{ override def +(op: AreaMetric): KiloMetresSq = KiloMetresSq(kMetresSqNum + op.kMetresSqNum)
  override def -(op: AreaMetric): KiloMetresSq = KiloMetresSq(kMetresSqNum - op.kMetresSqNum)
  override def metresSqNum: Double = kMetresSqNum * 1000000
}

object KiloMetresSq
{ /** Factory apply method for creating units of square kilometres. */
  def apply(kMetresSqNum: Double): KiloMetresSq = new KiloMetresSq(kMetresSqNum)
}