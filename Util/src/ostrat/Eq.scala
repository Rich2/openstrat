/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

trait Eq2[A1, A2] extends Equals with Prod2[A1, A2]{
  def elemsEquals(that: Any): Boolean =
    that match {
      case that: Prod2[_, _] => el1 == that.el1 && el2 == that.el2
      case _ => false
    }
}
