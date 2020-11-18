/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** Base trait for Angle, Latitude and Longitude. */
trait AngleLike extends Any
{ /** The value of this angle expressed in degrees. */
  @inline def degs: Double = secs / secsInDeg

  /** The angle expressed in degrees. */
  def secs: Double

  /** The angle expressed in thousandths of a second of a degree. */
  //def milliSecs: Double

  /** The value of the angle expressed in radians. */
  @inline final def radians: Double = secs.secsToRadians

  /** The sine value of this angle. */
  @inline def sin: Double = math.sin(radians)

  /** The cosine value of this angle. */
  @inline def cos: Double = math.cos(radians)
}