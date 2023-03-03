/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pgui._, Colour.Black

/** Immutable Graphic element that defines and draws a Polygon. */
trait PolygonDraw extends PolygonGraphicSimple with CanvShapeDraw
{ //override def fTrans(f: Vec2 => Vec2): PolygonDraw = PolygonDraw(shape.fTrans(f), lineWidth, lineColour)
  override def rendToCanvas(cp: CanvasPlatform): Unit = cp.polygonDraw(this)

  /** Translate geometric transformation on a PolygonDraw, returns a PolygonDraw. */
  override def slateXY(xDelta: Double, yDelta: Double): PolygonDraw = PolygonDraw(shape.slateXY(xDelta, yDelta), lineWidth, lineColour)

  /** Uniform scaling transformation a PolygonDraw, returns a PolygonDraw. */
  override def scale(operand: Double): PolygonDraw = PolygonDraw(shape.scale(operand), lineWidth, lineColour)

  /** Mirror, reflection transformation across the X axis upon a PolygonDraw returns a PolygonDraw. */
  override def negY: PolygonDraw = PolygonDraw(shape.negY, lineWidth, lineColour)

  /** Mirror, reflection transformation across the X axis upon a PolygonDraw returns a PolygonDraw. */
  override def negX: PolygonDraw = PolygonDraw(shape.negX, lineWidth, lineColour)

  /** Mirror, reflection transformation across the X axis upon a PolygonDraw returns a PolygonDraw. */
  override def prolign(matrix: ProlignMatrix): PolygonDraw = PolygonDraw(shape.prolign(matrix), lineWidth, lineColour)
  
  /** Mirror, rotate(radians) transformation across the X axis upon a PolygonDraw returns a PolygonDraw. */
  override def rotate(angle: AngleVec): PolygonDraw = PolygonDraw(shape.rotate(angle), lineWidth, lineColour)

  override def rotate90: PolygonDraw = ???
  override def rotate180: PolygonDraw = ???
  override def rotate270: PolygonDraw = ???
  
  /** Mirror, reflection transformation across the X axis upon a PolygonDraw returns a PolygonDraw. */
  override def reflect(lineLike: LineLike): PolygonDraw = PolygonDraw(shape.reflect(lineLike), lineWidth, lineColour)

  /** Independent X and Y dimension scaling upon a PolygonDraw returns a PolygonDraw. */
  override def scaleXY(xOperand: Double, yOperand: Double): PolygonDraw = PolygonDraw(shape.scaleXY(xOperand, yOperand), lineWidth, lineColour)
  
  /** Shear along the X axis upon a PolygonDraw returns a PolygonDraw. */
  override def shearX(operand: Double): PolygonDraw = PolygonDraw(shape.shearX(operand), lineWidth, lineColour)
  
  /** Shear along the Y axis upon a PolygonDraw returns a PolygonDraw. */
  override def shearY(operand: Double): PolygonDraw = PolygonDraw(shape.shearY(operand), lineWidth, lineColour)
}

object PolygonDraw
{
  def apply (shape: Polygon, lineWidth: Double = 2, lineColour: Colour = Black): PolygonDraw = PolygonDrawImp(shape, lineWidth, lineColour)

  implicit val slateImplicit: Slate[PolygonDraw] = (obj: PolygonDraw, dx: Double, dy: Double) => obj.slateXY(dx, dy)
  implicit val scaleImplicit: Scale[PolygonDraw] = (obj: PolygonDraw, operand: Double) => obj.scale(operand)
  implicit val rotateImplicit: Rotate[PolygonDraw] = (obj: PolygonDraw, angle: AngleVec) => obj.rotate(angle)
  implicit val XYScaleImplicit: ScaleXY[PolygonDraw] = (obj, xOperand, yOperand) => obj.scaleXY(xOperand, yOperand)
  implicit val prolignImplicit: Prolign[PolygonDraw] = (obj, matrix) => obj.prolign(matrix)

  implicit val reflectAxesImplicit: TransAxes[PolygonDraw] = new TransAxes[PolygonDraw]
  { override def negYT(obj: PolygonDraw): PolygonDraw = obj.negY
    override def negXT(obj: PolygonDraw): PolygonDraw = obj.negX
    override def rotate90(obj: PolygonDraw): PolygonDraw = obj.rotate90
    override def rotate180(obj: PolygonDraw): PolygonDraw = obj.rotate180
    override def rotate270(obj: PolygonDraw): PolygonDraw = obj.rotate270
  }
  
  /*implicit val persistImplicit: Persist3[Polygon, Double, Colour, PolygonDraw] =
    Persist3("PolyFill", "poly", _.shape, "lineWidth", _.lineWidth, "colour", _.lineColour, apply)*/

  /** Immutable Graphic element that defines and draws a Polygon. */
  case class PolygonDrawImp(shape: Polygon, lineWidth: Double = 2, lineColour: Colour = Black) extends PolygonDraw
  { 
    override def rendToCanvas(cp: CanvasPlatform): Unit = cp.polygonDraw(this)
  }
}