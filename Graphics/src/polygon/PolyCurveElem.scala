/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import Colour.Black

trait PolyCurveElem extends GraphicAffineElem with GraphicBoundedAffine
{ type ThisT <: PolyCurveElem
  def shape: ShapeGenOld
  def segsLen: Int = shape.elemsNum
  override def boundingRect: BoundingRect = shape.boundingRect
}

case class PolyCurveFill(shape: ShapeGenOld, colour: Colour) extends PolyCurveElem
{ override type ThisT = PolyCurveFill
  override def ptsTrans(f: Pt2 => Pt2) = PolyCurveFill(shape.ptsTrans(f), colour)
  override def rendToCanvas(cp: pCanv.CanvasPlatform): Unit = cp.shapeGenFill(ShapeGenFillOld(shape, colour))
  def xCen: Double = ???
  def yCen: Double = ???
  def cen: Pt2 = ???
}

case class PolyCurveDraw(shape: ShapeGenOld, colour: Colour = Black, lineWidth: Double = 2.0) extends PolyCurveElem
{ override type ThisT = PolyCurveDraw
  override def ptsTrans(f: Pt2 => Pt2) = PolyCurveDraw(shape.ptsTrans(f), colour, lineWidth)
  override def rendToCanvas(cp: pCanv.CanvasPlatform): Unit = cp.shapeGenDraw(ShapeGenDrawOld(shape, colour, lineWidth))
  def xCen: Double = ???
  def yCen: Double = ???
  def cen: Pt2 = ???
}

case class PolyCurveFillDraw(shape: ShapeGenOld, fillColour: Colour, lineColour: Colour = Black, lineWidth: Double = 2.0) extends PolyCurveElem
{ override type ThisT = PolyCurveFillDraw
  override def ptsTrans(f: Pt2 => Pt2) = PolyCurveFillDraw(shape.ptsTrans(f), fillColour, lineColour, lineWidth)

  override def rendToCanvas(cp: pCanv.CanvasPlatform): Unit =
  { cp.shapeGenFill(ShapeGenFillOld(shape, fillColour))
    cp.shapeGenDraw(ShapeGenDrawOld(shape, lineColour, lineWidth))
  }
  def xCen: Double = ???
  def yCen: Double = ???
  def cen: Pt2 = ???
}

case class PolyCurveFillDrawText(shape: ShapeGenOld, fillColour: Colour, str: String, fontSize: Int = 24, lineColour: Colour = Black,
                                 lineWidth: Double = 2) extends PolyCurveElem
{ override type ThisT = PolyCurveFillDrawText
  override def ptsTrans(f: Pt2 => Pt2) = PolyCurveFillDrawText(shape.ptsTrans(f), fillColour, str, fontSize, lineColour, lineWidth)
  def textOnly: TextGraphic = TextGraphic(str, fontSize, shape.boundingRect.cen, Black, CenAlign)
  def fillDrawOnly: PolyCurveFillDraw = PolyCurveFillDraw(shape, fillColour, lineColour, lineWidth)

  override def rendToCanvas(cp: pCanv.CanvasPlatform): Unit =
  { cp.shapeGenFill(ShapeGenFillOld(shape, fillColour))
    cp.shapeGenDraw(ShapeGenDrawOld(shape, lineColour, lineWidth))
    cp.textGraphic(textOnly)
  }
  def xCen: Double = ???
  def yCen: Double = ???
  def cen: Pt2 = ???
}

case class PolyCurveAllOld(shape: ShapeGenOld, pointerId: Any, str: String, fillColour: Colour, fontSize: Int = 24, lineColour: Colour = Black,
                           lineWidth: Double = 2) extends PolyCurveElem with PolyCurveActive
{ override type ThisT = PolyCurveAllOld
  override def ptsTrans(f: Pt2 => Pt2) = PolyCurveAllOld(shape.ptsTrans(f), pointerId, str, fillColour, fontSize, lineColour, lineWidth)
  def textOnly: TextGraphic = TextGraphic(str, fontSize, shape.boundingRect.cen, Black, CenAlign)
  def fillDrawOnly: PolyCurveFillDraw = PolyCurveFillDraw(shape, fillColour, lineColour, lineWidth)

  override def rendToCanvas(cp: pCanv.CanvasPlatform): Unit =
  { cp.shapeGenFill(ShapeGenFillOld(shape, fillColour))
    cp.shapeGenDraw(ShapeGenDrawOld(shape, lineColour, lineWidth))
    cp.textGraphic(textOnly)
  }

  def xCen: Double = ???
  def yCen: Double = ???
  def cen: Pt2 = ???
}