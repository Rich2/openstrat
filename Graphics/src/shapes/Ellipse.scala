/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pWeb._, scala.math.{Pi, sqrt}

/** The Ellipse trait can either be implemented as an [[Ellipse]] class or as a [[Circle]]. Which also fulfills the Ellipse interface. The factory
 *  methods in the Ellipse companion object return [Ellipse]]. */
trait Ellipse extends Shape
{
  /** The x component of centre of the ellipse. */
  def xCen: Double

  /** The y component of centre of the ellipse. */
  def yCen: Double
  
  /** The centre of the ellipse. */
  final def cen: Vec2 = xCen vv yCen
  
  /** The x component of curvestill point 0. By default this will be the curvestill at the top of the Ellipse. */
  def xs0: Double

  /** The y component of curvestill point 0. By default this will be the curvestill at the top of the Ellipse. */
  def ys0: Double
  
  /** Curvestill point 0. By default this will be the curvestill at the top of the Ellipse. */
  def cs0: Vec2

  /** The x component of curvestill point 1. By default this will be the curvestill at the right of the Ellipse. */
  def xs1: Double

  /** The y component of curvestill point 1. By default this will be the curvestill at the right of the Ellipse. */
  def ys1: Double

  /** Curvestill point 1. By default this will be the curvestill at the right of the Ellipse. */
  final def cs1: Vec2 = xs1 vv ys1

  /** The x component of curvestill point 2. By default this will be the curvestill at the bottom of the Ellipse. */
  def xs2: Double

  /** The y component of curvestill point 2. By default this will be the curvestill at the bottom of the Ellipse. */
  def ys2: Double

  /** Curvestill point 2. By default this will be the curvestill at the bottom of the Ellipse. */
  final def cs2: Vec2 = Vec2(xs2, ys2)

  /** The x component of curvestill point 3. By default this will be the curvestill at the right of the Ellipse. */
  def xs3: Double

  /** The y component of curvestill point 3. By default this will be the curvestill at the right of the Ellipse. */
  def ys3: Double

  /** Curvestill point 3. By default this will be the curvestill at the right of the Ellipse. */
  def cs3: Vec2 = xs3 vv ys3

  /** radius 0. By default this will be the up radius to cs0. By convention and defualt This will normally be the value of b, the minor ellipse
   *  radius, but even if it starts as b in certain transformations it may become a, the major ellipse radius. */
  def radius0: Double

  /** radius 1. This will normally be the value of a, the major ellipse radius, but even if it starts as a in certain transformations it may become b,
   *  the minor ellipse radius. */
  def r1: Double

  /** The major radius of this ellipse. */
  def a: Double

  /** The major radius of this ellipse. */
  def b: Double

  /** The h value of this ellipse. */
  def h: Double

  def ellipeRotation: Angle

  /** Eccentricity of ellipse. */
  def e: Double

  def area: Double
  def cxAttrib: XANumeric = XANumeric("cx", xCen)
  def cyAttrib: XANumeric = XANumeric("cy", yCen)
 // override def rotateRadians(radians: Double): Ellipse
  def rxAttrib: XANumeric = XANumeric("rx", r1)
  def ryAttrib: XANumeric = XANumeric("ry", radius0)
  def shapeAttribs: Arr[XANumeric] = Arr(cxAttrib, cyAttrib, rxAttrib, ryAttrib)
  def boundingRect: BoundingRect

  /** Translate geometric transformation on a Ellipse returns a Ellipse. */
  override def slate(offset: Vec2): Ellipse = ???

  /** Translate geometric transformation. */
  override def slate(xOffset: Double, yOffset: Double): Ellipse = ???

  /** Uniform scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves Circles and
   * Squares. Use the xyScale method for differential scaling. */
  override def scale(operand: Double): Ellipse = ???

  /** Rotates 90 degrees or Pi/2 radians anticlockwise. */
  override def rotate90: Ellipse = ???

  /** Rotates 180 degrees or Pi radians. */
  override def rotate180: Ellipse = ???

  /** Rotates 90 degrees or Pi/2 radians clockwise. */
  override def rotate270: Ellipse = ???

  override def prolign(matrix: ProlignMatrix): Ellipse = ???
  override def xyScale(xOperand: Double, yOperand: Double): Ellipse = ???
  override def rotateRadians(radians: Double): Ellipse = ???

  override def reflectX: Ellipse = ???

  override def reflectY: Ellipse = ???

  override def reflectYOffset(xOffset: Double): Ellipse = ???

  override def reflectXOffset(yOffset: Double): Ellipse = ???

