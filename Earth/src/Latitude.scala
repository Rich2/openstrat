/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom; package pglobe

/** A compile time wrapper class for Latitude. The value is stored in arc seconds. */
final class Latitude private(val milliSecs: Double) extends AnyVal with AngleLike
{ override def typeStr: String = "Latitude"

  /** True if northern latitude or the equator. */
  def northern: Boolean = milliSecs >= 0

  /** True if southern latitude. */
  def southern: Boolean = milliSecs < 0

  override def showDec(style: ShowStyle, maxPlaces: Int, minPlaces: Int): String = style match
  { case ShowTyped => typeStr + degs.showDec(ShowStandard, maxPlaces, 0).enParenth
    case ShowUnderScore => "_"
    case _ => degs.abs.showDec(ShowStandard, maxPlaces, minPlaces) + ife(northern, "N", "S")
  }

  def * (long: Longitude): LatLong = LatLong.milliSecs(milliSecs, long.milliSecs)
  def ll (longDegs: Double): LatLong = LatLong.milliSecs(milliSecs, longDegs.degsToMilliSecs)

  override def canEqual(that: Any): Boolean = that.isInstanceOf[Latitude]

  override def approx(that: Any, precision: AngleVec = precisionDefault): Boolean = that match {
    case th: Latitude => milliSecs =~(th.milliSecs, precision.milliSecs)
    case _ => false
  }
}

/** Companion object for the [[Latitude]] class. */
object Latitude
{
  def radians(value: Double): Latitude = milliSecs(value.radiansToMilliSecs)

  def apply(degVal: Double): Latitude = secs(degVal.degsToSecs)

  def secs(input: Double): Latitude = milliSecs(input * 1000)

  def milliSecs(input: Double): Latitude = input match
  {
    case i if i >= MilliSecsIn360Degs => milliSecs(input % MilliSecsIn360Degs)
    case i if i <= -MilliSecsIn360Degs => milliSecs(input % MilliSecsIn360Degs)
    case i if i > MilliSecsIn180Degs => milliSecs(-MilliSecsIn360Degs + i)
    case i if i <= - MilliSecsIn180Degs => milliSecs(MilliSecsIn360Degs + i)
    case i if i > MilliSecsIn90Degs => new Latitude((MilliSecsIn180Degs - i))
    case i if i < -MilliSecsIn90Degs => new Latitude((-MilliSecsIn180Degs + i))
    case i => new Latitude(i)
  }

  implicit val showTEv: ShowT[Latitude] = Showeding("Latitude")

  implicit val eqTImplicit: EqT[Latitude] = (a1, a2) => a1.milliSecs == a2.milliSecs
  implicit val approxTImplicit: ApproxAngleT[Latitude] = (a1, a2, precsion) => a1 =~ (a2, precsion)
}

