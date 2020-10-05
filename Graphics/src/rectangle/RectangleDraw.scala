/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import Colour._, pCanv._

trait RectangleDraw extends PolygonDraw with RectangleGraphicSimple 
{
  /** Translate geometric transformation on a RectangleDraw, returns a RectangleDraw. */
  override def slate(offset: Vec2): RectangleDraw = RectangleDraw(shape.slate(offset), lineWidth, lineColour)

  /** Translate geometric transformation on a RectangleDraw, returns a RectangleDraw. */
  override def slate(xOffset: Double, yOffset: Double): RectangleDraw = RectangleDraw(shape.slate(xOffset, yOffset), lineWidth, lineColour)

  /** Uniform scaling transformation a RectangleDraw, returns a RectangleDraw. */
  override def scale(operand: Double): RectangleDraw = RectangleDraw(shape.scale(operand), lineWidth, lineColour)

  /** Mirror, reflection transformation across the X axis upon a RectangleDraw returns a RectangleDraw. */
  override def negY: RectangleDraw = RectangleDraw(shape.negY, lineWidth, lineColour)

  /** Mirror, reflection transformation across the X axis upon a RectangleDraw returns a RectangleDraw. */
  override def negX: RectangleDraw = RectangleDraw(shape.negX, lineWidth, lineColour)

  /** Rotate 90 degrees anti clockwise or rotate 270 degrees clockwise 2D geometric transformation on a RectangleDraw, returns a RectangleDraw. The return
   * type will be narrowed in sub traits / classes. */
  override def rotate90: RectangleDraw = RectangleDraw(shape.rotate90, lineWidth, lineColour)

  /** Rotate 180 degrees 2D geometric transformation on a RectangleDraw, returns a RectangleDraw. The return type will be narrowed in sub traits /
   * classes. */
  override def rotate180: RectangleDraw = RectangleDraw(shape.rotate180, lineWidth, lineColour)

  /** Rotate 270 degrees anti clockwise or rotate 90 degrees clockwise 2D geometric transformation on a RectangleDraw, returns a RectangleDraw. The return
   * type will be narrowed in sub traits / classes. */
  override def rotate270: RectangleDraw = RectangleDraw(shape.rotate270, lineWidth, lineColour)

  /** Mirror, reflection transformation across the X axis upon a RectangleDraw returns a RectangleDraw. */
  override def prolign(matrix: ProlignMatrix): RectangleDraw = RectangleDraw(shape.prolign(matrix), lineWidth, lineColour)

  /** Mirror, rotate(radians) transformation across the X axis upon a RectangleDraw returns a RectangleDraw. */
  override def rotate(angle: Angle): RectangleDraw = RectangleDraw(shape.rotate(angle), lineWidth, lineColour)

  /** Mirror, reflection transformation across the X axis upon a RectangleDraw returns a RectangleDraw. */
  override def reflect(lineLike: LineLike): RectangleDraw = RectangleDraw(shape.reflect(lineLike), lineWidth, lineColour)

  /** Independent X and Y dimension scaling upon a RectangleDraw returns a RectangleDraw. */
  override def xyScale(xOperand: Double, yOperand: Double): RectangleDraw = RectangleDraw(shape.xyScale(xOperand, yOperand), lineWidth, lineColour)
}

object RectangleDraw
{
  def apply(shape: Rectangle, lineWidth: Double = 2, lineColour: Colour = Black): RectangleDraw = RectangleDrawImp(shape, lineWidth, lineColour)
  
  /** Immutable Graphic element that defines and draws a Polygon. */
  case class RectangleDrawImp(shape: Rectangle, lineWidth: Double = 2, lineColour: Colour = Black) extends RectangleDraw
  {
    override def rendToCanvas(cp: CanvasPlatform): Unit = cp.polygonDraw(shape, lineWidth, lineColour)
  }
}
