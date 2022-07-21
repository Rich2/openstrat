/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat;package geom; package pglobe

/** The Longitude class is a compile time wrapper around a Double. The longitude value is stored in arc seconds,to allow precise storage of values
 * specified in the old Degrees, Minutes and Seconds system. Decimals of a degree can also be stored precisely. */
final class Longitude private(val milliSecs: Double) extends AnyVal with AngleLike
{ override def typeStr: String = "Longitude"
  /** True if eastern longitude or Greenwich meridian. */
  def eastern: Boolean = milliSecs >= 0

  /** True if western longitude. */
  def western: Boolean = milliSecs < 0

  override def showDec(style: ShowStyle, maxPlaces: Int, minPlaces: Int): String = style match {
    case ShowTyped => typeStr + degs.showDec(ShowStandard, maxPlaces, 0).enParenth
    case _ => degs.abs.showDec(ShowStandard, maxPlaces, minPlaces) + ife(eastern, "E", "W")
  }

  override def canEqual(that: Any): Boolean = that.isInstanceOf[Longitude]

  override def approx(that: Any, precision: AngleVec = precisionDefault): Boolean = that match
  { case th: Longitude => milliSecs =~(th.milliSecs, precision.milliSecs)
    case _ => false
  }

  /** Adds the operand [[Longitude]]. */
  def +(operand: Longitude): Longitude = { Longitude.milliSecs(milliSecs + operand.milliSecs) }

  /** Subtracts the operand [[Longitude]]. */
  def -(operand: Longitude): Longitude = Longitude.milliSecs(milliSecs - operand.milliSecs)
}

/** Companion object of the [[Longitude]] class. */
object Longitude
{
  /** Factory method for creating [[Longitude]] from the value defined in degrees. A positive value creates a western value, a negative value creates
   * an eastern value. Values beyond 180 degrees East and from 180 degrees West loop around. */
  def degs(degsValue: Double): Longitude = milliSecs(degsValue.degsToMilliSecs)

  /** Factory method for creating [[Longitude]] from the value defined in radians. A positive value creates a western value, a negative value creates
   * an eastern value. Values beyond 180 degrees East and from 180 degrees West loop around. */
  def radians(radiansValue: Double): Longitude = milliSecs(radiansValue.radiansToMilliSecs)

  /** Factory method for creating [[Longitude]] from the value defined in seconds of a degree. A positive value creates a western value, a negative
   *  value creates an eastern value. Values beyond 180 degrees East and from 180 degrees West loop around. */
  def secs(secondsValue: Double): Longitude = milliSecs(secondsValue * 1000)

  /** Factory method for creating [[Longitude]] from the value defined in one thousands of a second of a degree. A positive value creates a western
   *  value, a negative value creates an eastern value. Values beyond 180 degrees East and from 180 degrees West loop around. */
  def milliSecs(milliSecondsValue: Double): Longitude =
  { val millis = milliSecondsValue % MilliSecsIn360Degs match{
      case m0 if m0 > MilliSecsIn180Degs => - MilliSecsIn360Degs + m0
      case m0 if m0 <= -MilliSecsIn180Degs => MilliSecsIn360Degs + m0
      case m0 => m0
    }
    new Longitude(millis)
  }

  implicit val showTEv: ShowT[Longitude] = ShowShowT("Longitude")
  implicit val eqTImplicit: EqT[Longitude] = (a1, a2) => a1.milliSecs == a2.milliSecs
  implicit val approxTImplicit: ApproxAngleT[Longitude] = (a1, a2, precsion) => a1 =~ (a2, precsion)
  implicit val defaultValueImplicit: DefaultValue[Longitude] = new DefaultValue[Longitude] { override val default: Longitude = new Longitude(0)}
}