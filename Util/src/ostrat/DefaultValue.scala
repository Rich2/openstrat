/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

trait DefaultValue[T] {
  def default: T
}

object DefaultValue
{ implicit val intImplicit: Int = 0
  implicit val doubleImplicit: Double = 0
  implicit val stringImplicit: String = ""
}