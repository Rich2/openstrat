/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pWeb._, scala.math.{Pi, sqrt}

/** The Ellipse trait can either be implemented as an [[Ellipse]] class or as a [[Circle]]. Which also fulfills the Ellipse interface. The factory
 *  methods in the Ellipse companion object return [Ellipse]]. */
trait Ellipse extends Shape with ProlignPreserve
{ type ThisT <: Ellipse
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
  override def rotateRadians(radians: Double): Ellipse
  def rxAttrib: XANumeric = XANumeric("rx", r1)
  def ryAttrib: XANumeric = XANumeric("ry", r2)
  def shapeAttribs: Arr[XANumeric] = Arr(cxAttrib, cyAttrib, rxAttrib, ryAttrib)
  def boundingRect: BoundingRect
      
  override def scaleXY(xOperand: Double, yOperand: Double): Ellipse = ???

  //override def mirrorX: Ellipse
  def fill(fillColour: Colour): EllipseGraphic = EllipseGraphic(this, Arr(FillColour(fillColour)), Arr())
}

object Ellipse
{ /** Factory method for an [[Ellipse]]. The apply factory methods in this Ellipse companion object default to an [[EllipseGen]] class. */
  def apply(radiusA: Double, radiusB: Double): EllipseGen = new EllipseGen(0, 0, radiusA, 0, 0, radiusB)

  /** Factory method for an [[Ellipse]]. The apply factory methods in this Ellipse companion object default to an [[EllipseGen]] class. */
  def apply(radiusA: Double, radiusB: Double, cen: Vec2): EllipseGen = new EllipseGen(cen.x, cen.y, cen.x + radiusA, cen.y, cen.x, cen.y + radiusB)

  /** The apply factory methods default to an EllipseClass. */
  def apply(radiusA: Double, radiusB: Double, xCen: Double, yCen: Double): EllipseGen =
    new EllipseGen(xCen, yCen, xCen + radiusA, yCen, xCen, yCen + radiusB)
  
  implicit def slateImplicit: Slate[Ellipse] = (ell, offset) => EllipseGen(ell.cen + offset, ell.v1 + offset, ell.v3 + offset)

  /** The implementation class for Ellipses that are not Circles. The Ellipse is encoded as 3 Vec2s or 6 scalars although it is possible to encode an
   * ellipse with 5 scalars. Encoding the Ellipse this way greatly helps human visualisation of transformations upon an ellipse. */
  case class EllipseGen(xCen: Double, yCen: Double, x1: Double, y1: Double, x3: Double, y3: Double) extends Ellipse with AffinePreserve
  { override type ThisT = EllipseGen
    def x2: Double = 2 * xCen - x1
    def y2: Double = 2 * yCen - y1
    override def r1: Double = (v1 - cen).magnitude
    def r2: Double = (v3 - cen).magnitude
    def a: Double = r1.max(r2)
    def b: Double = r1.min(r2)
    override def area: Double = Pi * r1 * r2
    override def e: Double = sqrt(a.squared - b.squared) / a
    override def h: Double = (a - b).squared / (a + b).squared
    override def fTrans(f: Vec2 => Vec2): EllipseGen = EllipseGen(f(cen), f(v1), f(v3))
    override def fillOld(fillColour: Colour): EllipseFill = EllipseFill(this, fillColour)
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
  object EllipseGen
  { def apply(vLeft: Vec2, vRight: Vec2, vUp: Vec2): EllipseGen = new EllipseGen(vLeft.x, vLeft.y, vRight.x, vRight.y, vUp.x, vUp.y)
    def cenV1V3(cen: Vec2, v1: Vec2, v3: Vec2): EllipseGen = new EllipseGen(cen.x, cen.y, v1.x, v1.y, v3.x, v3.y)
  }
}