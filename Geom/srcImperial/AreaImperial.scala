/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom; package impunits

/** Quantity of area specified in [[MetricUnits]]. */
trait AreaImperial extends Any with Area with ImperialUnits
{ /** Add an [[Area]] defined in [[LengthMetric]] units. Use addArea method if you wish to mix units from different unit measurement systems. */
  def + (op: AreaMetric): AreaMetric

  /** Subtract an [[Area]] defined in [[LengthMetric]] units. Use subArea method if you wish to mix units from different unit measurement systems. */
  def - (op: AreaMetric): AreaMetric

  override def * (operand: Double): AreaMetric
  override def / (operand: Double): AreaMetric
}