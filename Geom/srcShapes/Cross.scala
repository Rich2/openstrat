/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** This just a temporary start. */
object Cross
{
  /** Temporary start. */
 def apply(scale: Double = 1, cen: Pt2 = Pt2Z): RArr[LineSegDraw] =
 { val lh = LineSeg(-10 pp 0, 10 pp 0)
   val rh =  LineSeg(0 pp 10, 0 pp -10)
   LineSegArr(lh, rh)map(_.scale(scale).slate(cen).draw(lineWidth = 2))
 }

  /** Diagonal cross with a width and height of 1.  */
  def diag: LineSegArr = LineSegArr(LineSeg(-0.5, -0.5, 0.5, 0.5), LineSeg(-0.5, 0.5, 0.5, -0.5))
}