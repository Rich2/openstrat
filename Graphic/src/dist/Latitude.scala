/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import scala.math.Pi

final class Latitude private(val degSecs: Double) extends AnyVal with AngleLike
{
  @inline override def degs: Double = degSecs / 3600
  @inline override def radians: Double = degs * Pi / 180.0

  def addWithin(deltaAngle: Angle, maxLat: Latitude, minLat: Latitude): Latitude = (radians + deltaAngle.radians) match
  { case r if r <= - PiH => Latitude.radians(-PiH)
    case r if r >= PiH => Latitude.radians(PiH)
    case _ if minLat.radians > maxLat.radians => excep("Latitude.addwithin minLat greaterd than maxLat")
    case _ if maxLat.radians < minLat.radians => excep("Latitude.addwithin maxLat less than minLat")
  }

  def * (long: Longitude): LatLong = LatLong.degs(degs, long.degs)
  def ll (longDegs: Double): LatLong = LatLong.degs(degs, longDegs)
}

object Latitude
{
  def radians(value: Double): Latitude = value match
  { case r if r < -Pi => excep("Latitude with less than - Pi")
    case r if r > Pi => excep("Latitude with greater than Pi")
    case r => new Latitude(r.radiansToSecs)
  }

  def apply(degVal: Double) = new Latitude(degVal * 3600)

  def secs(input: Double): Latitude = input match
  {
    case i if i >= secsIn360Degs => secs(input % secsIn360Degs)
    case i if i <= -secsIn360Degs => secs(input % secsIn360Degs)
    case _ => ???
  }
}