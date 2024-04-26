/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom; package impunits

/** Quantity of area specified in [[MetricUnits]]. */
trait AreaImperial extends Any with Area with ImperialUnits
{ override def + (operand: Area): AreaImperial
  override def - (operand: Area): AreaImperial
  override def * (operand: Double): AreaImperial
  override def / (operand: Double): AreaImperial

  /** the number of metres square in this area. */
  override def metresSqNum: Double = ???

  override def kiloMetresSqNum: Double = milesSqNum * Area.sqMileToKm
}



/** Square miles a measure of [[Area]]. */
class MilesSq(val milesSqNum: Double) extends AnyVal with AreaImperial
{ override def + (operand: Area): MilesSq = new MilesSq(milesSqNum + operand.milesSqNum)
  override def - (operand: Area): MilesSq = new MilesSq(milesSqNum - operand.milesSqNum)
  override def * (operand: Double): MilesSq = new MilesSq(milesSqNum * operand)
  def / (operand: Double): MilesSq = new MilesSq(milesSqNum / operand)

  //override def kiloMilesSqNum: Double = milesSqNum / 1000000
}