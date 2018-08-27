/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

trait BezierSegLike extends CurveSegLike
{
   def xC1: Double
   def yC1: Double
   final def pC1: Vec2 = Vec2(xC1, yC1)
   def xC2: Double
   def yC2: Double
   final def pC2: Vec2 = Vec2(xC2, yC2)   
}

/** Cubic bezier curve */
class Bezier (val xStart: Double, val yStart: Double, val xC1: Double, val yC1: Double, val xC2: Double, val yC2: Double,
      val xEnd: Double, val yEnd: Double) extends CurveLike with BezierSegLike

case class BezierDraw (xStart: Double, yStart: Double, xC1: Double, yC1: Double, xC2: Double, yC2: Double, xEnd: Double, yEnd: Double,
      val lineWidth: Double, val colour: Colour) extends PaintElem[BezierDraw] with BezierSegLike with CurveLike
{
   override def fTrans(f: Vec2 => Vec2): BezierDraw = BezierDraw(f(pStart), f(pC1),f(pC2), f(pEnd), lineWidth, colour)
}

object BezierDraw
{
   def apply (start: Vec2, pC1: Vec2, pC2: Vec2, endPt: Vec2, lineWidth: Double, colour: Colour): BezierDraw =
      new BezierDraw(start.x, start.y, pC1.x, pC1.y, pC2.x, pC2.y, endPt.x, endPt.y, lineWidth, colour)
}

case class BezierSeg(xC1: Double, val yC1: Double, val xC2: Double, val yC2: Double, val xEnd: Double, val yEnd: Double) extends CurveSeg with
   BezierSegLike
{
   override def fTrans(f: Vec2 => Vec2) = BezierSeg(f(pC1), f(pC2), f(pEnd))
}
object BezierSeg
{
   def apply(pC1: Vec2, pC2: Vec2, pEnd: Vec2): BezierSeg = new BezierSeg(pC1.x, pC1.y, pC2.x, pC2.y, pEnd.x, pEnd.y)
}