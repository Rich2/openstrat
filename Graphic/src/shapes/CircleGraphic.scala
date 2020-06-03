/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pCanv._, Colour.Black

trait CircleGraphic extends ShapeGraphic
{ type GraphicT <: CircleGraphic
  def circle: Circle
  def fTrans(newCircle: Circle): GraphicT
}

final case class CircleFill(circle: Circle, fillColour: Colour) extends CircleGraphic with ShapeFill
{ type GraphicT = CircleFill
  override def fTrans(newCircle: Circle): GraphicT = CircleFill(newCircle, fillColour)
  override def rendToCanvas(cp: CanvasPlatform): Unit = cp.circleFill(this)

  override def mirrorXOffset(yOffset: Double): CircleFill = copy(circle.mirrorXOffset(yOffset))
  override def mirrorX: CircleFill = copy(circle.mirrorX)
  override def mirrorYOffset(xOffset: Double): CircleFill = CircleFill(circle.mirrorYOffset(xOffset), fillColour)
  override def mirrorY: CircleFill = copy(circle.mirrorY)

  override def slate(offset: Vec2): CircleFill = copy(circle.slate(offset))
  
  /** Translate geometric transformation. */
  @inline def slate(xOffset: Double, yOffset: Double): CircleFill = copy(circle.slate(xOffset, yOffset))

  override def scale(operand: Double): CircleFill = copy(circle.scale(operand))

  override def prolign(matrix: ProlignMatrix): CircleFill = copy(circle.prolign(matrix))

  override def rotate90: CircleFill = copy(circle.rotate90)

  override def rotate180: CircleFill = copy(circle.rotate180)

  override def rotate270: CircleFill = copy(circle.rotate270)

  override def rotateRadians(radians: Double): CircleFill = copy(circle.rotateRadians(radians))

  override def mirror(line: Line2): CircleFill = copy(circle.mirror(line))
}

final case class CircleDraw(circle: Circle, lineWidth: Double, lineColour: Colour) extends CircleGraphic with ShapeDraw
{ type GraphicT = CircleDraw
  override def fTrans(newCircle: Circle): CircleDraw = CircleDraw(newCircle, lineWidth, lineColour)
  override def rendToCanvas(cp: CanvasPlatform): Unit = ??? // cp.circleDraw(this)

  override def mirrorXOffset(yOffset: Double): CircleDraw = copy(circle.mirrorXOffset(yOffset))
  override def mirrorX: CircleDraw = copy(circle.mirrorX)
  override def mirrorYOffset(xOffset: Double): CircleDraw = copy(circle.mirrorYOffset(xOffset))
  override def mirrorY: CircleDraw = copy(circle.mirrorY)

  override def slate(offset: Vec2): CircleDraw = copy(circle.slate(offset))

  /** Translate geometric transformation. */
  @inline def slate(xOffset: Double, yOffset: Double): CircleDraw = copy(circle.slate(xOffset, yOffset))

  override def scale(operand: Double): CircleDraw = copy(circle.scale(operand))

  override def prolign(matrix: ProlignMatrix): CircleDraw = copy(circle.prolign(matrix))

  override def rotate90: CircleDraw = copy(circle.rotate90)

  override def rotate180: CircleDraw = copy(circle.rotate180)

  override def rotate270: CircleDraw = copy(circle.rotate270)

  override def rotateRadians(radians: Double): CircleDraw = copy(circle.rotateRadians(radians))

  override def mirror(line: Line2): CircleDraw = copy(circle.mirror(line))
}


case class CircleFillIcon(fillColour: Colour) extends ShapeFillIcon
{
  override def scaleSlate(scale: Double, cen: Vec2): CircleFill = CircleFill(Circle(scale, cen), fillColour)

  override def scaleSlate(scale: Double, xCen: Double, yCen: Double): CircleFill = CircleFill(Circle(scale, xCen, yCen), fillColour)
}
