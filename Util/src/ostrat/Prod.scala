/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** Algebraic product type with 2 elements. */
trait Prod2[A1, A2] extends Any
{ /** Element 1 of this Prod2 algebraic product type. */
  def el1: A1

  /** Element 1 of this Prod2 algebraic product type. */
  def el2: A2
}
