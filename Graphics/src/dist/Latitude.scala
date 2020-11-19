/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
//import scala.math.Pi

/** A compile time wrapper class for Latitude. The value is stored in arc seconds. */
final class Latitude private(val milliSecs: Double) extends AnyVal with AngleLike
{
  /*def addWithin(deltaAngle: Angle, maxLat: Latitude, minLat: Latitude): Latitude = (radians + deltaAngle.radians) match
  { case r if r <= - PiH => Latitude.radians(-PiH)
    case r if r >= PiH => Latitude.radians(PiH)
    case _ if minLat.radians > maxLat.radians => excep("Latitude.addwithin minLat greater than maxLat")
    case _ if maxLat.radians < minLat.radians => excep("Latitude.addwithin maxLat less than minLat")
  }*/

  /*def addWithin2(deltaAngle: Angle, maxLat: Latitude, minLat: Latitude): Latitude = (milliSecs + deltaAngle.milliSecs) match
  { case r if r <= - MilliSecsIn90Degs => Latitude.milliSecs(-MilliSecsIn90Degs)
    case r if r >= MilliSecsIn90Degs => Latitude.milliSecs(MilliSecsIn90Degs)
    case _ if minLat.radians > maxLat.radians => excep("Latitude.addwithin minLat greater than maxLat")
    case _ if maxLat.radians < minLat.radians => excep("Latitude.addwithin maxLat less than minLat")
  }*/

  def * (long: Longitude): LatLong = LatLong.milliSecs(milliSecs, long.milliSecs)
  def ll (longDegs: Double): LatLong = LatLong.milliSecs(milliSecs, longDegs.degsToMilliSecs)
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
}