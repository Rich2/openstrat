/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom
import Colour.Black

/** Super trait to Arc and ArcDraw and Arc fill which has not been implemented yet. */
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
    pCen + resultAngle.toVec2(radius / alphaAngle.cos)
  }
  /** Calculates ControlPt and then passes controlPt.x, controlPt.y, XEnd, yEnd, radius to f */
  def fControlEndRadius(f: (Double, Double, Double, Double, Double) => Unit): Unit =
  { val cp = controlPt; f(cp.x, cp.y, xEnd, yEnd, radius) }
}

/** Currently the Arc class doesn't define direction of the Arc. I think this needs modification. */
case class CArcOld(xStart: Double, yStart: Double, xCen: Double, yCen: Double, xEnd: Double, yEnd: Double) extends ArcLike
{ def typeStr: String = "Arc"
   //override def str = persist3(pStart, pCen, pEnd)
   def fTrans(f: Vec2 => Vec2): CArcOld = CArcOld(f(pStart), f(pCen), f(pEnd))
}

/** The companion object for the Arc class. */
object CArcOld
{ def apply(pStart: Vec2, pCen: Vec2, pEnd: Vec2): CArcOld =  new CArcOld(pStart.x, pStart.y, pCen.x, pCen.y, pEnd.x, pEnd.y)
}

/** A functional paint element to Draw an Arc. Defined by the arc, the line width, the colour and the zOrder. */
case class CArcDrawOld(xStart: Double, yStart: Double, xCen: Double, yCen: Double, xEnd: Double, yEnd: Double, lineWidth: Double, colour: Colour)
  extends PaintFullElem with ArcLike
{ def typeStr: String = "ArcDraw"
  //def str: String = persist6(pStart, pCen, pEnd, lineWidth, colour, zOrder)
  override def fTrans(f: Vec2 => Vec2) = CArcDrawOld(f(pStart), f(pCen), f(pEnd), lineWidth, colour)
  override def rendToCanvas(cp: pCanv.CanvasPlatform): Unit = cp.cArcDrawOld(this)
}

/** The companion object for the ArcDraw class. */
object CArcDrawOld
{ def apply(pStart: Vec2, pCen: Vec2, pEnd: Vec2, lineWidth: Double = 1.0, colour: Colour = Black): CArcDrawOld =
      new CArcDrawOld(pStart.x, pStart.y, pCen.x, pCen.y, pEnd.x, pEnd.y, lineWidth, colour)
}