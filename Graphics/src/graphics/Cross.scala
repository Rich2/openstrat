/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** This just a temporary start. */
object Cross
{
  /** Temporary start. */
 def apply(scale: Double = 1, cen: Pt2 = Vec2Z): Arr[LineSegDraw] =
 { val lh = LineSeg(-10 vv 0, 10 vv 0)
   val rh =  LineSeg(0 vv 10, 0 vv -10)
   LineSegs(lh, rh)map(_.scale(scale).slate(cen).draw(lineWidth = 2))
 }
}