  override def reflect(line: Line): Ellipse = ???

  override def reflect(line: Sline): Ellipse = ???

  override def xShear(operand: Double): Ellipse = ???

  override def yShear(operand: Double): Ellipse = ???

  //override def mirrorX: Ellipse
  def fill(fillColour: Colour): EllipseGraphic = EllipseGraphic(this, Arr(FillColour(fillColour)), Arr())
}

object Ellipse
{ /** Factory method for an [[Ellipse]]. The apply factory methods in this Ellipse companion object default to an [[Implementation]] class. */
  def apply(radiusA: Double, radiusB: Double): Ellipse = new Implementation(0, 0, radiusA, 0, 0, radiusB)

  /** Factory method for an [[Ellipse]]. The apply factory methods in this Ellipse companion object default to an [[Implementation]] class. */
  def apply(radiusA: Double, radiusB: Double, cen: Vec2): Ellipse = new Implementation(cen.x, cen.y, cen.x + radiusA, cen.y, cen.x, cen.y + radiusB)

  /** The apply factory methods default to an [[Ellipse.Implementation]] Class. */
  def apply(radiusA: Double, radiusB: Double, xCen: Double, yCen: Double): Ellipse =
    new Implementation(xCen, yCen, xCen + radiusA, yCen, xCen, yCen + radiusB)

  //def cx1x3(cen: Vec2,)

  implicit val slateImplicit: Slate[Ellipse] = (ell, offset) => Implementation(ell.cen + offset, ell.cs1 + offset, ell.cs0 + offset)
  implicit val scaleImplicit: Scale[Ellipse] = (obj: Ellipse, operand: Double) => obj.scale(operand)
  implicit val rotateImplicit: Rotate[Ellipse] = (ell, radians) => Ellipse(0, 0, 0, 0)

  /** The implementation class for Ellipses that are not Circles. The Ellipse is encoded as 3 Vec2s or 6 scalars although it is possible to encode an
   * ellipse with 5 scalars. Encoding the Ellipse this way greatly helps human visualisation of transformations upon an ellipse. */
  case class Implementation(xCen: Double, yCen: Double, xs1: Double, ys1: Double, x3: Double, y3: Double) extends Ellipse
  {
    override def xs0: Double = ???

    override def ys0: Double = ???

    override def cs0: Vec2 = ???

    override def xs2: Double = 2 * xCen - xs0
    override def ys2: Double = 2 * yCen - ys0

    def xs3: Double = 2 * xCen - xs1
    def ys3: Double = 2 * yCen - ys1

        def radius0: Double = (cs0 - cen).magnitude
    override def r1: Double = (cs1 - cen).magnitude

    def a: Double = r1.max(radius0)
    def b: Double = r1.min(radius0)
    override def area: Double = Pi * r1 * radius0
    override def e: Double = sqrt(a.squared - b.squared) / a
    override def h: Double = (a - b).squared / (a + b).squared
   // override def fTrans(f: Vec2 => Vec2): Implementation = Implementation(f(cen), f(v1), f(v3))
    override def fillOld(fillColour: Colour): ShapeFillOld = ??? //EllipseFill = EllipseFill(this, fillColour)
    override def fill(fillColour: Colour): EllipseGraphic = EllipseGraphic(this, Arr(FillColour(fillColour)), Arr())
    override def drawOld(lineWidth: Double, lineColour: Colour): ShapeDraw = ???
    override def fillDrawOld(fillColour: Colour, lineWidth: Double, lineColour: Colour): ShapeFillDraw = ???

    def boundingRect: BoundingRect =
    { val xd0: Double = r1.squared * (ellipeRotation.cos).squared + radius0.squared * (ellipeRotation.sin).squared
      val xd = xd0.sqrt
      val yd0: Double = r1.squared * (ellipeRotation.sin).squared + radius0.squared * (ellipeRotation.cos).squared
      val yd = yd0.sqrt
      BoundingRect(xCen - xd, xCen + xd, yCen - yd, yCen + yd)
    }

    override def ellipeRotation: Angle = (cs1 - cen).angle
  }

  /** Companion object for the EllipseClass. Contains various factory methods for the creation of ellipses from different starting points. */
  object Implementation
  { def apply(vLeft: Vec2, vRight: Vec2, vUp: Vec2): Implementation = new Implementation(vLeft.x, vLeft.y, vRight.x, vRight.y, vUp.x, vUp.y)
    def cenV1V3(cen: Vec2, v1: Vec2, v3: Vec2): Implementation = new Implementation(cen.x, cen.y, v1.x, v1.y, v3.x, v3.y)
  }
}