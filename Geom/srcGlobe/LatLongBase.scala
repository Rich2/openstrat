/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom; package pglobe

/** The purpose of this trait is to provide super trait for surface positions on all Spheroids.  */
trait LatLongBase
{ def latSecs: Double
  def longSecs: Double
  def latMilliSecs: Double
  def longMilliSecs: Double
  def equatorialRadius: Metres
  def polarRadius: Metres
  def equatorialRadiusKm = equatorialRadius
  @inline final def lat: Latitude = Latitude.milliSecs(latMilliSecs)
  @inline final def long: Longitude = Longitude.milliSecs(longSecs)
  @inline final def latDegs: Double = latMilliSecs.milliSecsToDegs
  @inline final def longDegs: Double = longMilliSecs.milliSecsToDegs
  @inline final def latRadians: Double = latMilliSecs.milliSecsToRadians
  @inline final def longRadians: Double = longMilliSecs.milliSecsToRadians

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
}