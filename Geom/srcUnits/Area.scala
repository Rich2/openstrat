/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

trait Area extends Any
{
  def metresSqNum: Double
  def kMetresSqNum: Double
}

trait AreaMetric extends Any with Area with MetricUnits
{
  def + (op: AreaMetric): AreaMetric
}

/** Square metres  a measure of [[Area]]. */
class MetresSq(val metresSqNum: Double) extends AnyVal with AreaMetric
{
  override def + (op: AreaMetric): MetresSq = new MetresSq(metresSqNum + op.metresSqNum)
  def - (op: MetresSq): MetresSq = new MetresSq(metresSqNum - op.metresSqNum)
  def * (operand: Double): MetresSq = new MetresSq(metresSqNum * operand)
  def / (operand: Double): MetresSq = new MetresSq(metresSqNum / operand)

  override def kMetresSqNum: Double = metresSqNum / 1000000
}

/** Square kilometres  a measure of [[Area]]. */
class KMetresSq(val kMetresSqNum: Double) extends AnyVal with AreaMetric
{
  override def +(op: AreaMetric): KMetresSq = KMetresSq(kMetresSqNum = op.kMetresSqNum)

  override def metresSqNum: Double = kMetresSqNum * 1000000
}

object KMetresSq
{
  def apply(kMetresSqNum: Double): KMetresSq = new KMetresSq(kMetresSqNum)
}