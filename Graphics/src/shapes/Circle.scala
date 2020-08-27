/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pWeb._

/** Circle class is defined by its centre and radius. It fulfills the interface for an Ellipse. */
final case class Circle(radius: Double, xCen: Double, yCen: Double) extends Ellipse with SimilarPreserve
{
  /** Diameter of the circle. This has the same value as width, a property that hasn't been created yet. */
  override type ThisT = Circle

  override def fTrans(f: Vec2 => Vec2): Circle =
  { val v1: Vec2 = cen.addX(radius)
    val newV1: Vec2 = f(v1)
    val newCen = f(cen)
    val newRadius = (newV1 - newCen).magnitude
    Circle(newRadius, newCen)
  }
  
  @inline def diameter: Double = radius * 2
  override def x1: Double = xCen + radius
  override def y1: Double = yCen
  override def x2: Double = xCen - radius
  override def y2: Double = yCen

  override def x3: Double = xCen

  override def y3: Double = yCen + radius

  override def majorRadius: Double = radius
  override def minorRadius: Double = radius
  
  override def shearX(operand: Double): TransElem = ???

  override def shearY(operand: Double): TransElem = ???
  
  override def fillOld(fillColour: Colour): CircleFillOld = CircleFillOld(this, fillColour)
  override def fill(fillColour: Colour): CircleGraphic = CircleGraphic(this, Arr(FillColour(fillColour)))
  def fillRadial(cenColour: Colour, outerColour: Colour): CircleGraphic =
    CircleGraphic(this, Arr(FillRadial(cenColour, outerColour)), Arr())
  
  def drawOld(lineWidth: Double = 2, lineColour: Colour = Colour.Black): CircleDraw = CircleDraw(this, lineWidth, lineColour)

  def draw(lineWidth: Double = 2, lineColour: Colour = Colour.Black): CircleGraphic =
    CircleGraphic(this, Arr(CurveDraw(lineWidth, lineColour)), Arr())

  override def fillDrawOld(fillColour: Colour, lineWidth: Double, lineColour: Colour): ShapeFillDraw =
    CircleFillDraw(this, fillColour, lineWidth, lineColour)

  def fillDraw(fillColour: Colour, lineWidth: Double, lineColour: Colour): CircleGraphic =
    CircleGraphic(this, Arr(FillColour(fillColour), CurveDraw(lineWidth, lineColour)), Arr())
  
  def cxAttrib: XANumeric = XANumeric("cx", xCen)
  def cyAttrib: XANumeric = XANumeric("cy", yCen)
  def rAttrib: XANumeric = XANumeric("r", radius)
  def circleAttribs: Arr[XANumeric] = Arr(cxAttrib, cyAttrib, rAttrib)
}

/** This object provides factory methods for circles. */
object Circle extends ShapeIcon
{ def apply(radius: Double, cen: Vec2 = Vec2Z) = new Circle(radius, cen.x, cen.y)
  
  implicit val slateImplicit: Slate[Circle] = (obj, offset) => obj.slate(offset)
  implicit val scaleImplicit: Scale[Circle] = (obj, operand) => obj.scale(operand)
  
  override def scaleSlate(scale: Double, cen: Vec2): Circle = Circle(scale, cen)
  override def scaleSlate(scale: Double, xCen: Double, yCen: Double): Circle = Circle(scale, xCen, yCen)  

  override def fill(colour: Colour): CircleFillIcon = CircleFillIcon(colour)
}