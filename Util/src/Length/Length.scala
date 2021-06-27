/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

import scala.math.Ordered

/** A quantity or measurement of length. The final classes include [[Metres]], [[KMetres]]. The purpose of the separate classes is for displaying
 *  values. 100M and 0.1KMetres have the same value but will be displayed differently. */
trait Length extends Any with Ordered[Length]
{ /** Adds the operand length to this length. The return type will be narrowed to the dispatching object's type in the final implementing class. */
  def +(operand: Length): Length

  /** Subtracts the operand length from this length. The return type will be narrowed to the dispatching object's type in the final implementing
   *  class. */
  def -(operand: Length): Length

  /** Negates this length. The return type will be narrowed to the final class's class in the implementing class. */
  def unary_- : Length

  /** Multiplies this length by the operand scalar [[Double]]. The return type will be narrowed to the final class's class in the implementing
   *  class. */
  def *(operand: Double): Length

  /** Divides this length by the operand scalar [[Double]]. The return type will be narrowed to the final class's class in the implementing class. */
  def /(operand: Double): Length

  /** The scalar [[Double]] value of this length expressed in metres. */
  def metres: Double

  /** The value of this length expressed as [[KMetres]]. */
  @inline final def toMetres: Metres = new Metres(metres)

  /** The scalar [[Double]] value of this length expressed in kilometres. */
  def kMetres: Double

  /** The value of this length expressed as [[KMetres]]. */
  @inline final def toKMetres: KMetres = new KMetres(kMetres)

  /** The scalar Double value of this length expressed in miles. */
  def miles: Double

  /** The value of this length expressed as [[Miles]]. */
  @inline final def toMiles: Miles = new Miles(miles)
}

object Length
{
  implicit class LengthExtensions(val thisLength: Length)
  { def / (operand: Length): Double = thisLength.metres / operand.metres
  }
}