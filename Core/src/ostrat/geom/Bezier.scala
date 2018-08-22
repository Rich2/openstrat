/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

/** Cubic bezier curve */
class Bezier (val xStart: Double, val yStart: Double, val xC1: Double, val yC1: Double, val xC2: Double, val yC2: Double,
      val xEnd: Double, val yEnd: Double)
{
   def start = Vec2(xStart, yStart)
   def c1 = Vec2(xC1, yC1)
   def c2 = Vec2(xC2, yC2)
   def endPt = Vec2(xEnd, yEnd)
}

class BezierDraw (xStart: Double, yStart: Double, xCtl1: Double, yCtl1: Double, xCtl2: Double, yCtl2: Double, xEnd: Double, yEnd: Double,
      val lineWidth: Double, val colour: Colour) extends Bezier(xStart, yStart, xCtl1, yCtl1, xCtl2, yCtl2, xEnd, yEnd) with Transable[BezierDraw]
{
   override def fTrans(f: Vec2 => Vec2): BezierDraw = BezierDraw(f(start), f(c1),f(c2), f(endPt), lineWidth, colour)
}

object BezierDraw
{
   def apply (start: Vec2, c1: Vec2, c2: Vec2, endPt: Vec2, lineWidth: Double, colour: Colour): BezierDraw =
      new BezierDraw(start.x, start.y, c1.x, c1.y, c2.x, c2.y, endPt.x, endPt.y, lineWidth, colour)
}