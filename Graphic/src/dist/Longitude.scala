/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import math.Pi

class Longitude private(val degSecs: Double) extends AnyVal with AngleLike
{ override def degs: Double = degSecs.secsToDegs
  def radians: Double = degSecs.secsToRadians

  def addWithin(deltaAngle: Angle, maxLong: Longitude, minLong: Longitude): Longitude = (radians + deltaAngle.radians) match
  { case r if r <= - Pi => Longitude.radians(-Pi)
    case r if r >= Pi => Longitude.radians(Pi)

    case _ if minLong.radians > maxLong.radians => excep("Latitude.addwithin minLat greaterd than maxLat")
    case _ if maxLong.radians < minLong.radians => excep("Latitude.addwithin maxLat less than minLat")
  }
}

object Longitude
{ def degs(degVal: Double) = new Longitude(degVal.degsToSecs)
  def radians(value: Double) = new Longitude(value.radiansToSecs)
}