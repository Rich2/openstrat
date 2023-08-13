/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom; package pglobe

/** A [[Latitude]] and [[Longitude]] class with a binary north / south direction. */
class LatLongDirn(val latMilliSecs: Double, val longMilliSecs: Double, dirn: Boolean) extends LatLongBase {

  /** Add the delta parameter to the longitude. */
  def addLongMilliSeca(delta: Double): LatLongDirn = {
    val long1 = longMilliSecs + delta
    val long2 = long1 %+- MilliSecsIn180Degs
    LatLongDirn.milliSecs(latMilliSecs, long2, dirn)
  }

  def addLongDegs(degsDelta: Double): LatLongDirn = addLongVec(degsDelta.degsVec)

  def subLongDegs(degsDelta: Double): LatLongDirn = addLongDegs(-degsDelta)

  /** Add the [[AngleVec]] delta parameter to the longitude. */
  def addLongVec(delta: AngleVec): LatLongDirn = addLongMilliSeca(delta.milliSecs)
}

object LatLongDirn{
  /** Factory method for [[LatLong]], creates LatLong from the [[Double]] values for the Latitude and Longitude in thousands of an arc second of a
   * degree, where southern and western values are negative. */
  def milliSecs(lat: Double, long: Double, dirn: Boolean): LatLongDirn = {
    val lat1 = lat %+- MilliSecsIn180Degs
    val lat2 = lat1 match {
      case l if l > MilliSecsIn90Degs => MilliSecsIn180Degs - l
      case l if l < -MilliSecsIn90Degs => -MilliSecsIn180Degs - l
      case l => l
    }
    val long1 = long %+- MilliSecsIn180Degs
    val long2 = long1 match {
      case lo if lo >= 0 & (lat1 > MilliSecsIn90Degs | lat1 < -MilliSecsIn90Degs) => -MilliSecsIn180Degs + lo
      case lo if lo < 0 & (lat1 > MilliSecsIn90Degs | lat1 < -MilliSecsIn90Degs) => MilliSecsIn180Degs + lo
      case lo => lo
    }
    new LatLongDirn(lat2, long2, dirn)
  }
}
