/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom
import math.{Pi}

/** Base trait for Angle, Latitude and Longitude. Not sure if this is a good idea. */
trait AngleLike extends Any
{ def radians: Double
  @inline def sin: Double = math.sin(radians)
  @inline def cos: Double = math.cos(radians)
  def degs: Double = radians * 180.0 / math.Pi
  def arcLength(radius: Double): Double = radians * radius
  def arcDistance (radiusDist: Dist): Dist = radians * radiusDist
}

/** Angle value class. Its particularly important not to use this class to represent Latitudes as the Angle class has a normal range +- 180 degrees,
 *  while Latitudes have a normal range +- 90 degrees. */
final class Angle private(val radians: Double) extends AnyVal with AngleLike
{ override def toString = degStr2
  def degStr2: String = degs.str2 -"\u00B0"
  def toVec2: Vec2 = Vec2(math.cos(radians), math.sin(radians))
  def radians360: Double = ife(radians < 0, Pi2 - radians, radians)
  def +(other: Angle) = Angle(radians + other.radians)
  
  /** returns an angle between -Pi and Pi */
  def angleTo(other: Angle): Angle = other.radians -radians match
  { case r if r > Pi => Angle(r - Pi2)
    case r if r < -Pi => Angle(Pi2 + r)
    case r => Angle(r)
  }
  
  def addRadians(other: Double) = Angle(radians + other)
  def subRadians(other: Double) = Angle(radians - other)
  def * (factor: Double): Angle = Angle(radians * factor)
  def / (factor: Double): Angle = Angle(radians / factor)
  @ inline def unary_- : Angle = Angle(- radians)
  /** This is gives the smaller of the bisection angles  */
  def bisect(operand: Angle) = Angle(radians + angleTo(operand).radians / 2)
}

/** Angle Companion object. */
object Angle
{ /** Use of recursion is rubbish */
  @inline def apply(radians: Double): Angle = new Angle(reset(radians))
  
  /** Resets radians to between + and - Pi */
  @inline def reset(radians: Double): Double =  radians %% Pi2 match
  { case r if r <= -Pi => Pi2 + r
    case r if r > Pi => r - Pi2
    case r => r
  }
  
  /** Factory method for Angle from number of degrees */
  def deg(degrees: Double): Angle = Angle(degrees * Pi / 180)
}
