/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** Measurement of area. */
trait Area extends Any with Ordered[Area]
{ /** the number of [[Picares]], square picometres in this area. */
  def picaresNum: Double

  /** the number of millimetre² in this area. */
  def millaresNum: Double

  /** the number of metre² in this area. */
  def metraresNum: Double

  /** the number of kilometre² in this area. */
  def kilaresNum: Double

  /** the number of hectares, (100m)² in this area. */
  def hectaresNum: Double

  /** the number of yard² in this area. */
  def yardaresNum: Double

  /** the number of acres, 1 acre = 10chain² = 66feet by 660feet. */
  def acresNum: Double

  /** the number of mile² in this area. */
  def milearesNum: Double

  /** Adds an [[Area]] returning an [[Area]] in the units of this subject. */
  def + (operand: Area): Area

  /** Subtracts an [[Area]] returning an [[Area]] in the units of this subject. */
  def - (operand: Area): Area

  /** Multiply this [[Area]] by a scalar. */
  def * (operand: Double): Area

  /** Divide this [[Area]] by a scalar. */
  def / (operand: Double): Area
}

object Area
{ /** A quantity of km² converted to Mile². */
  def kilaresToMileares: Double = 0.386102

  /** A quantity of mile² converted to km². */
  def milearesToKilares: Double = 2.58999
}