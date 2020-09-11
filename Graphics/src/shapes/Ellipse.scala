/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pWeb._, scala.math.{Pi, sqrt}

/** The Ellipse trait can either be implemented as an [[Ellipse]] class or as a [[Circle]]. Which also fulfills the Ellipse interface. The factory
 *  methods in the Ellipse companion object return [Ellipse]]. */
trait Ellipse extends Shape //with ProlignPreserve
{ //type ThisT <: Ellipse
  def xCen: Double
  def yCen: Double
  final def cen: Vec2 = xCen vv yCen
  def x1: Double
  def y1: Double
  final def v1: Vec2 = x1 vv y1
  def x2: Double
  def y2: Double
  def v2: Vec2 = x2 vv y2
  def x3: Double
  def y3: Double
  def v3: Vec2 = x3 vv y3
  
  /** radius 1. This will normally be the value of a, the major ellipse radius, but even if it starts as a in certain transformations it may become b,
   *  the minor ellipse radius. */
  def r1: Double

  /** radius 2. This will normally be the value of b, the minor ellipse radius, but even if it starts as b in certain transformations it may become a,
   *  the major ellipse radius. */
  def r2: Double
  
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
  def ryAttrib: XANumeric = XANumeric("ry", r2)
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
  override def scaleXY(xOperand: Double, yOperand: Double): Ellipse = ???
  override def rotateRadians(radians: Double): Ellipse = ???

  override def reflectX: Ellipse = ???

  override def reflectY: Ellipse = ???

  override def reflectYOffset(xOffset: Double): Ellipse = ???

  override def reflectXOffset(yOffset: Double): Ellipse = ???

  override def reflect(line: Line): Ellipse = ???

  override def reflect(line: Sline): Ellipse = ???

  override def shearX(operand: Double): Ellipse = ???

  override def shearY(operand: Double): Ellipse = ???

  //override def mirrorX: Ellipse
  def fill(fillColour: Colour): EllipseGraphic = EllipseGraphic(this, Arr(FillColour(fillColour)), Arr())
}

object Ellipse
{ /** Factory method for an [[Ellipse]]. The apply factory methods in this Ellipse companion object default to an [[Implementation]] class. */
  def apply(radiusA: Double, radiusB: Double): Ellipse = new Implementation(0, 0, radiusA, 0, 0, radiusB)

  /** Factory method for an [[Ellipse]]. The apply factory methods in this Ellipse companion object default to an [[Implementation]] class. */
  def apply(radiusA: Double, radiusB: Double, cen: Vec2): Ellipse = new Implementation(cen.x, cen.y, cen.x + radiusA, cen.y, cen.x, cen.y + radiusB)

  /** The apply factory methods default to an EllipseClass. */
  def apply(radiusA: Double, radiusB: Double, xCen: Double, yCen: Double): Ellipse =
    new Implementation(xCen, yCen, xCen + radiusA, yCen, xCen, yCen + radiusB)
  
  implicit def slateImplicit: Slate[Ellipse] = (ell, offset) => Implementation(ell.cen + offset, ell.v1 + offset, ell.v3 + offset)
  
  implicit def rotateImplicit: Rotate[Ellipse] = (ell, radians) => Ellipse(0, 0, 0, 0)

  /** The implementation class for Ellipses that are not Circles. The Ellipse is encoded as 3 Vec2s or 6 scalars although it is possible to encode an
   * ellipse with 5 scalars. Encoding the Ellipse this way greatly helps human visualisation of transformations upon an ellipse. */
  case class Implementation(xCen: Double, yCen: Double, x1: Double, y1: Double, x3: Double, y3: Double) extends Ellipse //with AffinePreserve
  { //override type ThisT = Implementation
    def x2: Double = 2 * xCen - x1
    def y2: Double = 2 * yCen - y1
    override def r1: Double = (v1 - cen).magnitude
    def r2: Double = (v3 - cen).magnitude
    def a: Double = r1.max(r2)
    def b: Double = r1.min(r2)
    override def area: Double = Pi * r1 * r2
    override def e: Double = sqrt(a.squared - b.squared) / a
    override def h: Double = (a - b).squared / (a + b).squared
   // override def fTrans(f: Vec2 => Vec2): Implementation = Implementation(f(cen), f(v1), f(v3))
    override def fillOld(fillColour: Colour): ShapeFillOld = ??? //EllipseFill = EllipseFill(this, fillColour)
    override def fill(fillColour: Colour): EllipseGraphic = EllipseGraphic(this, Arr(FillColour(fillColour)), Arr())
    override def drawOld(lineWidth: Double, lineColour: Colour): ShapeDraw = ???
    override def fillDrawOld(fillColour: Colour, lineWidth: Double, lineColour: Colour): ShapeFillDraw = ???

    def boundingRect: BoundingRect =
    { val xd0: Double = r1.squared * (ellipeRotation.cos).squared + r2.squared * (ellipeRotation.sin).squared
      val xd = xd0.sqrt
      val yd0: Double = r1.squared * (ellipeRotation.sin).squared + r2.squared * (ellipeRotation.cos).squared
      val yd = yd0.sqrt
      BoundingRect(xCen - xd, xCen + xd, yCen - yd, yCen + yd)
    }

    override def ellipeRotation: Angle = (v1 - cen).angle
  }

  /** Companion object for the EllipseClass. Contains various factory methods for the creation of ellipses from different starting points. */
  object Implementation
  { def apply(vLeft: Vec2, vRight: Vec2, vUp: Vec2): Implementation = new Implementation(vLeft.x, vLeft.y, vRight.x, vRight.y, vUp.x, vUp.y)
    def cenV1V3(cen: Vec2, v1: Vec2, v3: Vec2): Implementation = new Implementation(cen.x, cen.y, v1.x, v1.y, v3.x, v3.y)
  }
}