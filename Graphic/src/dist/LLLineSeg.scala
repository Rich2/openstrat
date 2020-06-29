/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** A 2 dimensional line segment defined in units of latitude and longitude rather than scalars in X and Y. A line on the service of the earth. */
case class LLLineSeg(latStart: Double, longStart: Double, lat2: Double, long2: Double)
{ def llStart = LatLong(latStart, longStart)
  def latLong2 = LatLong(lat2, long2)
}

/** Companion object for the [[LLLineSeg]] class. */
object LLLineSeg
{ def apply(llStart: LatLong, ll2: LatLong): LLLineSeg = new LLLineSeg(llStart.latRadians, llStart.longRadians, ll2.latRadians, ll2.longRadians)
}