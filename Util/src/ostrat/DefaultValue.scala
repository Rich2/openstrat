/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

trait DefaultValue[T] {
  def default: T
}

object DefaultValue
{ implicit val intImplicit: DefaultValue[Int] = new DefaultValue[Int] { override def default: Int = 0 }
  implicit val doubleImplicit: DefaultValue[Double] = new DefaultValue[Double] { override def default: Double = 0 }
  implicit val stringImplicit: DefaultValue[String] = new DefaultValue[String] { override def default: String = "" }
}