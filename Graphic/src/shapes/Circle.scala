/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pXml._

/** Circle class is defined by its centre and radius. It fulfills the interface for an Ellipse. */
final case class Circle(xCen: Double, yCen: Double, radius: Double) extends Ellipse with TransSimElem
{
  /** Diameter of the circle. This has the same value as width, a property that hasn't been created yet. */
  override type ThisT = Circle

  override def fTrans(f: Vec2 => Vec2): Circle =
  { val v1: Vec2 = cen.addX(radius)
    val newV1: Vec2 = f(v1)
    val newCen = f(cen)
    val newRadius = (newV1 - newCen).magnitude
    Circle(newCen, newRadius)
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
  override def slate(offset: Vec2): Circle = Circle(cen + offset, radius)
  @inline override def slate(xOffset: Double, yOffset: Double): Circle = Circle(xCen + xOffset, yCen + yOffset, radius)
  override def scale(operand: Double): Circle = Circle(cen * operand, radius * operand)
  override def mirrorXOffset(yOffset: Double): Circle = Circle(cen.mirrorXOffset(yOffset), radius)
  override def mirrorX: Circle = Circle(xCen, -yCen, radius)
  override def mirrorYOffset(xOffset: Double): Circle = Circle(cen.mirrorYOffset(xOffset), radius)
  override def mirrorY: Circle = Circle(-xCen, yCen, radius)
  override def prolign(matrix: ProlignMatrix): Circle = Circle(cen.prolign(matrix), radius * matrix.vFactor)
  override def rotate90: Circle = Circle(cen.rotate90, radius)
  override def rotate180: Circle = Circle(cen.rotate180, radius)
  override def rotate270: Circle = Circle(cen.rotate270, radius)
  override def rotateRadians(radians: Double): Circle = Circle(cen.rotateRadians(radians), radius)
  override def mirror(line: Line2): Circle = Circle(cen.mirror(line), radius)
  
  override def fill(colour: Colour): CircleFill = CircleFill(this, colour)
  def draw(lineWidth: Double = 2, lineColour: Colour = Colour.Black): CircleDraw = CircleDraw(this, lineWidth, lineColour)
  def cxAttrib: NumericAttrib = NumericAttrib("cx", xCen)
  def cyAttrib: NumericAttrib = NumericAttrib("cy", yCen)
  def rAttrib: NumericAttrib = NumericAttrib("r", radius)
  def circleAttribs: Arr[NumericAttrib] = Arr(cxAttrib, cyAttrib, rAttrib)
}

/** This object provides factory methods for circles. */
object Circle extends ShapeIcon
{ def apply(cen: Vec2, radius: Double) = new Circle(cen.x, cen.y, radius)
  
  implicit val slateImplicit: Slate[Circle] = (obj, offset) => obj.slate(offset)
  implicit val scaleImplicit: Scale[Circle] = (obj, operand) => obj.scale(operand)
  
  override def scaleSlate(scale: Double, cen: Vec2): Circle = Circle(cen, scale)
  override def scaleSlate(scale: Double, xCen: Double, yCen: Double): Circle = Circle(xCen, yCen, scale)  

  override def fill(colour: Colour): CircleFillIcon = CircleFillIcon(colour)
}