/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import math.{Pi}

/** Base trait for Angle, Latitude and Longitude. Not sure if this is a good idea. */
trait AngleLike extends Any
{ def radians: Double
  @inline def sin: Double = math.sin(radians)
  @inline def cos: Double = math.cos(radians)
  def degs: Double //= radians * 180.0 / math.Pi
  def arcLength(radius: Double): Double = radians * radius
  def arcDistance (radiusDist: Dist): Dist = radians * radiusDist
  /** The angle expressed in 36 millionths of a degree. */
  def degoids: Double
}

/** Angle value class. Its particularly important not to use this class to represent Latitudes as the Angle class has a normal range +- 180 degrees,
 *  while Latitudes have a normal range +- 90 degrees. */
final class Angle(val degs: Double) extends AnyVal with AngleLike
{ override def degoids: Double = radians * 10000000 / 2 / Pi
  override def radians: Double = degs * Pi / 180
  override def toString = degStr2
  def degStr2: String = degs.str2 + "\u00B0"

  /** Creates a Vec2 from this Angle and the magnitude parameter. */
  def toVec2(magnitude: Double): Vec2 = Vec2(math.cos(radians) * magnitude, math.sin(radians) * magnitude)

  /** This method needs changing. */
  def radians360: Double = ife(radians < 0, Pi2 - radians, radians)

  def +(other: Angle) = Angle.radians(radians + other.radians)
  def -(other: Angle) = Angle.radians(radians - other.radians)
  
  /** returns an angle between -Pi and Pi */
  def angleTo(other: Angle): Angle = other.radians -radians match
  { case r if r > Pi => Angle.radians(r - Pi2)
    case r if r < -Pi => Angle.radians(Pi2 + r)
    case r => Angle.radians(r)
  }
  
  def addRadians(other: Double) = Angle.radians(radians + other)
  def subRadians(other: Double) = Angle.radians(radians - other)
  def * (factor: Double): Angle = Angle.radians(radians * factor)
  def / (factor: Double): Angle = Angle.radians(radians / factor)
  @ inline def unary_- : Angle = Angle.radians(- radians)
  /** This is gives the smaller of the bisection angles  */
  def bisect(operand: Angle) = Angle.radians(radians + angleTo(operand).radians / 2)
}

/** Angle Companion object. */
object Angle
{
  /** Factory method for Angle from number of degrees */
  def apply(degrees: Double): Angle = new Angle(degrees)

  /** Resets radians to between + and - Pi */
  @inline def reset(radians: Double): Double =  radians %% Pi2 match
  { case r if r <= -Pi => Pi2 + r
    case r if r > Pi => r - Pi2
    case r => r
  }

  @inline def radians(radians: Double): Angle = new Angle(reset(radians) * 180 / Pi)
}