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
  override def rendElem(cp: pCanv.CanvasPlatform): Unit = cp.shapeFill(this)
}

case class ShapeDraw(shape: Shape, lineWidth: Double, colour: Colour = Black) extends ShapeElem
{ override def fTrans(f: Vec2 => Vec2) = ShapeDraw(shape.fTrans(f), lineWidth, colour)
  override def rendElem(cp: pCanv.CanvasPlatform): Unit = cp.shapeDraw(this)
}

/** A pointable shape without visual. */
case class ShapeActive(shape: Shape, evObj: Any) extends GraphicElem with ShapeActiveTr
{ override def fTrans(f: Vec2 => Vec2): ShapeActive = ShapeActive(shape.fTrans(f), evObj) }

case class ShapeFillDraw(shape: Shape, fillColour: Colour, lineWidth: Double, lineColour: Colour = Black) extends ShapeElem
{ override def fTrans(f: Vec2 => Vec2) = ShapeFillDraw(shape.fTrans(f), fillColour, lineWidth, lineColour)
  override def rendElem(cp: pCanv.CanvasPlatform): Unit = cp.shapeFillDraw(this)
}

case class ShapeFillDrawText(shape: Shape, fillColour: Colour, str: String, fontSize: Int = 24, lineWidth: Double = 2, lineColour: Colour = Black)
  extends ShapeElem
{
  override def fTrans(f: Vec2 => Vec2) = ShapeFillDrawText(shape.fTrans(f), fillColour, str,fontSize, lineWidth, lineColour)
  def drawOnly: ShapeDraw = ShapeDraw(shape, lineWidth, lineColour)
  def textOnly: TextGraphic = TextGraphic(str, fontSize, shape.boundingRect.cen, Black, CenAlign)
  def fillDrawOnly: ShapeFillDraw = ShapeFillDraw(shape, fillColour, lineWidth, lineColour)
  override def rendElem(cp: pCanv.CanvasPlatform): Unit = { cp.shapeFillDraw(fillDrawOnly); cp.textGraphic(textOnly) }
}

case class ShapeAll(shape: Shape, evObj: Any, str: String, fillColour: Colour, fontSize: Int = 24, lineWidth: Double = 2, lineColour: Colour = Black)
  extends ShapeElem with ShapeActiveTr
{
  override def fTrans(f: Vec2 => Vec2) = ShapeAll(shape.fTrans(f), evObj, str, fillColour, fontSize, lineWidth, lineColour)
  def drawOnly: ShapeDraw = ShapeDraw(shape, lineWidth, lineColour)
  def textOnly: TextGraphic = TextGraphic(str, fontSize, shape.boundingRect.cen, Black, CenAlign)
  def fillDrawOnly: ShapeFillDraw = ShapeFillDraw(shape, fillColour, lineWidth, lineColour)
  override def rendElem(cp: pCanv.CanvasPlatform): Unit = { cp.shapeFillDraw(fillDrawOnly); cp.textGraphic(textOnly) }
}
 