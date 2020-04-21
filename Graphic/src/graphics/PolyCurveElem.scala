/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom
import Colour.Black

trait PolyCurveElem extends PaintFullElem with GraphicBounded
{ def shape: PolyCurve
  def segsLen: Int = shape.length
  override def boundingRect: BoundingRect = shape.boundingRect
}

case class PolyCurveFill(shape: PolyCurve, colour: Colour) extends PolyCurveElem
{ override def fTrans(f: Vec2 => Vec2) = PolyCurveFill(shape.fTrans(f), colour)
  override def rendToCanvas(cp: pCanv.CanvasPlatform): Unit = cp.shapeFill(shape, colour)
}

case class PolyCurveDraw(shape: PolyCurve, lineWidth: Double, colour: Colour = Black) extends PolyCurveElem
{ override def fTrans(f: Vec2 => Vec2) = PolyCurveDraw(shape.fTrans(f), lineWidth, colour)
  override def rendToCanvas(cp: pCanv.CanvasPlatform): Unit = cp.shapeDraw(shape, lineWidth, colour)
}

/** A pointable shape without visual. */
case class PolyCurveActiveOnly(shape: PolyCurve, pointerId: Any) extends GraphicFullElem with ShapeActive
{ override def fTrans(f: Vec2 => Vec2): PolyCurveActiveOnly = PolyCurveActiveOnly(shape.fTrans(f), pointerId) }

case class PolyCurveFillDraw(shape: PolyCurve, fillColour: Colour, lineWidth: Double, lineColour: Colour = Black) extends PolyCurveElem
{ override def fTrans(f: Vec2 => Vec2) = PolyCurveFillDraw(shape.fTrans(f), fillColour, lineWidth, lineColour)

  override def rendToCanvas(cp: pCanv.CanvasPlatform): Unit =
  { cp.shapeFill(shape, fillColour)
    cp.shapeDraw(shape, lineWidth, lineColour)
  }
}

case class PolyCurveFillDrawText(shape: PolyCurve, fillColour: Colour, str: String, fontSize: Int = 24, lineWidth: Double = 2, lineColour: Colour = Black)
  extends PolyCurveElem
{
  override def fTrans(f: Vec2 => Vec2) = PolyCurveFillDrawText(shape.fTrans(f), fillColour, str,fontSize, lineWidth, lineColour)
  def drawOnly: PolyCurveDraw = PolyCurveDraw(shape, lineWidth, lineColour)
  def textOnly: TextGraphic = TextGraphic(str, fontSize, shape.boundingRect.cen, Black, CenAlign)
  def fillDrawOnly: PolyCurveFillDraw = PolyCurveFillDraw(shape, fillColour, lineWidth, lineColour)
  override def rendToCanvas(cp: pCanv.CanvasPlatform): Unit =
  { cp.shapeFill(shape, fillColour)
    cp.shapeDraw(shape, lineWidth, lineColour)
    cp.textGraphic(textOnly)
  }
}

case class PolyCurveAll(shape: PolyCurve, pointerId: Any, str: String, fillColour: Colour, fontSize: Int = 24, lineWidth: Double = 2, lineColour: Colour = Black)
  extends PolyCurveElem with ShapeActive
{
  override def fTrans(f: Vec2 => Vec2) = PolyCurveAll(shape.fTrans(f), pointerId, str, fillColour, fontSize, lineWidth, lineColour)
  def drawOnly: PolyCurveDraw = PolyCurveDraw(shape, lineWidth, lineColour)
  def textOnly: TextGraphic = TextGraphic(str, fontSize, shape.boundingRect.cen, Black, CenAlign)
  def fillDrawOnly: PolyCurveFillDraw = PolyCurveFillDraw(shape, fillColour, lineWidth, lineColour)

  override def rendToCanvas(cp: pCanv.CanvasPlatform): Unit =
  { cp.shapeFill(shape, fillColour)
    cp.shapeDraw(shape, lineWidth, lineColour)
    cp.textGraphic(textOnly)
  }
}