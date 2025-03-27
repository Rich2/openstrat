/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pgui._, Colour.Black

/** Immutable Graphic element that defines and draws a Polygon. */
trait PolygonDraw extends PolygonGraphicSimple with CanvShapeDraw
{ override def rendToCanvas(cp: CanvasPlatform): Unit = cp.polygonDraw(this)
  override def slate(operand: VecPt2): PolygonDraw = PolygonDraw(shape.slate(operand), lineWidth, lineColour)
  override def slate(xOperand: Double, yOperand: Double): PolygonDraw = PolygonDraw(shape.slate(xOperand, yOperand), lineWidth, lineColour)
  override def scale(operand: Double): PolygonDraw = PolygonDraw(shape.scale(operand), lineWidth, lineColour)
  override def negY: PolygonDraw = PolygonDraw(shape.negY, lineWidth, lineColour)
  override def negX: PolygonDraw = PolygonDraw(shape.negX, lineWidth, lineColour)
  override def prolign(matrix: ProlignMatrix): PolygonDraw = PolygonDraw(shape.prolign(matrix), lineWidth, lineColour)
  override def rotate(angle: AngleVec): PolygonDraw = PolygonDraw(shape.rotate(angle), lineWidth, lineColour)
  override def rotate90: PolygonDraw = ???
  override def rotate180: PolygonDraw = ???
  override def rotate270: PolygonDraw = ???
  override def reflect(lineLike: LineLike): PolygonDraw = PolygonDraw(shape.reflect(lineLike), lineWidth, lineColour)
  override def scaleXY(xOperand: Double, yOperand: Double): PolygonDraw = PolygonDraw(shape.scaleXY(xOperand, yOperand), lineWidth, lineColour)
  override def shearX(operand: Double): PolygonDraw = PolygonDraw(shape.shearX(operand), lineWidth, lineColour)
  override def shearY(operand: Double): PolygonDraw = PolygonDraw(shape.shearY(operand), lineWidth, lineColour)
}

object PolygonDraw
{
  def apply (shape: Polygon, lineWidth: Double = 2, lineColour: Colour = Black): PolygonDraw = PolygonDrawImp(shape, lineWidth, lineColour)

  /** Implicit [[Slate]] type class instance / evidence for [[PolygonDraw]]. */
  implicit val slateEv: Slate[PolygonDraw] = (obj, operand) => obj.slate(operand)

  /** Implicit [[SlateXY]] type class instance / evidence for [[PolygonDraw]]. */
  implicit val slateXYEv: SlateXY[PolygonDraw] = (obj: PolygonDraw, dx: Double, dy: Double) => obj.slate(dx, dy)

  /** Implicit [[Scale]] type class instance / evidence for [[PolygonDraw]]. */
  implicit val scaleEv: Scale[PolygonDraw] = (obj: PolygonDraw, operand: Double) => obj.scale(operand)

  /** Implicit [[Rotate]] type class instance / evidence for [[PolygonDraw]]. */
  implicit val rotateEv: Rotate[PolygonDraw] = (obj: PolygonDraw, angle: AngleVec) => obj.rotate(angle)

  /** Implicit [[ScaleXY]] type class instance / evidence for [[PolygonDraw]]. */
  implicit val scaleXYEv: ScaleXY[PolygonDraw] = (obj, xOperand, yOperand) => obj.scaleXY(xOperand, yOperand)

  /** Implicit [[Prolgn]] type class instance / evidence for [[PolygonDraw]]. */
  implicit val prolignEv: Prolign[PolygonDraw] = (obj, matrix) => obj.prolign(matrix)

  /** Implicit [[TransAxes]] type class instance / evidence for [[PolygonDraw]]. */
  implicit val transAxesEv: TransAxes[PolygonDraw] = new TransAxes[PolygonDraw]
  { override def negYT(obj: PolygonDraw): PolygonDraw = obj.negY
    override def negXT(obj: PolygonDraw): PolygonDraw = obj.negX
    override def rotate90(obj: PolygonDraw): PolygonDraw = obj.rotate90
    override def rotate180(obj: PolygonDraw): PolygonDraw = obj.rotate180
    override def rotate270(obj: PolygonDraw): PolygonDraw = obj.rotate270
  }
  
  /*implicit val persistEv: Persist3[Polygon, Double, Colour, PolygonDraw] =
    Persist3("PolyFill", "poly", _.shape, "lineWidth", _.lineWidth, "colour", _.lineColour, apply)*/

  /** Immutable Graphic element that defines and draws a Polygon. */
  case class PolygonDrawImp(shape: Polygon, lineWidth: Double = 2, lineColour: Colour = Black) extends PolygonDraw
  { 
    override def rendToCanvas(cp: CanvasPlatform): Unit = cp.polygonDraw(this)
  }
}