/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom; package impunits

/** British Imperial system of measurement units. */
trait ImperialUnits extends Any

trait LengthImperial extends Any with Length with ImperialUnits
{ /** The number of yards in this length */
  def yardsNum: Double

  /** The number of miles in this length */
  def milesNum: Double

  /** The number of MegaMiles in this length */
  def megaMilesNum: Double

  override def +(operand: Length): LengthImperial
  override def -(operand: Length): LengthImperial
  override def *(operand: Double): LengthImperial
  override def /(operand: Double): LengthImperial
  override def unary_- : LengthImperial
  override def kilometresNum: Double = metresNum / 1000
  override def megametresNum: Double = metresNum / 1000000
  override def gigametresNum: Double = metresNum / 1000000000
}

object LengthImperial
{ /** Converts yards to metres. */
  val yardsToMetres: Double = 0.9144

  /** The number of yards in a mile. */
  val yardsInMile: Double = 1760

  /** Converts miles to metres. */
  val milesToMetres: Double = yardsInMile * yardsToMetres
}