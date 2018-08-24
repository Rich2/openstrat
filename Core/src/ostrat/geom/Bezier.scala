/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

trait BezierLike

/** Cubic bezier curve */
class Bezier (val xStart: Double, val yStart: Double, val xC1: Double, val yC1: Double, val xC2: Double, val yC2: Double,
      val xEnd: Double, val yEnd: Double) extends Curve
{  
   def pC1 = Vec2(xC1, yC1)
   def pC2 = Vec2(xC2, yC2)  
}

class BezierDraw (xStart: Double, yStart: Double, xCtl1: Double, yCtl1: Double, xCtl2: Double, yCtl2: Double, xEnd: Double, yEnd: Double,
      val lineWidth: Double, val colour: Colour) extends Bezier(xStart, yStart, xCtl1, yCtl1, xCtl2, yCtl2, xEnd, yEnd) with PaintElem[BezierDraw]
{
   override def fTrans(f: Vec2 => Vec2): BezierDraw = BezierDraw(f(pStart), f(pC1),f(pC2), f(pEnd), lineWidth, colour)
}

object BezierDraw
{
   def apply (start: Vec2, pC1: Vec2, pC2: Vec2, endPt: Vec2, lineWidth: Double, colour: Colour): BezierDraw =
      new BezierDraw(start.x, start.y, pC1.x, pC1.y, pC2.x, pC2.y, endPt.x, endPt.y, lineWidth, colour)
}

case class BezierSeg(xC1: Double, val yC1: Double, val xC2: Double, val yC2: Double, val xEnd: Double, val yEnd: Double)