/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pWeb._, math.Pi

/** Circle class is defined by its centre and radius. It fulfills the interface for an Ellipse. */
final case class Circle(radius: Double, xCen: Double, yCen: Double) extends Ellipse
{  
  override def fTrans(f: Vec2 => Vec2): Circle =
  { val v1: Vec2 = cen.addX(radius)
    val newV1: Vec2 = f(v1)
    val newCen = f(cen)
    val newRadius = (newV1 - newCen).magnitude
    Circle(newRadius, newCen)
  }
  
  /** Diameter of the circle. This has the same value as width, a property that hasn't been created yet. */
  @inline def diameter: Double = radius * 2

  override def xs0: Double = xCen
  override def ys0: Double = yCen + radius
  override def s0: Vec2 = Vec2(xCen, ys0)
  override def xs1: Double = xCen + radius
  override def ys1: Double = yCen
  override def xs2: Double = xCen
  override def ys2: Double = yCen - ys0
  override def xs3: Double = xCen - radius
  override def ys3: Double = yCen
  
  @inline override def radius1: Double = radius
  @inline override def radius0: Double = radius
  @inline override def a: Double = radius
  @inline override def b: Double = radius
  override def area: Double = Pi * radius * radius
  override def e: Double = 0
  override def h: Double = 0

  /** Translate geometric transformation on a Circle returns a Circle. */
  override def slate(offset: Vec2): Circle = Circle(radius, cen + offset)

  /** Translate geometric transformation on a Circle returns a Circle. */
  override def slate(xOffset: Double, yOffset: Double): Circle = Circle(radius, cen.addXY(xOffset, yOffset))

  /** uniform scaling transformation on a Circle returns a circle. Use the xyScale method for differential scaling. */
  override def scale(operand: Double): Circle = Circle(radius * operand, cen * operand)

  override def prolign(matrix: ProlignMatrix): Circle = fTrans(_.prolign(matrix))

  override def rotateRadians(radians: Double): Circle = Circle(radius, cen.rotateRadians(radians))
  def rotate(angle: Angle): Circle = Circle(radius, cen.rotate(angle))

  override def reflect(line: Line): Circle = Circle(radius, cen.reflect(line))

  override def reflect(line: LineSeg): Circle = Circle(radius, cen.reflect(line))

  override def reflectYOffset(xOffset: Double): Circle = Circle(radius, cen.reflectYOffset(xOffset))

  override def reflectXOffset(yOffset: Double): Circle = Circle(radius, cen.reflectXOffset(yOffset))

  override def reflectX: Circle = Circle(radius, cen.reflectX)

  override def reflectY: Circle = Circle(radius, cen.reflectY)
  
  def boundingRect: BoundingRect = BoundingRect(xCen - radius, xCen + radius, yCen - radius, yCen + radius)
  
  override def fillOld(fillColour: Colour): CircleFillOld = CircleFillOld(this, fillColour)
  override def fill(fillColour: Colour): CircleGraphic = CircleGraphic(this, Arr(FillColour(fillColour)))
  def fillRadial(cenColour: Colour, outerColour: Colour): CircleGraphic =
    CircleGraphic(this, Arr(FillRadial(cenColour, outerColour)), Arr())
  
  def drawOld(lineWidth: Double = 2, lineColour: Colour = Colour.Black): CircleDraw = CircleDraw(this, lineWidth, lineColour)

  override def draw(lineWidth: Double = 2, lineColour: Colour = Colour.Black): CircleGraphic =
    CircleGraphic(this, Arr(CurveDraw(lineWidth, lineColour)), Arr())

  override def fillDrawOld(fillColour: Colour, lineWidth: Double, lineColour: Colour): ShapeFillDraw =
    CircleFillDraw(this, fillColour, lineWidth, lineColour)

  def fillDraw(fillColour: Colour, lineWidth: Double, lineColour: Colour): CircleGraphic =
    CircleGraphic(this, Arr(FillColour(fillColour), CurveDraw(lineWidth, lineColour)), Arr())  
  
  def rAttrib: XANumeric = XANumeric("r", radius)
  override def shapeAttribs: Arr[XANumeric] = Arr(cxAttrib, cyAttrib, rAttrib)
  override def ellipeRotation: Angle = 0.degs
}

/** This object provides factory methods for [[Circle]]s. */
object Circle extends ShapeIcon
{ def apply(radius: Double, cen: Vec2 = Vec2Z) = new Circle(radius, cen.x, cen.y)
  
  override def scaleSlate(scale: Double, cen: Vec2): Circle = Circle(scale, cen)
  override def scaleSlate(scale: Double, xCen: Double, yCen: Double): Circle = Circle(scale, xCen, yCen)
  
  implicit val slateImplicit: Slate[Circle] = (obj, offset) => obj.slate(offset)
  implicit val scaleImplicit: Scale[Circle] = (obj, operand) => obj.scale(operand)
  implicit val rotateImplicit: Rotate[Circle] = (obj: Circle, radians: Double) => obj.rotateRadians(radians)
  implicit val prolignImplicit: Prolign[Circle] = (obj, matrix) => obj.prolign(matrix)
    

  override def fill(colour: Colour): CircleFillIcon = CircleFillIcon(colour)
}