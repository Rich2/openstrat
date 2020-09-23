/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pCanv._, Colour.Black, pWeb._

trait CircleGraphicOld extends ShapeGraphicOld with SimilarPreserve
{ type ThisT <: CircleGraphicOld
  override def shape: Circle
  def svgStr: String = tagVoidStr("circle", attribs)
  def circleAttribs: Arr[XANumeric] = shape.shapeAttribs
  @inline final def cen: Vec2 = shape.cen
  @inline final def xCen: Double = shape.xCen
  @inline final def yCen: Double = shape.yCen
  @inline final def radius: Double = shape.radius
  @inline final def diameter: Double = shape.diameter
}

/** To be removed. */
final case class CircleFillOld(shape: Circle, fillColour: Colour) extends CircleGraphicOld with ShapeFillOld
{ type ThisT = CircleFillOld
  override def fTrans(f: Vec2 => Vec2): ThisT = CircleFillOld(shape.fTrans(f), fillColour)
  override def rendToCanvas(cp: CanvasPlatform): Unit = cp.circleFillOld(this)
  override def xyScale(xOperand: Double, yOperand: Double): DisplayElem = ???
  override def xShear(operand: Double): TransElem = ???

  override def yShear(operand: Double): TransElem = ???
  override def attribs: Arr[XmlAtt] = circleAttribs +- fillAttrib
}

/** To be removed. */
final case class CircleDraw(shape: Circle, lineWidth: Double = 2.0, lineColour: Colour = Black) extends CircleGraphicOld with ShapeDraw
{ type ThisT = CircleDraw
  override def fTrans(f: Vec2 => Vec2): CircleDraw = CircleDraw(shape.fTrans(f), lineWidth, lineColour)
  override def rendToCanvas(cp: CanvasPlatform): Unit = cp.circleDrawOld(this)
  override def xyScale(xOperand: Double, yOperand: Double): DisplayElem = ???
  override def xShear(operand: Double): TransElem = ???

  override def yShear(operand: Double): TransElem = ???
  override def attribs: Arr[XmlAtt] = drawAttribs
}

case class CircleFillIcon(fillColour: Colour) extends ShapeFillIcon
{ override def scaleSlate(scale: Double, cen: Vec2): CircleFillOld = CircleFillOld(Circle(scale, cen), fillColour)
  override def scaleSlate(scale: Double, xCen: Double, yCen: Double): CircleFillOld = CircleFillOld(Circle(scale, xCen, yCen), fillColour)
}