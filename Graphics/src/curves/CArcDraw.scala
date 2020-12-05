/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import Colour._

case class CArcDraw(curveSeg: CArc, colour: Colour = Black, lineWidth: Double = 2) extends EArcDraw //with SimilarPreserve
{

  override def rendToCanvas(cp: pCanv.CanvasPlatform): Unit = ??? //cp.cArcDraw3(this)
}
