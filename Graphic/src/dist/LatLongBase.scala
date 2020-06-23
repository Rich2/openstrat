/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom
import math.Pi

class Latitude private(val radians: Double) extends AnyVal with AngleLike
{   
   def addWithin(deltaAngle: Angle, maxLat: Latitude, minLat: Latitude): Latitude = (radians + deltaAngle.radians) match
   {
      case r if r <= - PiH => Latitude(-PiH)
      case r if r >= PiH => Latitude(PiH)
      case _ if minLat.radians > maxLat.radians => excep("Latitude.addwithin minLat greaterd than maxLat")
      case _ if maxLat.radians < minLat.radians => excep("Latitude.addwithin maxLat less than minLat")
   }
   
   def * (long: Longitude): LatLong = LatLong(radians, long.radians)
   def ll (longDegs: Double): LatLong = LatLong(radians, longDegs.degreesToRadians)
}
object Latitude
{
   def apply(radians: Double): Latitude = radians match
   {
      case r if r < -Pi => excep("Latitude with less than - Pi")
      case r if r > Pi => excep("Latitude with greater than Pi")
      case r => new Latitude(r)
   }
   def deg(degVal: Double) = Latitude(degVal.degreesToRadians)
}
case class Longitude(val radians: Double) extends AnyVal with AngleLike
{
   def addWithin(deltaAngle: Angle, maxLong: Longitude, minLong: Longitude): Longitude = (radians + deltaAngle.radians) match
   {
      case r if r <= - Pi => Longitude(-Pi)
      case r if r >= Pi => Longitude(Pi)
      case _ if minLong.radians > maxLong.radians => excep("Latitude.addwithin minLat greaterd than maxLat")
      case _ if maxLong.radians < minLong.radians => excep("Latitude.addwithin maxLat less than minLat")
   }
}
object Longitude
{
   def deg(degVal: Double) = Longitude(degVal.degreesToRadians)
}

trait LatLongBase
{
   def lat: Double
   def long: Double
   def latitude: Latitude = Latitude(lat)
   def longitude: Longitude = Longitude(long)
   @inline def longDegs: Double = long.radiansToDegrees
   @inline def latDegs: Double = lat.radiansToDegrees
   def equatorialRadius: Dist
   def polarRadius: Dist
   override def toString: String = degStr
   def latLetter: String = lat.ifNeg("S", "N")
   def longLetter: String = long.ifNeg("W", "E")
   def latDegStr: String = latDegs.abs.str2 + latLetter
   def longDegStr: String = longDegs.abs.str2 + longLetter
   def degStr: String = latDegStr.appendCommas(longDegStr)
   def latDegMinStr: String =
   {
      
      val (degs, mins) = lat.abs.toDegsMins
      degs.toString + latLetter + mins.str2Dig
   }
   def longDegMinStr: String =
   {
      
      val (degs, mins) = long.abs.toDegsMins
      degs.toString + longLetter + mins.str2Dig
   }
   
   def degMinStr: String = latDegMinStr.appendCommas(longDegMinStr)
   def degMinStrs: (String, String) = (latDegMinStr, longDegMinStr)   
   def toDist3: Dist3 =
   {
      val clat = lat.cos.abs
      Dist3(long.sin * equatorialRadius * clat, lat.sin * polarRadius, long.cos * equatorialRadius * clat)   
   }
}