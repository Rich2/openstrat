/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pWeb._, math.Pi

/** Circle class is defined by its centre and radius. It fulfills the interface for an Ellipse. */
final case class Circle(diameter: Double, xCen: Double, yCen: Double) extends Ellipse with OrdinaledElem
{  
  override def fTrans(f: Pt2 => Pt2): Circle =
  { val v1: Pt2 = cen.addX(radius)
    val newV1: Pt2 = f(v1)
    val newCen = f(cen)
    val newRadius = newCen.distTo(newV1)
    Circle(newRadius * 2, newCen)
  }
  
  /** Diameter of the circle. This has the same value as width, a property that hasn't been created yet. */
  @inline def radius: Double = diameter / 2

  override def xs0: Double = xCen
  override def ys0: Double = yCen + radius
  override def s0: Pt2 = Pt2(xCen, ys0)
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
  override def slate(offset: Vec2Like): Circle = Circle(diameter, cen.slate(offset))

  /** Translate geometric transformation on a Circle returns a Circle. */
  override def slate(xOffset: Double, yOffset: Double): Circle = Circle(diameter, cen.addXY(xOffset, yOffset))

  /** uniform scaling transformation on a Circle returns a circle. Use the xyScale method for differential scaling. */
  override def scale(operand: Double): Circle = Circle(diameter * operand, cen.scale(operand))

  override def prolign(matrix: ProlignMatrix): Circle = fTrans(_.prolign(matrix))

  override def rotate(angle: AngleVec): Circle = Circle(diameter, cen.rotate(angle))

  override def reflect(lineLike: LineLike): Circle = Circle(diameter, cen.reflect(lineLike))

  override def negY: Circle = Circle(diameter, cen.negY)

  override def negX: Circle = Circle(diameter, cen.negX)

  //override def slateTo(newCen: Pt2): Circle = Circle(diameter, newCen)
  
  def boundingRect: BoundingRect = BoundingRect(xCen - radius, xCen + radius, yCen - radius, yCen + radius)
  
  override def fill(fillColour: Colour): CircleFill = CircleFill(this, fillColour)
  override def fillHex(intValue: Int): CircleFill = CircleFill(this, Colour(intValue))

  def fillRadial(cenColour: Colour, outerColour: Colour): CircleCompound = CircleCompound(this, Arr(FillRadial(cenColour, outerColour)), Arr())
  
  override def draw(lineColour: Colour = Colour.Black, lineWidth: Double = 2): CircleDraw = CircleDraw(this, lineWidth, lineColour)

  override def fillDraw(fillColour: Colour, lineColour: Colour, lineWidth: Double): CircleCompound =
    CircleCompound(this, Arr(fillColour, DrawFacet(lineColour, lineWidth)), Arr())
  
  def rAttrib: XANumeric = XANumeric("r", radius)
  override def attribs: Arr[XANumeric] = Arr(cxAttrib, cyAttrib, rAttrib)
  override def alignAngle: Angle = 0.degsAng

  private[this] def rr2: Double = diameter * 2.sqrt
  override def topRight: Pt2 = Pt2(rr2, rr2)
  override def bottomRight: Pt2 = Pt2(rr2, -rr2)
  override def bottomLeft: Pt2 = Pt2(-rr2, -rr2)
  override def topLeft: Pt2 = Pt2(-rr2, rr2)

  //override def topCen: Vec2 = ???
}

/** This is the companion object for the Circle case class. It provides factory methods for creating [[Circle]]s. */
object Circle extends ShapeIcon
{
  override type ShapeT = Circle
  /** Standard factory method for creating a circle from its diameter and the position of its centre. */
  def apply(diameter: Double, cen: Pt2 = Pt2Z) = new Circle(diameter, cen.x, cen.y)

  /** Factory method for creating a circle from its radius and the position of its centre. */
  def fromRadius(radius: Double, cen: Pt2 = Pt2Z) = new Circle(radius * 2, cen.x, cen.y)

  /** Factory method for creating a circle from its radius and the position of its centre. */
  def fromRadius(radius: Double, xCen: Double, yCen: Double) = new Circle(radius * 2, xCen, yCen)

  override def reify(scale: Double, cen: Pt2): Circle = Circle(scale, cen)
  override def reify(scale: Double, xCen: Double, yCen: Double): Circle = Circle(scale, xCen, yCen)
  
  implicit val slateImplicit: Slate[Circle] = (obj, dx, dy) => obj.slate(dx, dy)
  implicit val scaleImplicit: Scale[Circle] = (obj, operand) => obj.scale(operand)
  implicit val rotateImplicit: Rotate[Circle] = (obj: Circle, angle: AngleVec) => obj.rotate(angle)
  implicit val prolignImplicit: Prolign[Circle] = (obj, matrix) => obj.prolign(matrix)

  implicit val reflectAxesImplicit: ReflectAxes[Circle] = new ReflectAxes[Circle]
  { override def negYT(obj: Circle): Circle = obj.negY
    override def negXT(obj: Circle): Circle = obj.negX
  }

  override def fill(colour: Colour): CircleFillIcon = CircleFillIcon(colour)
}