/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pCanv._, Colour.Black, pXml._

trait CircleGraphic extends ShapeGraphic
{ type GraphicT <: CircleGraphic
  override def shape: Circle
  def fTrans(newCircle: Circle): GraphicT
  override def mirrorXOffset(yOffset: Double): GraphicT = fTrans(shape.mirrorXOffset(yOffset))
  override def mirrorX: GraphicT = fTrans(shape.mirrorX)
  override def mirrorYOffset(xOffset: Double): GraphicT = fTrans(shape.mirrorYOffset(xOffset))
  override def mirrorY: GraphicT = fTrans(shape.mirrorY)
  override def slate(offset: Vec2): GraphicT = fTrans(shape.slate(offset))
  @inline def slate(xOffset: Double, yOffset: Double): GraphicT = fTrans(shape.slate(xOffset, yOffset))
  override def scale(operand: Double): GraphicT = fTrans(shape.scale(operand))
  override def prolign(matrix: ProlignMatrix): GraphicT = fTrans(shape.prolign(matrix))
  override def rotate90: GraphicT = fTrans(shape.rotate90)
  override def rotate180: GraphicT = fTrans(shape.rotate180)
  override def rotate270: GraphicT = fTrans(shape.rotate270)
  override def rotateRadians(radians: Double): GraphicT = fTrans(shape.rotateRadians(radians))
  override def mirror(line: Line2): GraphicT = fTrans(shape.mirror(line))
  def svgStr: String = closedTagStr("circle", attribs)
  
  def cen: Vec2 = shape.cen
}

final case class CircleFill(shape: Circle, fillColour: Colour) extends CircleGraphic with ShapeFill
{ type GraphicT = CircleFill
  override def fTrans(newCircle: Circle): GraphicT = CircleFill(newCircle, fillColour)
  override def rendToCanvas(cp: CanvasPlatform): Unit = cp.circleFill(this)

  override def scaleXY(xOperand: Double, yOperand: Double): GraphicElem = ???
 // override def attribs: Arr[Attrib] = Arr(fillAttrib)  
}

final case class CircleDraw(shape: Circle, lineWidth: Double = 2.0, lineColour: Colour = Black) extends CircleGraphic with ShapeDraw
{ type GraphicT = CircleDraw
  override def fTrans(newCircle: Circle): CircleDraw = CircleDraw(newCircle, lineWidth, lineColour)
  override def rendToCanvas(cp: CanvasPlatform): Unit = cp.circleDraw(this) 
  override def scaleXY(xOperand: Double, yOperand: Double): GraphicElem = ???
  override def attribs: Arr[Attrib] = drawAttribs
}

final case class CircleFillDraw(shape: Circle, fillColour: Colour, lineWidth: Double = 2.0, lineColour: Colour = Black) extends CircleGraphic with
  ShapeFillDraw
{ type GraphicT = CircleFillDraw
  override def fTrans(newCircle: Circle): CircleFillDraw = CircleFillDraw(newCircle, fillColour, lineWidth, lineColour)
  override def rendToCanvas(cp: CanvasPlatform): Unit = cp.circleFillDraw(this)
  override def scaleXY(xOperand: Double, yOperand: Double): GraphicElem = ???
  override def attribs: Arr[Attrib] = fillDrawAttribs
}

case class CircleFillIcon(fillColour: Colour) extends ShapeFillIcon
{ override def scaleSlate(scale: Double, cen: Vec2): CircleFill = CircleFill(Circle(scale, cen), fillColour)
  override def scaleSlate(scale: Double, xCen: Double, yCen: Double): CircleFill = CircleFill(Circle(scale, xCen, yCen), fillColour)
}