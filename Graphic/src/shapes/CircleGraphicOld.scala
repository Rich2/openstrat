/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pCanv._, Colour.Black, pXml._

trait CircleGraphicOld extends ShapeGraphicOld with SimilarPreserve
{ type ThisT <: CircleGraphicOld
  override def shape: Circle
  def svgStr: String = closedTagStr("circle", attribs)
  def circleAttribs: Arr[NumericAttrib] = shape.circleAttribs
  @inline final def cen: Vec2 = shape.cen
  @inline final def xCen: Double = shape.xCen
  @inline final def yCen: Double = shape.yCen
  @inline final def radius: Double = shape.radius
  @inline final def diameter: Double = shape.diameter
}



final case class CircleFill(shape: Circle, fillColour: Colour) extends CircleGraphicOld with ShapeFill
{ type ThisT = CircleFill
  override def fTrans(f: Vec2 => Vec2): ThisT = CircleFill(shape.fTrans(f), fillColour)
  override def rendToCanvas(cp: CanvasPlatform): Unit = cp.circleFill(this)
  override def scaleXY(xOperand: Double, yOperand: Double): DisplayElem = ???
  override def shearX(operand: Double): TransElem = ???

  override def shearY(operand: Double): TransElem = ???
  override def attribs: Arr[Attrib] = circleAttribs +- fillAttrib
}

final case class CircleDraw(shape: Circle, lineWidth: Double = 2.0, lineColour: Colour = Black) extends CircleGraphicOld with ShapeDraw
{ type ThisT = CircleDraw
  override def fTrans(f: Vec2 => Vec2): CircleDraw = CircleDraw(shape.fTrans(f), lineWidth, lineColour)
  override def rendToCanvas(cp: CanvasPlatform): Unit = cp.circleDraw(this)
  override def scaleXY(xOperand: Double, yOperand: Double): DisplayElem = ???
  override def shearX(operand: Double): TransElem = ???

  override def shearY(operand: Double): TransElem = ???
  override def attribs: Arr[Attrib] = drawAttribs
}

final case class CircleFillDraw(shape: Circle, fillColour: Colour, lineWidth: Double = 2.0, lineColour: Colour = Black) extends CircleGraphicOld with
  ShapeFillDraw
{ type ThisT = CircleFillDraw
  override def fTrans(f: Vec2 => Vec2): CircleFillDraw = CircleFillDraw(shape.fTrans(f), fillColour, lineWidth, lineColour)
  override def rendToCanvas(cp: CanvasPlatform): Unit = cp.circleFillDraw(this)
  override def scaleXY(xOperand: Double, yOperand: Double): DisplayElem = ???
  override def shearX(operand: Double): TransElem = ???

  override def shearY(operand: Double): TransElem = ???
  override def attribs: Arr[Attrib] = fillDrawAttribs
}

case class CircleFillIcon(fillColour: Colour) extends ShapeFillIcon
{ override def scaleSlate(scale: Double, cen: Vec2): CircleFill = CircleFill(Circle(scale, cen), fillColour)
  override def scaleSlate(scale: Double, xCen: Double, yCen: Double): CircleFill = CircleFill(Circle(scale, xCen, yCen), fillColour)
}