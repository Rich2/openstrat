/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom
import pCanv._, Colour.Black

case class CircleFillOld(circle: CircleOld, colour: Colour) extends TransSimerUser with PaintElemOld
{ override type AlignT = CircleFillOld
  override type MemT = CircleOld
  override def geomMem: MemT = circle
  override def newThis(transer: CircleOld): CircleFillOld = CircleFillOld(transer, colour)
  def shear(xScale: Double, yScale: Double): TransAller = ???
  override def rendToCanvas(cp: CanvasPlatform): Unit = cp.circleFillOld(this)

  override def mirrorYOffset(xOffset: Double): CircleFillOld = ???

  override def mirrorXOffset(yOffset: Double): CircleFillOld = ???

  override def mirrorX: CircleFillOld = CircleFillOld(circle.mirrorX, colour)

  override def mirrorY: CircleFillOld = ???

  override def prolign(matrix: ProlignMatrix): CircleFillOld = ???

  override def rotate90: CircleFillOld = ???
}

final case class CircleFill(circle: Circle, fillColour: Colour) extends FillElem
{
  override def rendToCanvas(cp: CanvasPlatform): Unit = {}

  override def mirrorXOffset(yOffset: Double): CircleFill = CircleFill(circle.mirrorXOffset(yOffset), fillColour)
  @inline def mirrorX: CircleFill = mirrorXOffset(0)
  override def mirrorYOffset(xOffset: Double): CircleFill = CircleFill(circle.mirrorYOffset(xOffset), fillColour)
  @inline def mirrorY: CircleFill = mirrorYOffset(0)

  override def slate(offset: Vec2): CircleFill = CircleFill(circle.slate(offset), fillColour)
  /** Translate geometric transformation. */
  @inline def slate(xOffset: Double, yOffset: Double): CircleFill = CircleFill(circle.slate(xOffset, yOffset), fillColour)

  override def scale(operand: Double): CircleFill = CircleFill(circle.scale(operand), fillColour)

  override def prolign(matrix: ProlignMatrix): CircleFill = CircleFill(circle.prolign(matrix), fillColour)

  override def rotate90: CircleFill = ???
}

case class CircleDrawOld(circle: CircleOld, lineWidth: Double, colour: Colour) extends TransSimerUser with PaintElemOld
{ override type AlignT = CircleDrawOld
  override type MemT = CircleOld
  override def geomMem: MemT = circle
  override def newThis(transer: CircleOld): CircleDrawOld = CircleDrawOld(transer, lineWidth, colour)
  def shear(xScale: Double, yScale: Double): TransAller = ???
  override def rendToCanvas(cp: CanvasPlatform): Unit = cp.circleDraw(this)

  override def mirrorYOffset(xOffset: Double): CircleDrawOld = ???

  override def mirrorXOffset(yOffset: Double): CircleDrawOld = ???

  override def mirrorX: CircleDrawOld = ???

  override def mirrorY: CircleDrawOld = ???

  override def prolign(matrix: ProlignMatrix): CircleDrawOld = ???

  override def rotate90: CircleDrawOld = ???
  //override def fTrans(f: Vec2 => Vec2): GeomElem = ???
}

case class CircleFillDraw(circle: CircleOld, fillColour: Colour, lineWidth: Double = 2.0, lineColour: Colour = Black) extends TransSimerUser
  with PaintElemOld
{ override type AlignT = CircleFillDraw
  override type MemT = CircleOld
  override def geomMem: MemT = circle
  override def newThis(transer: CircleOld): CircleFillDraw = CircleFillDraw(transer, fillColour, lineWidth, lineColour)
  override def shear(xScale: Double, yScale: Double): TransAller = ???
  override def rendToCanvas(cp: CanvasPlatform): Unit = cp.circleFillDraw(this)

  override def mirrorYOffset(xOffset: Double): CircleFillDraw = ???

  override def mirrorXOffset(yOffset: Double): CircleFillDraw = ???

  override def mirrorX: CircleFillDraw = ???

  override def mirrorY: CircleFillDraw = ???

  override def prolign(matrix: ProlignMatrix): CircleFillDraw = ???

  override def rotate90: CircleFillDraw = ???
}