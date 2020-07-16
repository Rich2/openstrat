/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import math.Pi

/** The Longitude class is a compile time wrapper around a Double. The longitude value is stored in arc seconds,to allow precise storage of values
 * specified in the old Degrees, Minutes and Seconds system. Decimals of a degree can also be stored precisely. */
final class Longitude private(val secs: Double) extends AnyVal with AngleLike
{
  def addWithin(deltaAngle: Angle, maxLong: Longitude, minLong: Longitude): Longitude = (radians + deltaAngle.radians) match
  { case r if r <= - Pi => Longitude.radians(-Pi)
    case r if r >= Pi => Longitude.radians(Pi)

    case _ if minLong.radians > maxLong.radians => excep("Latitude.addwithin minLat greaterd than maxLat")
    case _ if maxLong.radians < minLong.radians => excep("Latitude.addwithin maxLat less than minLat")
  }
}

/** Companion object of the [[Longitude]] class. */
object Longitude
{ def degs(degVal: Double) = new Longitude(degVal.degsToSecs)
  def radians(value: Double) = new Longitude(value.radiansToSecs)
  def secs(value: Double): Longitude = new Longitude(value)
}