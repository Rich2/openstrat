/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import math._

trait Vec2Like extends Any
{
  def x: Double
  def y: Double

  /** The magnitude of this vector2Like. */
  def magnitude = math.sqrt(x * x + y * y)

  /** Gives the angle of the vector with respect of the origin. */
  def angle: Angle = Angle.radians(angleRadians)



  /** Gives the angle of the vector with respect of the origin in radians. */
  def angleRadians: Double =
  { def at = atan(y / x)
    x match
    { case _ if x == 0 & y == 0 => 0
    case _ if x == 0 & y > 0 => Pi / 2
    case _ if x == 0 => -Pi / 2
    case _ if x > 0 => at
    case _ if y > 0 => Pi + at
    case _ => at - Pi
    }
  }

  /** Gives the angle of the vector with respect of the origin in radians. */
  def angleRadiansPos: Double =
  { val at = atan(y / x)
    x match
    { case x if x < - 0.000000010 && y < 0 => at + Pi
    case x if x < - 0.00000001 => Pi + at
    case x if x > 0.00000001 => at
    case _ if y < 0 => at + Pi/2
    case _ => at + Pi/2
    }
  }

}
