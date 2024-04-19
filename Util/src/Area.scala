/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

trait Area extends Any

trait AreaMetric extends Any with Area with MetricUnits

/** Not sure about this class. */
class MetresSq(val metresSq: Double) extends AnyVal with AreaMetric
{ def + (op: MetresSq): MetresSq = new MetresSq(metresSq + op.metresSq)
  def - (op: MetresSq): MetresSq = new MetresSq(metresSq - op.metresSq)
  def * (operand: Double): MetresSq = new MetresSq(metresSq * operand)
  def / (operand: Double): MetresSq = new MetresSq(metresSq / operand)
}