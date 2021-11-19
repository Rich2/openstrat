/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat;
import math._

/** Common trait for metric units of length. */
trait ImperialLength extends Any with Length
{ @inline override def kMetresNum: Double = metresNum / 1000
  @inline override def mMetresNum: Double = metresNum / 1000000
  @inline override def gMetresNum: Double = metresNum / 1000000000
}

/** Length in yards. */
final class Yards(override val yardsNum: Double) extends AnyVal with ImperialLength
{
  override def compare(that: Length): Int = (yardsNum - that.yardsNum).match3(_ < -1, -1, _ == 0, 0, 1)

  /** Adds the operand length to this Yards. Returns the value in Yards. */
  override def +(operand: Length): Yards = new Yards(yardsNum + operand.yardsNum)

  /** Subtracts the operand length from this Yards. Returns the value in Yards. */
  override def -(operand: Length): Yards = new Yards(yardsNum - operand.yardsNum)

  /** Negates this Yards. Returns the value in Yards. */
  override def unary_- : Yards = new Yards(-yardsNum)

  /** Multiplies this Yards by the operand scalar [[Double]]. Returns the value in Yards. */
  override def *(operand: Double): Length = new Yards(yardsNum * operand)

  /** Divides this Yrads by the operand scalar [[Double]]. Returns the value in Yards. */
  override def /(operand: Double): Length = new Yards(yardsNum / operand)

  /** Returns the max length of this and the operand length in [[Yards]]. */
  override def max(operand: Length): Yards = new Yards(yardsNum.max(operand.yardsNum))

  /** The scalar [[Double]] value of this length expressed in metres. */
  @inline override def metresNum: Double = yardsNum * 0.9144

  @inline override def milesNum: Double = yardsNum / 1760
  @inline override def mMilesNum: Double = yardsNum / 1760000000
}

/** Length in miles. */
final class Miles(override val milesNum: Double) extends AnyVal with ImperialLength
{ def typeStr: String = "Miles"
  override def metresNum: Double = 1609.34 * milesNum
  override def +(operand: Length): Miles = Miles(milesNum + operand.milesNum)
  override def -(operand: Length): Miles = Miles(milesNum - operand.milesNum)
  override def unary_- : Miles = Miles(-milesNum)
  override def *(operand: Double): Miles = Miles(milesNum * operand)
  override def /(operand: Double): Miles = Miles(milesNum / operand)

  /** Returns the max length of this and the operand length in [[Miles]]. */
  override def max(operand: Length): Miles = new Miles(milesNum.max(operand.milesNum))

  override def compare(that: Length): Int = (milesNum - that.milesNum).match3(_ < 0, -1,_ == 0,0,1)

  def pos: Boolean = milesNum >= 0
  def neg: Boolean = milesNum < 0
  @inline override def yardsNum: Double = milesNum * 1760
  @inline override def mMilesNum: Double = milesNum / 1000000
}

/** Companion object for the [[Metres] class. */
object Miles
{ def apply(miles: Double): Miles = new Miles(miles)

  implicit val summableImplicit: Sumable[Miles] = new Sumable[Miles]{
    override def identity: Miles = Miles(0)

    override def sum(a1: Miles, a2: Miles): Miles = ???
  }
  /*implicit class MetreExtensions(thisDist: Metre)
  { def * (operand: Metre): Area = new Area(thisDist.miles * operand.miles)
  }*/

  //implicit object DistPersist extends PersistDbl1[miles]("Dist", "miles",_.miles, new miles(_))
}