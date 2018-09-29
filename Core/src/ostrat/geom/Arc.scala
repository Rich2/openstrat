/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom
import Colour.Black

/** Super trait to Arc and ArcDraw */
trait ArcLike extends CurveLike
{ def xCen: Double
  def yCen: Double
  def pCen: Vec2 = Vec2(xCen, yCen)
  def radius: Double = (pEnd - pCen).magnitude
  def startAngle: Angle = (pStart - pCen).angle
  def endAngle: Angle = (pEnd - pCen).angle
  def deltaAngle: Angle = startAngle.angleTo(endAngle)
  def controlPt: Vec2 =
  { val sAng: Angle = startAngle
    val resultAngle = sAng.bisect(endAngle)
    val alphaAngle =  sAng.angleTo(endAngle) / 2
    pCen + resultAngle.toVec2 * radius / alphaAngle.cos
  }
  /** Calculates ControlPt and then passes controlPt.x, controlPt.y, XENd, yEnd, radius to f */
  def fControlEndRadius(f: (Double, Double, Double, Double, Double) => Unit): Unit =
  { val cp = controlPt; f(cp.x, cp.y, xEnd, yEnd, radius) }
}

case class Arc(xStart: Double, yStart: Double, xCen: Double, yCen: Double, xEnd: Double, yEnd: Double) extends CurveLike with ArcLike
{ def typeSym = 'Arc
   override def str = persist3(pStart, pCen, pEnd)
   def fTrans(f: Vec2 => Vec2): Arc = Arc(f(pStart), f(pCen), f(pEnd))   
}

object Arc
{
   def apply(pStart: Vec2, pCen: Vec2, pEnd: Vec2): Arc =  new Arc(pStart.x, pStart.y, pCen.x, pCen.y, pEnd.x, pEnd.y)
}

case class ArcDraw(xStart: Double, yStart: Double, xCen: Double, yCen: Double, xEnd: Double, yEnd: Double, lineWidth: Double,
      colour: Colour) extends PaintElem[ArcDraw] with ArcLike
{ def typeSym = 'ArcDraw
  def str = persist5(pStart, pCen, pEnd, lineWidth, colour)
   override def fTrans(f: Vec2 => Vec2) = ArcDraw(f(pStart), f(pCen), f(pEnd), lineWidth, colour)   
}

object ArcDraw
{
   def apply(pStart: Vec2, pCen: Vec2, pEnd: Vec2, lineWidth: Double = 1.0, colour: Colour = Black): ArcDraw =
      new ArcDraw(pStart.x, pStart.y, pCen.x, pCen.y, pEnd.x, pEnd.y, lineWidth, colour)
}

