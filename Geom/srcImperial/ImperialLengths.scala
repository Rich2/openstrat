/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom; package impunits

/** British Imperial system of measurment units. */
trait ImperialUnits extends Any

trait ImperialLength extends Any with Length with ImperialUnits
{
  /** Add a [[Length]] defined in [[ImperialLength]] units. Use addLength method if you wish to mix units from different unit measurement systems. */
  def +(operand: ImperialLength): ImperialLength

  /** Subtrct a [[Length]] defined in [[ImperialLength]] units. Use addLength method if you wish to mix units from different unit measurement systems. */
  def -(operand: ImperialLength): ImperialLength

  override def addLength(operand: Length): ImperialLength
  override def subLength(operand: Length): ImperialLength

  def yardsNum: Double
  def milesNum: Double
  def megaMilesNum: Double

  override def unary_- : ImperialLength
  override def kiloMetresNum: Double = metresNum / 1000
}

object ImperialLength
{ /** Converts yards to metres. */
  val yardsToMetres: Double = 0.9144

  /** The number of yards in a mile. */
  val yardsInMile: Double = 1760

  /** Converts miles to metres. */
  val milesToMetres: Double = yardsInMile * yardsToMetres
}