/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** Measurement of area. */
trait Area extends Any
{ /** the number of metres square in this area. */
  def metresSqNum: Double

  /** the number of kilometres square in this area. */
  def kiloMetresSqNum: Double

  /** the number of square miles in this area. */
  def milesSqNum: Double

  /** Adds an [[Area]] returning an [[Area]] in the units of the this subject. */
  def + (operand: Area): Area

  /** Subtracts an [[Area]] returning an [[Area]] in the units of the this subject. */
  def - (operand: Area): Area

  /** Multiply this [[Area]] by a scalar. */
  def * (operand: Double): Area

  /** Divide this [[Area]] by a scalar. */
  def / (operand: Double): Area
}

object Area
{
  def sqKmToMiles: Double = 0.386102
}