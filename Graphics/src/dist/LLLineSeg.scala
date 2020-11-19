/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** A 2 dimensional line segment defined in units of latitude and longitude rather than scalars in X and Y. A line on the service of the earth. */
case class LLLineSeg private(latStartSecs: Double, longStartSecs: Double, lat2Secs: Double, long2Secs: Double)
{ def llStart = LatLong.secs(latStartSecs, longStartSecs)
  def latLong2 = LatLong.secs(lat2Secs, long2Secs)
}

/** Companion object for the [[LLLineSeg]] class. */
object LLLineSeg
{ def apply(llStart: LatLong, ll2: LatLong): LLLineSeg = new LLLineSeg(llStart.latSecs, llStart.longSecs, ll2.latSecs, ll2.longSecs)
}