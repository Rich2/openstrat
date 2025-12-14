/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import Colour.*, ostrat.pWeb.SvgOwnLine

case class CArcDraw(curveSeg: CArc, colour: Colour = Black, lineWidth: Double = 2) extends EArcDraw, AxisFree
{
  override type ThisT = CArcDraw

  /** Radius of the circular arc. */
  def radius: Double = curveSeg.radius

  override def slate(operand: VecPt2): CArcDraw = CArcDraw(curveSeg.slate(operand), colour, lineWidth)
  override def slate(xOperand: Double, yOperand: Double): CArcDraw = CArcDraw(curveSeg.slate(xOperand, yOperand), colour, lineWidth)
  override def slateX(xOperand: Double): CArcDraw = CArcDraw(curveSeg.slateX(xOperand), colour, lineWidth)
  override def slateY(yOperand: Double): CArcDraw = CArcDraw(curveSeg.slateY(yOperand), colour, lineWidth)
  override def scale(operand: Double): CArcDraw = CArcDraw(curveSeg.scale(operand), colour, lineWidth)
  override def prolign(matrix: AxlignMatrix): CArcDraw = CArcDraw(curveSeg.prolign(matrix), colour, lineWidth)
  override def rotate(rotation: AngleVec): CArcDraw = CArcDraw(curveSeg.rotate(rotation), colour, lineWidth)
  override def mirror(lineLike: LineLike): CArcDraw = CArcDraw(curveSeg.mirror(lineLike), colour, lineWidth)
  override def rendToCanvas(cp: pgui.CanvasPlatform): Unit = cp.cArcDraw(this)
  override def svgElems: RArr[SvgOwnLine] = ???
}