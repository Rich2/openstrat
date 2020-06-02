/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom
import pCanv._, Colour.Black

case class CircleFillOld(circle: CircleOld, colour: Colour) extends TransSimerUser with PaintElem
{ override type SimerT = CircleFillOld
  override type MemT = CircleOld
  override def geomMem: MemT = circle
  override def newThis(transer: CircleOld): CircleFillOld = CircleFillOld(transer, colour)
  def shear(xScale: Double, yScale: Double): AffineElem = ???
  override def rendToCanvas(cp: CanvasPlatform): Unit = cp.circleFillOld(this)

  override def mirrorYOffset(xOffset: Double): CircleFillOld = ???

  override def mirrorXOffset(yOffset: Double): CircleFillOld = ???

  override def mirrorX: CircleFillOld = CircleFillOld(circle.mirrorX, colour)

  override def mirrorY: CircleFillOld = ???

  override def prolign(matrix: ProlignMatrix): CircleFillOld = ???

  override def rotate90: CircleFillOld = ???

  override def rotate180: CircleFillOld = ???

  override def rotate270: CircleFillOld = ???
}

final case class CircleFill(circle: Circle, fillColour: Colour) extends FillElem
{
  override def rendToCanvas(cp: CanvasPlatform): Unit = cp.circleFill(this)

  override def mirrorXOffset(yOffset: Double): CircleFill = copy(circle.mirrorXOffset(yOffset))
  override def mirrorX: CircleFill = copy(circle.mirrorX)
  override def mirrorYOffset(xOffset: Double): CircleFill = CircleFill(circle.mirrorYOffset(xOffset), fillColour)
  override def mirrorY: CircleFill = copy(circle.mirrorY)

  override def slate(offset: Vec2): CircleFill = copy(circle.slate(offset))
  
  /** Translate geometric transformation. */
  @inline def slate(xOffset: Double, yOffset: Double): CircleFill = copy(circle.slate(xOffset, yOffset))

  override def scale(operand: Double): CircleFill = CircleFill(circle.scale(operand), fillColour)

  override def prolign(matrix: ProlignMatrix): CircleFill = CircleFill(circle.prolign(matrix), fillColour)

  override def rotate90: CircleFill = copy(circle.rotate90)

  override def rotate180: CircleFill = copy(circle.rotate180)

  override def rotate270: CircleFill = copy(circle.rotate270)

  override def rotateRadians(radians: Double): CircleFill = copy(circle.rotateRadians(radians))

  override def mirror(line: Line2): CircleFill = copy(circle.mirror(line))
}

case class CircleDrawOld(circle: CircleOld, lineWidth: Double, colour: Colour) extends TransSimerUser with PaintElem
{ override type SimerT = CircleDrawOld
  override type MemT = CircleOld
  override def geomMem: MemT = circle
  override def newThis(transer: CircleOld): CircleDrawOld = CircleDrawOld(transer, lineWidth, colour)
  def shear(xScale: Double, yScale: Double): AffineElem = ???
  override def rendToCanvas(cp: CanvasPlatform): Unit = cp.circleDraw(this)

  override def mirrorYOffset(xOffset: Double): CircleDrawOld = ???

  override def mirrorXOffset(yOffset: Double): CircleDrawOld = ???

  override def mirrorX: CircleDrawOld = ???

  override def mirrorY: CircleDrawOld = ???

  override def prolign(matrix: ProlignMatrix): CircleDrawOld = ???

  override def rotate90: CircleDrawOld = ???

  override def rotate180: CircleDrawOld = ???

  override def rotate270: CircleDrawOld = ???
}

case class CircleFillDraw(circle: CircleOld, fillColour: Colour, lineWidth: Double = 2.0, lineColour: Colour = Black) extends TransSimerUser
  with PaintElem
{ override type SimerT = CircleFillDraw
  override type MemT = CircleOld
  override def geomMem: MemT = circle
  override def newThis(transer: CircleOld): CircleFillDraw = CircleFillDraw(transer, fillColour, lineWidth, lineColour)
  override def shear(xScale: Double, yScale: Double): AffineElem = ???
  override def rendToCanvas(cp: CanvasPlatform): Unit = cp.circleFillDraw(this)

  override def mirrorYOffset(xOffset: Double): CircleFillDraw = ???

  override def mirrorXOffset(yOffset: Double): CircleFillDraw = ???

  override def mirrorX: CircleFillDraw = ???

  override def mirrorY: CircleFillDraw = ???

  override def prolign(matrix: ProlignMatrix): CircleFillDraw = ???

  override def rotate90: CircleFillDraw = ???

  override def rotate180: CircleFillDraw = ???

  override def rotate270: CircleFillDraw = ???
}