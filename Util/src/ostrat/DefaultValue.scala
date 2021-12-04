/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** Type class for default values. */
trait DefaultValue[T] {
  def default: T
}

/** Companion object for the [[DefaultValue]] type class trait, contains instances for common types. */
object DefaultValue
{ implicit val intImplicit: DefaultValue[Int] = new DefaultValue[Int] { override def default: Int = 0 }
  implicit val doubleImplicit: DefaultValue[Double] = new DefaultValue[Double] { override def default: Double = 0 }
  implicit val stringImplicit: DefaultValue[String] = new DefaultValue[String] { override def default: String = "" }
  implicit def listImplicit[A]: DefaultValue[List[A]] = new DefaultValue[List[A]] { override def default: List[A] = Nil }
}