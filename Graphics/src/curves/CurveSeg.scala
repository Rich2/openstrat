/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** Sub traits include Line, LineDraw, Arc, ArcDraw, Bezier, BezierDraw */
trait CurveSeg extends Drawable
{ /** the x component of the start point often called x1 */
  def xStart: Double

  /** the y component of the start point often called y1 */
  def yStart: Double

  /** Start point of this curve segment, often called p1 */
  final def pStart: Pt2 = xStart pp yStart

  /** the x component of the end point of this curve segment. */
  def xEnd: Double

  /** the y component of the end point of this curve segment. */
  def yEnd: Double

  /** The end point of this curve segment. Often called p2 on a line or p4 on a cubic bezier. */
  final def pEnd: Pt2 = xEnd pp yEnd

  /** Translate 2D geometric transformation. The Return type will be narrowed in sub traits. */
  override def xySlate(xOffset: Double, yOffset: Double): CurveSeg

  /** Uniform 2D geometric scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves
   * [[Circle]]s and [[Square]]s. Use the xyScale method for differential scaling. The Return type will be narrowed in sub traits / classes. */
  override def scale(operand: Double): CurveSeg

  /** Mirror, reflection 2D geometric transformation across the X axis by negating y. The return type will be narrowed in sub traits / classes. */
  override def negY: CurveSeg

  /** Mirror, reflection 2D geometric transformation across the Y axis by negating X. The return type will be narrowed in sub traits / classes. */
  override def negX: CurveSeg

  /** 2D Transformation using a [[ProlignMatrix]]. The return type will be narrowed in sub classes / traits. */
  override def prolign(matrix: ProlignMatrix): CurveSeg

  /** Rotation 2D geometric transformation on a GeomElem. The return type will be narrowed in sub classes and traits. */
  override def rotate(angle: AngleVec): CurveSeg

  /** Reflect 2D geometric transformation across a line, line segment or ray on a GeomElem. The return type will be narrowed in sub classes and
   * traits. */
  override def reflect(lineLike: LineLike): CurveSeg

  /** XY scaling 2D geometric transformation on a GeomElem. This allows different scaling factors across X and Y dimensions. The return type will be
   * narrowed in sub classes and traits. */
  override def xyScale(xOperand: Double, yOperand: Double): CurveSeg

  /** Shear 2D geometric transformation along the X Axis on a GeomElem. The return type will be narrowed in sub classes and traits. */
  override def xShear(operand: Double): CurveSeg

  /** Shear 2D geometric transformation along the Y Axis on a GeomElem. The return type will be narrowed in sub classes and traits. */
  override def yShear(operand: Double): CurveSeg
}