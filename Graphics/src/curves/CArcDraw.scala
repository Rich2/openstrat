/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import Colour._

case class CArcDraw(curveSeg: CArc, colour: Colour = Black, lineWidth: Double = 2) extends EArcDraw with SimilarPreserve with CanvElem
{
  override type ThisT = CArcDraw

  override def fTrans(f: Pt2 => Pt2): CArcDraw = CArcDraw(curveSeg.fTrans(f), colour, lineWidth)
  override def rendToCanvas(cp: pCanv.CanvasPlatform): Unit = cp.cArcDraw3(this)

  /** XY scaling 2D geometric transformation on a CanvElem, returns a GrpahicElem. This allows different scaling factors across X and Y dimensions.
   * The return type will be narrowed in sub classes and traits.  */
  override def xyScale(xOperand: Double, yOperand: Double): CanvElem = ???

  /** Shear 2D geometric transformation along the X Axis on a CanvElem, returns a CanvElem. The return type will be narrowed in sub classes and
   * traits. */
  override def xShear(operand: Double): CanvElem = ???

  /** Shear 2D geometric transformation along the Y Axis on a CanvElem, returns a CanvElem. The return type will be narrowed in sub classes and
   * traits. */
  override def yShear(operand: Double): CanvElem = ???
}