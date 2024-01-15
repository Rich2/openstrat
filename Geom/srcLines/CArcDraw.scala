/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import Colour._
import ostrat.pWeb.SvgElem

case class CArcDraw(curveSeg: CArc, colour: Colour = Black, lineWidth: Double = 2) extends EArcDraw with AxisFree
{
  override type ThisT = CArcDraw

  /** Radius of the the circular arc. */
  def radius: Double = curveSeg.radius

  /** Translate 2D geometric transformation on this CArcDraw, returns a CArcDraw. */
  override def slateXY(xDelta: Double, yDelta: Double): CArcDraw = CArcDraw(curveSeg.slateXY(xDelta, yDelta), colour, lineWidth)

  /** Uniform scaling 2D geometric transformation on this CArcDraw, returns a CArcDraw. Use the xyScale method for differential scaling on the X and Y
   *  axes. */
  override def scale(operand: Double): CArcDraw = CArcDraw(curveSeg.scale(operand), colour, lineWidth)

  /** 2D geometric transformation using a [[ProlignMatrix]] on a EArcDraw, returns a EArcDraw. The Return type will be narrowed in sub traits /
   * classes. */
  override def prolign(matrix: ProlignMatrix): CArcDraw = CArcDraw(curveSeg.prolign(matrix), colour, lineWidth)

  /** Rotation 2D geometric transformation on a EArcDraw taking the rotation as a scalar measured in radians, returns a EArcDraw. The Return
   * type will be narrowed in sub traits / classes. */
  override def rotate(angle: AngleVec): CArcDraw = CArcDraw(curveSeg.rotate(angle), colour, lineWidth)

  /** Reflect 2D geometric transformation across a line, line segment or ray on a EArcDraw, returns a EArcDraw. The Return type will be narrowed
   * in sub traits / classes. */
  override def reflect(lineLike: LineLike): CArcDraw = CArcDraw(curveSeg.reflect(lineLike), colour, lineWidth)

  override def rendToCanvas(cp: pgui.CanvasPlatform): Unit = cp.cArcDraw(this)
  override def svgElems: RArr[SvgElem] = ???
}
