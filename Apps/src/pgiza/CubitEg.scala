/* Copyright 2025 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pgiza
import geom.*

trait CubitEgBase extends Any, LengthBased
{ override def toPicometreFactor: Double = ???
  override def toMetresFactor: Double = ???
  override def toKilometresFactor: Double = ???
}

case class CubitEg(cubitsNum: Double) extends AnyVal, LengthNotMetric, CubitEgBase
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
  override def compare(that: Length): Int = ???
}