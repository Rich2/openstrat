/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** Base trait for Angle, Latitude and Longitude. Not sure if this is a good idea. */
trait AngleLike extends Any
{ /** The value of this angle expressed in degrees. */
  def degs: Double

  /** The angle expressed in 36 millionths of a degree. */
  def degSecs: Double

  /** The value of the angle expressd in radians. */
  def radians: Double

  /** The sine value of this angle. */
  @inline def sin: Double = math.sin(radians)

  /** The cosine value of this angle. */
  @inline def cos: Double = math.cos(radians)

  def arcDistance (radiusDist: Dist): Dist = radians * radiusDist
}