/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A segment of a curve that could be used in a closed shape or curve path. The names start and end are used in CurveSeg and its sub classes to
 *  provide consistent naming across line segments [[LineSeg]]s, circular arcs [[CArc]]s, elliptical arcs [[EArc]]s and Square and cubic beziers
 *  [[Bezier]]s, which require different numbers of points in their specification. */
trait CurveSeg extends Drawable
{ /** The X component of the start point of this curve segment often called x1 in other APIs. */
  def startX: Double

  /** The Y component of the start point of this curve segment, often called y1 in other APIs. */
  def startY: Double

  /** Start point of this curve segment, often called p1 */
  final def pStart: Pt2 = startX pp startY

  /** The X component of the end point of this curve segment. Often called x2 on a line or x4 on a cubic bezier in other APIs.*/
  def endX: Double

  /** The Y component of the end point of this curve segment. Often called y2 on a line or y4 on a cubic bezier in other APIs. */
  def endY: Double

  /** The end point [[Pt2]] of this curve segment. Often called p2 on a line or p4 on a cubic bezier in other APIs. */
  final def pEnd: Pt2 = endX pp endY

  /** Translate 2D geometric transformation, on this CurveSeg, returns a CurveSeg. The Return type will be narrowed in sub traits. */
  override def slateXY(xDelta: Double, yDelta: Double): CurveSeg

  /** Uniform 2D geometric scaling transformation, on this CurveSeg, returns a CurveSeg. The Return type will be narrowed in sub traits / classes. */
  override def scale(operand: Double): CurveSeg

  /** Mirror, reflection 2D geometric transformation across the X axis by negating Y, on this CurveSeg, returns a CurveSeg. The return type will be
   *  narrowed in sub traits / classes. */
  override def negY: CurveSeg

  /** Mirror, reflection 2D geometric transformation across the Y axis by negating X, on this CurveSeg, returns a CurveSeg. The return type will be
   *  narrowed in sub traits / classes. */
  override def negX: CurveSeg

  /** 2D Transformation using a [[ProlignMatrix]], on this CurveSeg, returns a CurveSeg. The return type will be narrowed in sub classes / traits. */
  override def prolign(matrix: ProlignMatrix): CurveSeg

  /** Rotation 2D geometric transformation on a GeomElem, on this CurveSeg, returns a CurveSeg. The return type will be narrowed in sub classes and traits. */
  override def rotate(angle: AngleVec): CurveSeg

  override def rotate90: CurveSeg
  override def rotate180: CurveSeg
  override def rotate270: CurveSeg

  /** Reflect 2D geometric transformation across a line, line segment or ray, on this CurveSeg, returns a CurveSeg. The return type will be narrowed
   *  in sub classes and traits. */
  override def reflect(lineLike: LineLike): CurveSeg

  /** XY scaling 2D geometric transformation, on this CurveSeg, returns a CurveSeg. The return type will be narrowed in sub classes and traits. */
  override def scaleXY(xOperand: Double, yOperand: Double): CurveSeg

  /** Shear 2D geometric transformation along the X Axis, on this CurveSeg, returns a CurveSeg. The return type will be narrowed in sub classes and
   *  traits. */
  override def shearX(operand: Double): CurveSeg

  /** Shear 2D geometric transformation along the Y Axis, on this CurveSeg, returns a CurveSeg. The return type will be narrowed in sub classes and
   *  traits. */
  override def shearY(operand: Double): CurveSeg
}

object CurveSeg
{ implicit val slateImplicit: Slate[CurveSeg] = (obj: CurveSeg, dx: Double, dy: Double) => obj.slateXY(dx, dy)
  implicit val scaleImplicit: Scale[CurveSeg] = (obj: CurveSeg, operand: Double) => obj.scale(operand)
  implicit val rotateImplicit: Rotate[CurveSeg] = (obj: CurveSeg, angle: AngleVec) => obj.rotate(angle)
  implicit val prolignImplicit: Prolign[CurveSeg] = (obj, matrix) => obj.prolign(matrix)
  implicit val XYScaleImplicit: ScaleXY[CurveSeg] = (obj, xOperand, yOperand) => obj.scaleXY(xOperand, yOperand)
  implicit val ReflectImplicit: Reflect[CurveSeg] = (obj, lineLike) => obj.reflect(lineLike)

  implicit val transAxesImplicit: TransAxes[CurveSeg] = new TransAxes[CurveSeg]
  { override def negYT(obj: CurveSeg): CurveSeg = obj.negY
    override def negXT(obj: CurveSeg): CurveSeg = obj.negX
    override def rotate90(obj: CurveSeg): CurveSeg = obj.rotate90
    override def rotate180(obj: CurveSeg): CurveSeg = obj.rotate180
    override def rotate270(obj: CurveSeg): CurveSeg = obj.rotate270
  }

  implicit val shearImplicit: Shear[CurveSeg] = new Shear[CurveSeg]
  { override def shearXT(obj: CurveSeg, yFactor: Double): CurveSeg = obj.shearX(yFactor)
    override def shearYT(obj: CurveSeg, xFactor: Double): CurveSeg = obj.shearY(xFactor)
  }
}