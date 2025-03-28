/* Copyright 2025 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pgiza
import geom.*, Colour.*

case class CubitEg(cubitsNum: Double) extends AnyVal, LengthNotMetric
{
  override def metresNum: Double = ???

  override def toRectArea(operand: Length): Area = ???
  override def +(operand: Length): Length = ???
  override def -(operand: Length): Length = ???
  override def unary_- : Length = ???
  override def *(operand: Double): Length = ???

  override def /(operand: Double): Length = ???

  override def divByLength(operand: Length): Double = ???
  override def pos: Boolean = ???
  override def neg: Boolean = ???

  override def nonNeg: Boolean = ???

  override def compare(that: _root_.ostrat.geom.Length): Int = ???
}

object GreatPyramid
{
  def baseLen = 230.3.metres
  def height = 146.6.metres
}