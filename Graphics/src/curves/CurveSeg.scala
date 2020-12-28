/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** A segment of a curve that could be used in a closed shape or curve path. The names start and end are used in CurveSeg and its sub classes to
 *  provide consistent naming across line segments [[LineSeg]]s, circular arcs [[CArc]]s, elliptical arcs [[EArc]]s and Square and cubic beziers
 *  [[Bezier]]s, which require different numbers of points in their specification. */
trait CurveSeg extends Drawable
{ /** The X component of the start point of this curve segment often called x1 in other APIs. */
  def xStart: Double

  /** The Y component of the start point of this curve segment, often called y1 in other APIs. */
  def yStart: Double

  /** Start point of this curve segment, often called p1 */
  final def pStart: Pt2 = xStart pp yStart

  /** The X component of the end point of this curve segment. Often called x2 on a line or x4 on a cubic bezier in other APIs.*/
  def xEnd: Double

  /** The Y component of the end point of this curve segment. Often called y2 on a line or y4 on a cubic bezier in other APIs. */
  def yEnd: Double

  /** The end point [[Pt2]] of this curve segment. Often called p2 on a line or p4 on a cubic bezier in other APIs. */
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