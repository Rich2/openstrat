/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pCanv._, Colour.Black, pWeb._

/** A Simple circle based graphic. Not sure if this trait is useful. */
trait CircleGraphicSimple extends CircleGraphic with ShapeGraphicSimple with SimilarPreserve
{ type ThisT <: CircleGraphicSimple
  override def attribs: Arr[XmlAtt] = shapeAttribs ++ nonShapeAttribs
}

/** A simple single colour fill of a circle graphic. */
final case class CircleFill(shape: Circle, colour: Colour) extends CircleGraphicSimple with ShapeFill
{ type ThisT = CircleFill
  override def fTrans(f: Vec2 => Vec2): ThisT = CircleFill(shape.fTrans(f), colour)
  override def rendToCanvas(cp: CanvasPlatform): Unit = cp.circleFill(shape, colour)
  override def xyScale(xOperand: Double, yOperand: Double): GraphicSimple = ???
  override def xShear(operand: Double): TransElem = ???

  override def yShear(operand: Double): TransElem = ???
}

/** A simple draw of a circle graphic. */
final case class CircleDraw(shape: Circle, lineWidth: Double = 2.0, lineColour: Colour = Black) extends CircleGraphicSimple with ShapeDraw
{ type ThisT = CircleDraw
  override def fTrans(f: Vec2 => Vec2): CircleDraw = CircleDraw(shape.fTrans(f), lineWidth, lineColour)
  override def rendToCanvas(cp: CanvasPlatform): Unit = cp.circleDrawOld(this)
  override def xyScale(xOperand: Double, yOperand: Double): GraphicSimple = ???
  override def xShear(operand: Double): TransElem = ???

  override def yShear(operand: Double): TransElem = ???
  
}

case class CircleFillIcon(fillColour: Colour) extends ShapeFillIcon
{ override def scaleSlate(scale: Double, cen: Vec2): CircleFill = CircleFill(Circle(scale, cen), fillColour)
  override def scaleSlate(scale: Double, xCen: Double, yCen: Double): CircleFill = CircleFill(Circle(scale, xCen, yCen), fillColour)
}