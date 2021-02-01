/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** The Longitude class is a compile time wrapper around a Double. The longitude value is stored in arc seconds,to allow precise storage of values
 * specified in the old Degrees, Minutes and Seconds system. Decimals of a degree can also be stored precisely. */
final class Longitude private(val milliSecs: Double) extends AnyVal with AngleLike
{ override def typeStr: String = "Longitude"

  /** The most basic Show method, paralleling the strT method on ShowT type class instances. */
  override def str: String = ???

  /** Intended to be a multiple parameter comprehensive Show method. Intended to be paralleled by showT method on [[ShowT]] type class instances. */
  override def show(way: Show.Way, decimalPlaces: Int): String = ???

  override def syntaxdepth: Int = 1

  override def canEqual(that: Any): Boolean = that.isInstanceOf[Longitude]

  override def approx(that: Any, precision: AngleVec = precisionDefault): Boolean = that match {
  case th: Longitude => milliSecs =~(th.milliSecs, precision.milliSecs)
  case _ => false
  }
}

/** Companion object of the [[Longitude]] class. */
object Longitude
{ def degs(degVal: Double): Longitude = new Longitude(degVal.degsToMilliSecs)
  def radians(value: Double): Longitude = new Longitude(value.radiansToMilliSecs)
  def secs(value: Double): Longitude = new Longitude(value * 1000)

  implicit val eqTImplicit: EqT[Longitude] = (a1, a2) => a1.milliSecs == a2.milliSecs
  implicit val approxTImplicit: ApproxAngleT[Longitude] = (a1, a2, precsion) => a1 =~ (a2, precsion)
}