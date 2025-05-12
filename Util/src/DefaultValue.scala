/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** Type class for default values. */
trait DefaultValue[T]
{ /** The actual value of the default for the [[DefaultValue]] type class. */
  def default: T
}

/** Companion object for the [[DefaultValue]] type class trait, contains instances for common types. */
object DefaultValue
{ given intEv: DefaultValue[Int] = new DefaultValue[Int] { override def default: Int = 0 }
  given doubleEv: DefaultValue[Double] = new DefaultValue[Double] { override def default: Double = 0 }
  given stringEv: DefaultValue[String] = new DefaultValue[String] { override def default: String = "" }
  given listEv[A]: DefaultValue[List[A]] = new DefaultValue[List[A]] { override def default: List[A] = Nil }
}