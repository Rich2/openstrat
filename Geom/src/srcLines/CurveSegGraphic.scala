/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pgui.CanvasPlatform

/** Graphic based on a [[CurveSeg]]. */
trait CurveSegGraphic extends Graphic2Elem, Geom2Elem
{
  def curveSeg: CurveSeg
  /** the x component of the start point often called x1 */
  def xStart: Double = curveSeg.startX

  /** the y component of the start point often called y1 */
  def yStart: Double = curveSeg.startY

  /** Start point often called p1 */
  final def pStart: Pt2 = curveSeg.pStart

  /** the x component of the end point. */
  def xEnd: Double = curveSeg.endX

  /** the y component of the end point. */
  def yEnd: Double = curveSeg.endY

  /** The end point. Often called p2 on a line or p4 on a cubic bezier. */
  final def pEnd: Pt2 = curveSeg.pEnd

  override def slate(operand: VecPt2): CurveSegGraphic
  override def slate(xOperand: Double, yOperand: Double): CurveSegGraphic
  override def slateFrom(operand: VecPt2): CurveSegGraphic
  override def slateFrom(xOperand: Double, yOperand: Double): CurveSegGraphic
  override def slateX(operand: Double): CurveSegGraphic
  override def slateY(operand: Double): CurveSegGraphic
  override def scale(operand: Double): CurveSegGraphic
  override def negX: CurveSegGraphic
  override def negY: CurveSegGraphic
  override def rotate90: CurveSegGraphic
  override def rotate180: CurveSegGraphic
  override def rotate270: CurveSegGraphic
  override def prolign(matrix: AxlignMatrix): CurveSegGraphic
  override def rotate(rotation: AngleVec): CurveSegGraphic
  override def mirror(lineLike: LineLike): CurveSegGraphic
  override def shearX(operand: Double): CurveSegGraphic
  override def shearY(operand: Double): CurveSegGraphic
  override def scaleXY(xOperand: Double, yOperand: Double): CurveSegGraphic
}

trait CurveSegDraw extends CurveSegGraphic
{ /** The default value for the colour is Black. */
  def colour: Colour

  /** The default value the line width is 2. */
  def lineWidth: Double

}