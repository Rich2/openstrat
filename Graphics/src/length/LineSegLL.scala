/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A 2 dimensional line segment defined in units of latitude and longitude rather than scalars in X and Y. A line on the service of the earth. */
final case class LineSegLL private(startSecsLat: Double, startSecsLong: Double, endSecsLat: Double, endSecsLong: Double)
{ def llStart = LatLong.secs(startSecsLat, startSecsLong)
  def latLong2 = LatLong.secs(endSecsLat, endSecsLong)
}

/** Companion object for the [[LineSegLL]] class. */
object LineSegLL
{ def apply(startPt: LatLong, endPt: LatLong): LineSegLL = new LineSegLL(startPt.latSecs, startPt.longSecs, endPt.latSecs, endPt.longSecs)
}