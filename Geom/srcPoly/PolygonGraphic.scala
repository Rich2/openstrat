/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A Polygon based graphic. If you just want a general polygon as opposed to specifically specified Polygons such as [[Rectangle]], [[Square]] or [[Triangle]]
 * use the implementation class [[PolygonCompound]]. */
trait PolygonGraphic extends ShapeGraphic with GraphicBounded
{ override def shape: Polygon

  def x1: Double = shape.v0x
  def y1: Double = shape.v0y

  /** The number of vertices. */
  def vertsNum: Int = shape.numVerts

  /** Checks for 2 or more vertices */
  def ifv2: Boolean = shape.numVerts >= 2

  /** Checks for 3 or more vertices */
  def ifv3: Boolean = shape.numVerts >= 3
  def xVertsArray: Array[Double] = shape.elem1sArray
  def yVertsArray: Array[Double] = shape.elem2sArray
  @inline def vertsForeach(f: Pt2 => Unit): Unit = shape.vertsForeach(f)
  @inline def vertsMap[A, ArrT <: Arr[A]](f: Pt2 => A)(implicit build: BuilderArrMap[A, ArrT]): ArrT = shape.vertsMap(f)

  def vertsFoldLeft[B](f: (B, Pt2) => B)(implicit default: DefaultValue[B]): B = vertsFoldLeft(default.default)(f)

  def vertsFoldLeft[B](init: B)(f: (B, Pt2) => B): B =
  { var acc = init
    vertsForeach{v => acc = f(acc, v) }
    acc
  }

  override def slate(operand: VecPt2): PolygonGraphic
  override def slateXY(xOperand: Double, yOperand: Double): PolygonGraphic
  override def scale(operand: Double): PolygonGraphic
  override def negY: PolygonGraphic
  override def negX: PolygonGraphic
  override def prolign(matrix: ProlignMatrix): PolygonGraphic
  override def rotate90: PolygonGraphic
  override def rotate180: PolygonGraphic
  override def rotate270: PolygonGraphic
  override def rotate(angle: AngleVec): PolygonGraphic
  override def reflect(lineLike: LineLike): PolygonGraphic
  override def scaleXY(xOperand: Double, yOperand: Double): PolygonGraphic
  override def shearX(operand: Double): PolygonGraphic
  override def shearY(operand: Double): PolygonGraphic
}

/** Companion object for Polygon Graphic, contains implicit instances for the 2D geometric transformations. */
object PolygonGraphic
{ /** Implicit [[Slate]] type class instance / evidence for [[PolygonGraphic]]. */
  implicit val slateEv: Slate[PolygonGraphic] = (obj, operand) => obj.slate(operand)

  /** Implicit [[SlateXY]] type class instance / evidence for [[PolygonGraphic]]. */
  implicit val slateXYEv: SlateXY[PolygonGraphic] = (obj: PolygonGraphic, dx: Double, dy: Double) => obj.slateXY(dx, dy)

  /** Implicit [[Scale]] type class instance / evidence for [[PolygonGraphic]]. */
  implicit val scaleEv: Scale[PolygonGraphic] = (obj: PolygonGraphic, operand: Double) => obj.scale(operand)

  /** Implicit [[Rotate]] type class instance / evidence for [[PolygonGraphic]]. */
  implicit val rotateEv: Rotate[PolygonGraphic] = (obj: PolygonGraphic, angle: AngleVec) => obj.rotate(angle)

  /** Implicit [[ScaleXY]] type class instance / evidence for [[PolygonGraphic]]. */
  implicit val scaleXYEv: ScaleXY[PolygonGraphic] = (obj, xOperand, yOperand) => obj.scaleXY(xOperand, yOperand)

  /** Implicit [[Prolign]] type class instance / evidence for [[PolygonGraphic]]. */
  implicit val prolignEv: Prolign[PolygonGraphic] = (obj, matrix) => obj.prolign(matrix)
  
  /** Implicit [[TransAxes]] type class instance / evidence for [[PolygonGraphic]]. */
  implicit val transAxesEv: TransAxes[PolygonGraphic] = new TransAxes[PolygonGraphic]
  { override def negYT(obj: PolygonGraphic): PolygonGraphic = obj.negY
    override def negXT(obj: PolygonGraphic): PolygonGraphic = obj.negX
    override def rotate90(obj: PolygonGraphic): PolygonGraphic = obj.rotate90
    override def rotate180(obj: PolygonGraphic): PolygonGraphic = obj.rotate180
    override def rotate270(obj: PolygonGraphic): PolygonGraphic = obj.rotate270
  }
}