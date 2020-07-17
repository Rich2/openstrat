/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** Geometrical Ray. */
class Ray private(x0: Double, y0: Double, angleSecs: Double)
{

}

object Ray
{
  def apply(v0: Vec2, angle: Angle): Ray = new Ray(v0.x, v0.y, angle.secs)
  def apply(x0: Double, y0: Double, angle: Angle): Ray = new Ray(x0, y0, angle.secs)
}