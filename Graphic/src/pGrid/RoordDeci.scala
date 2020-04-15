/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid
import geom._

/** A deci-Roordinate. Divides the line between 2 Roords into graduations of ten. Defines the point a certain number of tenths toward the second Roord. */
case class RoordDeci(y1: Int, c1: Int, y2: Int, c2: Int, tenths: Int)
{
  def r1: Roord = y1 rr c1
  def r2: Roord = y2 rr c2
  def toVec2(f: Roord => Vec2): Vec2 = f(r1) + f((r2 - r1) * tenths) / 10
}

object RoordDeci
{ def apply(r1: Roord, r2: Roord, tenths: Int): RoordDeci = new RoordDeci(r1.y, r1.c, r2.y, r2.c, tenths)
}