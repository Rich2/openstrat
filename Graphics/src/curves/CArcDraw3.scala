/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import Colour._

case class CArcDraw3(curveSeg: CArc3, colour: Colour = Black, lineWidth: Double = 2) extends EArcDraw with SimilarPreserve
{
  override type ThisT = CArcDraw3

  override def fTrans(f: Pt2 => Pt2): CArcDraw3 = CArcDraw3(curveSeg.fTrans(f), colour, lineWidth)
  override def rendToCanvas(cp: pCanv.CanvasPlatform): Unit = cp.cArcDraw3(this)
}