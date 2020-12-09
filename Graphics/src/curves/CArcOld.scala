/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** To be removed. Super trait to Arc and ArcDraw and Arc fill which has not been implemented yet. */
trait ArcLikeOld extends CurveSeg
{ def xCen: Double
  def yCen: Double
  def pCen: Pt2 = Pt2(xCen, yCen)
  def radius: Double = pEnd.distTo(pCen)
  def startAngle: Angle = pCen.angleTo(pStart)
  def endAngle: Angle = pCen.angleTo(pEnd)
  def deltaAngle: AngleVec = startAngle.deltaNegTo(endAngle)

  def controlPt: Pt2 =
  { val sAng: Angle = startAngle
    val resultAngle = sAng + deltaAngle / 2
    val alphaAngle =  sAng.deltaNegTo(endAngle) / 2
    pCen + resultAngle.toVec2(radius / alphaAngle.cos)
  }

  /** Calculates ControlPt and then passes controlPt.x, controlPt.y, XEnd, yEnd, radius to f */
  def fControlEndRadius(f: (Double, Double, Double, Double, Double) => Unit): Unit =
  { val cp = controlPt; f(cp.x, cp.y, xEnd, yEnd, radius) }
}

/** Currently the Arc class doesn't define direction of the Arc. I think this needs modification. */
case class CArcOld(xStart: Double, yStart: Double, xCen: Double, yCen: Double, xEnd: Double, yEnd: Double) extends ArcLikeOld with AffinePreserve
{ override type ThisT = CArcOld
  def typeStr: String = "Arc"
   //override def str = persist3(pStart, pCen, pEnd)
  def fTrans(f: Pt2 => Pt2): CArcOld = CArcOld(f(pStart), f(pCen), f(pEnd))

  /** Draws this geometric element to produce a [[GraphElem]] graphical element, tht can be displayed or printed. */
  override def draw(lineColour: Colour, lineWidth: Double): GraphicElem = ???
}

/** The companion object for the Arc class. */
object CArcOld
{ def apply(pStart: Pt2, pCen: Pt2, pEnd: Pt2): CArcOld =  new CArcOld(pStart.x, pStart.y, pCen.x, pCen.y, pEnd.x, pEnd.y)
}