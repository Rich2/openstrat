/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** Measurement of area. */
trait Area extends Any
{ /** the number of metres square in this area. */
  def metraresNum: Double

  /** the number of kilometres square in this area. kilares follows the same naming convention as hectares. */
  def kilaresNum: Double

  /** the number of hectares, kilometres square in this area. */
  def hectaresNum: Double

  /** the number of square yards. This follows the same naming convention as [[Hectares]]. */
  def yardaresNum: Double

  /** the number of acres, kilometres square in this area. */
  def acresNum: Double

  /** the number of square miles in this area. This follows the same naming convention as [[Hectares]] */
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