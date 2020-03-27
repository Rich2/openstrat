/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom
import Colour.Black

trait ShapeElem extends PaintElem with GraphicBounded
{ def shape: Shape
  def segsLen: Int = shape.length
  override def boundingRect: BoundingRect = shape.boundingRect
}

case class ShapeFill(shape: Shape, colour: Colour) extends ShapeElem
{ override def fTrans(f: Vec2 => Vec2) = ShapeFill(shape.fTrans(f), colour)
  override def rendToCanvas(cp: pCanv.CanvasPlatform): Unit = cp.shapeFill(shape, colour)
}

case class ShapeDraw(shape: Shape, lineWidth: Double, colour: Colour = Black) extends ShapeElem
{ override def fTrans(f: Vec2 => Vec2) = ShapeDraw(shape.fTrans(f), lineWidth, colour)
  override def rendToCanvas(cp: pCanv.CanvasPlatform): Unit = cp.shapeDraw(shape, lineWidth, colour)
}

/** A pointable shape without visual. */
case class ShapeActiveOnly(shape: Shape, pointerId: Any) extends GraphicElem with ShapeActive
{ override def fTrans(f: Vec2 => Vec2): ShapeActiveOnly = ShapeActiveOnly(shape.fTrans(f), pointerId) }

case class ShapeFillDraw(shape: Shape, fillColour: Colour, lineWidth: Double, lineColour: Colour = Black) extends ShapeElem
{ override def fTrans(f: Vec2 => Vec2) = ShapeFillDraw(shape.fTrans(f), fillColour, lineWidth, lineColour)

  override def rendToCanvas(cp: pCanv.CanvasPlatform): Unit =
  { cp.shapeFill(shape, fillColour)
    cp.shapeDraw(shape, lineWidth, lineColour)
  }
}

case class ShapeFillDrawText(shape: Shape, fillColour: Colour, str: String, fontSize: Int = 24, lineWidth: Double = 2, lineColour: Colour = Black)
  extends ShapeElem
{
  override def fTrans(f: Vec2 => Vec2) = ShapeFillDrawText(shape.fTrans(f), fillColour, str,fontSize, lineWidth, lineColour)
  def drawOnly: ShapeDraw = ShapeDraw(shape, lineWidth, lineColour)
  def textOnly: TextGraphic = TextGraphic(str, fontSize, shape.boundingRect.cen, Black, CenAlign)
  def fillDrawOnly: ShapeFillDraw = ShapeFillDraw(shape, fillColour, lineWidth, lineColour)
  override def rendToCanvas(cp: pCanv.CanvasPlatform): Unit =
  { cp.shapeFill(shape, fillColour)
    cp.shapeDraw(shape, lineWidth, lineColour)
    cp.textGraphic(textOnly)
  }
}

case class ShapeAll(shape: Shape, pointerId: Any, str: String, fillColour: Colour, fontSize: Int = 24, lineWidth: Double = 2, lineColour: Colour = Black)
  extends ShapeElem with ShapeActive
{
  override def fTrans(f: Vec2 => Vec2) = ShapeAll(shape.fTrans(f), pointerId, str, fillColour, fontSize, lineWidth, lineColour)
  def drawOnly: ShapeDraw = ShapeDraw(shape, lineWidth, lineColour)
  def textOnly: TextGraphic = TextGraphic(str, fontSize, shape.boundingRect.cen, Black, CenAlign)
  def fillDrawOnly: ShapeFillDraw = ShapeFillDraw(shape, fillColour, lineWidth, lineColour)

  override def rendToCanvas(cp: pCanv.CanvasPlatform): Unit =
  { cp.shapeFill(shape, fillColour)
    cp.shapeDraw(shape, lineWidth, lineColour)
    cp.textGraphic(textOnly)
  }
}