/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat;package geom; package pglobe

/** The Longitude class is a compile time wrapper around a Double. The longitude value is stored in arc seconds,to allow precise storage of values
 * specified in the old Degrees, Minutes and Seconds system. Decimals of a degree can also be stored precisely. */
final class Longitude private(val milliSecs: Double) extends AnyVal with AngleLike
{ override def typeStr: String = "Longitude"
  /** True if eastern longitude or Greenwich meridian. */
  def eastern: Boolean = milliSecs >= 0

  /** True if western longitude. */
  def western: Boolean = milliSecs < 0

  override def show(way: Show.Way, maxPlaces: Int, minPlaces: Int): String = way match {
    case Show.Typed => typeStr + degs.show(Show.Standard, maxPlaces, 0).enParenth
    case _ => degs.abs.show(Show.Standard, maxPlaces, 0) + ife(eastern, "E", "W")
  }

  override def canEqual(that: Any): Boolean = that.isInstanceOf[Longitude]

  override def approx(that: Any, precision: AngleVec = precisionDefault): Boolean = that match {
    case th: Longitude => milliSecs =~(th.milliSecs, precision.milliSecs)
    case _ => false
  }

  def +(operand: Longitude): Longitude = { Longitude.milliSecs(milliSecs + operand.milliSecs) }
  def -(operand: Longitude): Longitude = Longitude.milliSecs(milliSecs - operand.milliSecs)
}

/** Companion object of the [[Longitude]] class. */
object Longitude
{ def degs(degVal: Double): Longitude = new Longitude(degVal.degsToMilliSecs)
  def radians(value: Double): Longitude = new Longitude(value.radiansToMilliSecs)
  def secs(value: Double): Longitude = new Longitude(value * 1000)
  def milliSecs(milliSecsNum: Double): Longitude = new Longitude(milliSecsNum)

  implicit val eqTImplicit: EqT[Longitude] = (a1, a2) => a1.milliSecs == a2.milliSecs
  implicit val approxTImplicit: ApproxAngleT[Longitude] = (a1, a2, precsion) => a1 =~ (a2, precsion)
}