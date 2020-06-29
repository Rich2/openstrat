/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import math.Pi

class Latitude (val degs: Double) extends AnyVal with AngleLike
{
  def radians: Double = degs * Pi / 180.0
  
  def addWithin(deltaAngle: Angle, maxLat: Latitude, minLat: Latitude): Latitude = (radians + deltaAngle.radians) match
  { case r if r <= - PiH => Latitude.radians(-PiH)
    case r if r >= PiH => Latitude.radians(PiH)
    case _ if minLat.radians > maxLat.radians => excep("Latitude.addwithin minLat greaterd than maxLat")
    case _ if maxLat.radians < minLat.radians => excep("Latitude.addwithin maxLat less than minLat")
  }
   
   def * (long: Longitude): LatLong = LatLong(radians, long.radians)
   def ll (longDegs: Double): LatLong = LatLong(radians, longDegs.degreesToRadians)
}

object Latitude
{
  def radians(value: Double): Latitude = value match
  { case r if r < -Pi => excep("Latitude with less than - Pi")
    case r if r > Pi => excep("Latitude with greater than Pi")
    case r => new Latitude(r.radiansToDegrees)
  }
  
  def apply(degVal: Double) = Latitude.radians(degVal.degreesToRadians)
}

case class Longitude(val radians: Double) extends AnyVal with AngleLike
{
  override def degs: Double = radians * 180.0 / math.Pi
  
  def addWithin(deltaAngle: Angle, maxLong: Longitude, minLong: Longitude): Longitude = (radians + deltaAngle.radians) match
  { case r if r <= - Pi => Longitude(-Pi)
    case r if r >= Pi => Longitude(Pi)
    case _ if minLong.radians > maxLong.radians => excep("Latitude.addwithin minLat greaterd than maxLat")
    case _ if maxLong.radians < minLong.radians => excep("Latitude.addwithin maxLat less than minLat")
  }
}

object Longitude
{ def deg(degVal: Double) = Longitude(degVal.degreesToRadians)
}

trait LatLongBase
{ def latRadians: Double
  def longRadians: Double
  def latitude: Latitude = Latitude.radians(latRadians)
  def longitude: Longitude = Longitude(longRadians)
  @inline def longDegs: Double = longRadians.radiansToDegrees
  @inline def latDegs: Double = latRadians.radiansToDegrees
  def equatorialRadius: Dist
  def polarRadius: Dist
  override def toString: String = degStr
  def latLetter: String = latRadians.ifNeg("S", "N")
  def longLetter: String = longRadians.ifNeg("W", "E")
  def latDegStr: String = latDegs.abs.str2 + latLetter
  def longDegStr: String = longDegs.abs.str2 + longLetter
  def degStr: String = latDegStr.appendCommas(longDegStr)
  
  def latDegMinStr: String =
  { val (degs, mins) = latRadians.abs.toDegsMins
    degs.toString + latLetter + mins.str2Dig
  }
  
  def longDegMinStr: String =
  { val (degs, mins) = longRadians.abs.toDegsMins
    degs.toString + longLetter + mins.str2Dig
  }
   
  def degMinStr: String = latDegMinStr.appendCommas(longDegMinStr)
  def degMinStrs: (String, String) = (latDegMinStr, longDegMinStr)   
  
  def toDist3: Dist3 =
  { val clat = latRadians.cos.abs
    Dist3(longRadians.sin * equatorialRadius * clat, latRadians.sin * polarRadius, longRadians.cos * equatorialRadius * clat)   
  }
}