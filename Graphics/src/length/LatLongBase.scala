/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** The purpose of this trait is to provide super trait for surface positions on all Spheroids.  */
trait LatLongBase
{ def latSecs: Double
  def longSecs: Double
  def equatorialRadius: Metres
  def polarRadius: Metres
  def equatorialRadiusKm = equatorialRadius
  @inline final def lat: Latitude = Latitude.secs(latSecs)
  @inline final def long: Longitude = Longitude.secs(longSecs)
  @inline final def latDegs: Double = latSecs.secsToDegs
  @inline final def longDegs: Double = longSecs.secsToDegs
  @inline final def latRadians: Double = latSecs.secsToRadians
  @inline final def longRadians: Double = longSecs.secsToRadians

  /** The sine of the longitude, where East is a positive longitude. */
  @inline final def longSine: Double = longRadians.sine

  /** The cosine of the longitude, where East is a positive longitude. */
  @inline final def longCos: Double = longRadians.cos

  /** The sine of the latitude, where North is a positive latitude. */
  @inline final def latSine: Double = latRadians.sine

  /** The cosine of the latitude, where North is a positive latitude. */
  @inline final def latCos: Double = latRadians.cos

  @inline final def latMins: Double = latSecs / 60
  @inline final def longMins: Double = longSecs / 60

  override def toString: String = degStr
  def latLetter: String = ife(latRadians < 0, "S", "N")
  def longLetter: String = ife(longRadians < 0, "W", "E")

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

  /** Converts to Metres3 where 0°N 0°E is the max Z value 90°N is the max Y value, 0°N 90°E is the max X value. */
  def toMetres3: Metres3 =
  { /** This factor reduces the value of X and Z as latitudes move towards the Poles. */
    val clat = latRadians.cos.abs
    Metres3(longSine * equatorialRadius * clat, latSine * polarRadius, longCos * equatorialRadius * clat)
  }
}