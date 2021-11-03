/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import Colour._, pgui._

/** Graphic that draws a rectangle. */
trait RectangleDraw extends PolygonDraw with RectangleGraphicSimple 
{
  /** Translate geometric transformation on a RectangleDraw, returns a RectangleDraw. */
  override def slateXY(xDelta: Double, yDelta: Double): RectangleDraw = RectangleDraw(shape.slateXY(xDelta, yDelta), lineWidth, lineColour)

  /** Uniform scaling transformation a RectangleDraw, returns a RectangleDraw. */
  override def scale(operand: Double): RectangleDraw = RectangleDraw(shape.scale(operand), lineWidth, lineColour)

  /** Mirror, reflection transformation across the X axis upon a RectangleDraw returns a RectangleDraw. */
  override def negY: RectangleDraw = RectangleDraw(shape.negY, lineWidth, lineColour)

  /** Mirror, reflection transformation across the X axis upon a RectangleDraw returns a RectangleDraw. */
  override def negX: RectangleDraw = RectangleDraw(shape.negX, lineWidth, lineColour)

  override def rotate90: RectangleDraw = ???
  override def rotate180: RectangleDraw = ???
  override def rotate270: RectangleDraw = ???

  /** Mirror, reflection transformation across the X axis upon a RectangleDraw returns a RectangleDraw. */
  override def prolign(matrix: ProlignMatrix): RectangleDraw = RectangleDraw(shape.prolign(matrix), lineWidth, lineColour)

  /** Mirror, rotate(radians) transformation across the X axis upon a RectangleDraw returns a RectangleDraw. */
  override def rotate(angle: AngleVec): RectangleDraw = RectangleDraw(shape.rotate(angle), lineWidth, lineColour)

  /** Mirror, reflection transformation across the X axis upon a RectangleDraw returns a RectangleDraw. */
  override def reflect(lineLike: LineLike): RectangleDraw = RectangleDraw(shape.reflect(lineLike), lineWidth, lineColour)

  /** Independent X and Y dimension scaling upon a RectangleDraw returns a RectangleDraw. */
  override def scaleXY(xOperand: Double, yOperand: Double): RectangleDraw = RectangleDraw(shape.scaleXY(xOperand, yOperand), lineWidth, lineColour)
}

/** Companion object for RectangleDraw contains factory method and implementation class. */
object RectangleDraw
{
  def apply(shape: Rectangle, lineWidth: Double = 2, lineColour: Colour = Black): RectangleDraw = RectangleDrawImp(shape, lineWidth, lineColour)
  
  /** Immutable Graphic element that defines and draws a Polygon. */
  case class RectangleDrawImp(shape: Rectangle, lineWidth: Double = 2, lineColour: Colour = Black) extends RectangleDraw
  {
    override def rendToCanvas(cp: CanvasPlatform): Unit = cp.polygonDraw(this)
  }
}