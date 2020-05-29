/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** Super trait for a (cubic) Bezier and BezierDraw */
trait BezierLikeOld extends CurveLikeOld
{ def xC1: Double
  def yC1: Double
  final def pC1: Vec2 = Vec2(xC1, yC1)
  def xC2: Double
  def yC2: Double
  final def pC2: Vec2 = Vec2(xC2, yC2)   
}

/** Cubic bezier curve. */
class Bezier (val xStart: Double, val yStart: Double, val xC1: Double, val yC1: Double, val xC2: Double, val yC2: Double,
      val xEnd: Double, val yEnd: Double) extends BezierLikeOld
{ override type AlignT = Bezier
  def typeStr: String = "Bezier"
  def fTrans(f: Vec2 => Vec2): Bezier = Bezier(f(pStart), f(pC1), f(pC2), f(pEnd))

  override def canEqual(that: Any): Boolean = ???

  override def productArity: Int = ???

  override def productElement(n: Int): Any = ???
}

object Bezier
{ def apply(pStart: Vec2, pC1: Vec2, pC2: Vec2, pEnd: Vec2): Bezier = new Bezier(pStart.x, pStart.y, pC1.x, pC1.y, pC2.x, pC2.y, pEnd.x, pEnd.y)
}
      
/** Functional class for Drawing a cubic Bezier curve. */
case class BezierDraw (xStart: Double, yStart: Double, xC1: Double, yC1: Double, xC2: Double, yC2: Double, xEnd: Double, yEnd: Double,
    val lineWidth: Double, val colour: Colour, zOrder: Int = 0) extends PaintFullElem with BezierLikeOld
{ override type AlignT = BezierDraw
  def typeStr: String = "BezierDraw"
  //def str = persist6(pStart, pC1, pC2, pEnd, lineWidth, colour) 
  override def fTrans(f: Vec2 => Vec2): BezierDraw = BezierDraw(f(pStart), f(pC1),f(pC2), f(pEnd), lineWidth, colour)
  override def rendToCanvas(cp: pCanv.CanvasPlatform): Unit = cp.bezierDraw(this)
}

/** Companion object for the BezierDraw class. */
object BezierDraw
{
   def apply (start: Vec2, pC1: Vec2, pC2: Vec2, endPt: Vec2, lineWidth: Double, colour: Colour): BezierDraw =
      new BezierDraw(start.x, start.y, pC1.x, pC1.y, pC2.x, pC2.y, endPt.x, endPt.y, lineWidth, colour)
}

