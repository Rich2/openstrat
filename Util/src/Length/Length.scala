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

  /** Returns the max [[Length]] of this and the operand value. The return type will be narrowed to the final class's class in the implementing
   *  class. */
  def max(operand: Length): Length

  /** The scalar [[Double]] value of this length expressed in metres. */
  def metresNum: Double

  /** The value of this length expressed as [[Metres]]. */
  @inline final def metres: Metres = new Metres(metresNum)

  /** The scalar [[Double]] value of this length expressed in kilometres. */
  def kMetresNum: Double

  /** The value of this length expressed as [[KMetres]]. */
  @inline final def kMetres: KMetres = new KMetres(kMetresNum)

  /** The scalar [[Double]] value of this length expressed in megametres. */
  def mMetresNum: Double

  /** The value of this length expressed as [[MMetres]] megametres or thousands of kilometres. */
  @inline final def mMetres: MMetres = new MMetres(kMetresNum)

  /** The scalar [[Double]] value of this length expressed in gigametres or millions of kilometres. */
  def gMetresNum: Double

  /** The value of this length expressed as [[GMetres]] gigametres or millions of kilometres. */
  //@inline final def gMetres: GMetres = new GMetres(gMetresNum)

  /** The scalar Double value of this length expressed in miles. */
  def yardsNum: Double

  /** The value of this length expressed as [[Miles]]. */
  @inline final def yards: Yards = new Yards(yardsNum)

  /** The scalar Double value of this length expressed in miles. */
  def milesNum: Double

  /** The value of this length expressed as [[Miles]]. */
  @inline final def miles: Miles = new Miles(milesNum)

  /** The scalar Double value of this length expressed in millions of miles. */
  def mMilesNum: Double

  /** The value of this length expressed as [[Miles]]. */
  @inline final def mMmiles: MMiles = new MMiles(mMilesNum)
}

/** Companion object for [[Length]] trait contains extension class for [[Length]] */
object Length
{
  implicit class LengthExtensions(val thisLength: Length)
  { def / (operand: Length): Double = thisLength.metresNum / operand.metresNum
  }
}