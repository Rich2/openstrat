/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A segment of a curve that could be used in a closed shape or curve path. The names start and end are used in CurveSeg and its subclasses to provide
 * consistent naming across line segments [[LineSeg]]s, circular arcs [[CArc]]s, elliptical arcs [[EArc]]s and Square and cubic Béziers [[Bezier]]s, which
 * require different numbers of points in their specification. */
trait CurveSeg extends Drawable
{ 
  def boundingRect: Rect = ???
  
  /** The X component of the start point of this curve segment often called x1 in other APIs. */
  def startX: Double

  /** The Y component of the start point of this curve segment, often called y1 in other APIs. */
  def startY: Double

  /** Start point of this curve segment, often called p1 */
  final def pStart: Pt2 = Pt2(startX, startY)

  /** The X component of the end point of this curve segment. Often called x2 on a line or x4 on a cubic bezier in other APIs.*/
  def endX: Double

  /** The Y component of the end point of this curve segment. Often called y2 on a line or y4 on a cubic bezier in other APIs. */
  def endY: Double

  /** The end point [[Pt2]] of this curve segment. Often called p2 on a line or p4 on a cubic bezier in other APIs. */
  final def pEnd: Pt2 = Pt2(endX, endY)

  override def slate(operand: VecPt2): CurveSeg
  override def slate(xOperand: Double, yOperand: Double): CurveSeg
  override def scale(operand: Double): CurveSeg
  override def negX: CurveSeg
  override def negY: CurveSeg
  override def prolign(matrix: ProlignMatrix): CurveSeg
  override def rotate(rotation: AngleVec): CurveSeg
  override def rotate90: CurveSeg
  override def rotate180: CurveSeg
  override def rotate270: CurveSeg
  override def reflect(lineLike: LineLike): CurveSeg
  override def scaleXY(xOperand: Double, yOperand: Double): CurveSeg
  override def shearX(operand: Double): CurveSeg
  override def shearY(operand: Double): CurveSeg
}

object CurveSeg
{ /** Implicit [[Slate]] type class instance for [[CurveSeg]]. */
  implicit val slateEv: Slate[CurveSeg] = (obj, operand) => obj.slate(operand)

  /** Implicit [[SlateXY]] type class instance for [[CurveSeg]]. */
  implicit val slateXYEv: SlateXY[CurveSeg] = (obj: CurveSeg, dx: Double, dy: Double) => obj.slate(dx, dy)

  /** Implicit [[Scale]] type class instance for [[CurveSeg]]. */
  implicit val scaleEv: Scale[CurveSeg] = (obj: CurveSeg, operand: Double) => obj.scale(operand)

  /** Implicit [[Rotate]] type class instance for [[CurveSeg]]. */
  implicit val rotateEv: Rotate[CurveSeg] = (obj: CurveSeg, angle: AngleVec) => obj.rotate(angle)

  /** Implicit [[prolign]] type class instance for [[CurveSeg]]. */
  implicit val prolignEv: Prolign[CurveSeg] = (obj, matrix) => obj.prolign(matrix)

  /** Implicit [[ScaleXY]] type class instance for [[CurveSeg]]. */
  implicit val scaleXYEv: ScaleXY[CurveSeg] = (obj, xOperand, yOperand) => obj.scaleXY(xOperand, yOperand)

  /** Implicit [[Refelect]] type class instance for [[CurveSeg]]. */
  implicit val ReflectEv: Reflect[CurveSeg] = (obj, lineLike) => obj.reflect(lineLike)
  
  /** Implicit [[TransAxes]] type class instance for [[CurveSeg]]. */
  implicit val transAxesEv: TransAxes[CurveSeg] = new TransAxes[CurveSeg]
  { override def negYT(obj: CurveSeg): CurveSeg = obj.negY
    override def negXT(obj: CurveSeg): CurveSeg = obj.negX
    override def rotate90(obj: CurveSeg): CurveSeg = obj.rotate90
    override def rotate180(obj: CurveSeg): CurveSeg = obj.rotate180
    override def rotate270(obj: CurveSeg): CurveSeg = obj.rotate270
  }
  /** Implicit [[Shear]] type class instance for [[CurveSeg]]. */
  implicit val shearEv: Shear[CurveSeg] = new Shear[CurveSeg]
  { override def shearXT(obj: CurveSeg, yFactor: Double): CurveSeg = obj.shearX(yFactor)
    override def shearYT(obj: CurveSeg, xFactor: Double): CurveSeg = obj.shearY(xFactor)
  }
}