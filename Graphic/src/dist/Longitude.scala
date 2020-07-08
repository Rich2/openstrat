/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import math.Pi

class Longitude(val degs: Double) extends AnyVal with AngleLike
{ override def degSecs: Double = radians * 10000000 / 2 / Pi
  def radians: Double = degs.degreesToRadians

  def addWithin(deltaAngle: Angle, maxLong: Longitude, minLong: Longitude): Longitude = (radians + deltaAngle.radians) match
  { case r if r <= - Pi => Longitude.radians(-Pi)
    case r if r >= Pi => Longitude.radians(Pi)

    case _ if minLong.radians > maxLong.radians => excep("Latitude.addwithin minLat greaterd than maxLat")
    case _ if maxLong.radians < minLong.radians => excep("Latitude.addwithin maxLat less than minLat")
  }
}

object Longitude
{ def deg(degVal: Double) = new Longitude(degVal)
  def radians(value: Double) = new Longitude(value.radiansToDegrees)
}