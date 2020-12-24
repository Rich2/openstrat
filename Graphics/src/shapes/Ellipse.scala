/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pWeb._, Colour.Black, math.{Pi, sqrt}

/** The Ellipse trait can either be implemented as an [[Ellipse]] class or as a [[Circle]]. Which also fulfills the Ellipse interface. The factory
 *  methods in the Ellipse companion object return [Ellipse]]. */
trait Ellipse extends EllipseBased with ShapeCentred
{
  override def fill(fillColour: Colour): EllipseFill = EllipseFill(this, fillColour)
  override def fillHex(intValue: Int): EllipseFill = EllipseFill(this, Colour(intValue))
  override def draw(lineColour: Colour = Black, lineWidth: Double): EllipseDraw = EllipseDraw(this, lineColour, lineWidth)

  override def fillDraw(fillColour: Colour, lineColour: Colour, lineWidth: Double): GraphicElem =
    EllipseCompound(this, Arr(fillColour, DrawFacet(lineColour, lineWidth)))
  
  /** The x component of centre of the ellipse. */
  def xCen: Double

  /** The y component of centre of the ellipse. */
  def yCen: Double
  
  /** The centre of the ellipse. */
  final def cen: Pt2 = xCen pp yCen

  final def pAxes1: Pt2 = xAxes1 pp yAxes1

  /** The x component of curvestill point 2. By default this will be the curvestill at the bottom of the Ellipse. */
  def xs2: Double

  /** The y component of curvestill point 2. By default this will be the curvestill at the bottom of the Ellipse. */
  def ys2: Double

  final def pAxes2: Pt2 = Pt2(xs2, ys2)

  /** The x component of curvestill point 3. By default this will be the curvestill at the right of the Ellipse. */
  def xs3: Double

  /** The y component of curvestill point 3. By default this will be the curvestill at the right of the Ellipse. */
  def ys3: Double

  override def pAxes3: Pt2 = xs3 pp ys3

  /** The major radius of this ellipse. */
  def a: Double

  /** The major radius of this ellipse. */
  def b: Double

  /** The h value of this ellipse. */
  def h: Double

  def alignAngle: Angle

  /** Eccentricity of ellipse. */
  def e: Double

  def area: Double
  def cxAttrib: XANumeric = XANumeric("cx", xCen)
  def cyAttrib: XANumeric = XANumeric("cy", yCen)
 // override def rotateRadians(radians: Double): Ellipse
  def rxAttrib: XANumeric = XANumeric("rx", radius1)
  def ryAttrib: XANumeric = XANumeric("ry", radius2)
  def attribs: Arr[XANumeric] = Arr(cxAttrib, cyAttrib, rxAttrib, ryAttrib)
  def boundingRect: BoundingRect

  def fTrans(f: Pt2 => Pt2): Ellipse = Ellipse.cs1s0(f(cen), f(pAxes1), f(pAxes4))

  /** Translate geometric transformation on a Ellipse returns a Ellipse. */
  def slate(offset: Vec2Like): Ellipse = fTrans(_.slate(offset))

  /** Translate geometric transformation. */
  override def xySlate(xOffset: Double, yOffset: Double): Ellipse = fTrans(_.addXY(xOffset, yOffset))

  /** Uniform scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves Circles and
   * Squares. Use the xyScale method for differential scaling. */
  override def scale(operand: Double): Ellipse = fTrans(_.scale(operand))

  override def prolign(matrix: ProlignMatrix): Ellipse = fTrans(_.prolign(matrix))
  override def xyScale(xOperand: Double, yOperand: Double): Ellipse = fTrans(_.xyScale(xOperand, yOperand))
  override def rotate(angle: AngleVec): Ellipse = fTrans(_.rotate(angle))

  override def negY: Ellipse = fTrans(_.negY)

  override def negX: Ellipse = fTrans(_.negX)

  override def reflect(lineLike: LineLike): Ellipse = fTrans(_.reflect(lineLike))

  override def xShear(operand: Double): Ellipse = fTrans(_.xShear(operand))

  override def yShear(operand: Double): Ellipse = fTrans(_.yShear(operand))

 // override def slateTo(newCen: Pt2): Ellipse = ???
}

/** Companion object for the Ellipse trait caontains the EllipseImp implementation class and factory methods for Ellipse that delegate to EllipseImp.. */
object Ellipse
{ /** Factory method for an Ellipse. The apply factory methods in this Ellipse companion object default to an [[EllipseImp]] class. */
  def apply(radius1: Double, radius0: Double): Ellipse = new EllipseImp(0, 0, radius1, 0,  radius0)

  /** Factory method for an Ellipse. The apply factory methods in this Ellipse companion object default to an [[EllipseImp]] class. */
  def apply(radius1: Double, radius0: Double, cen: Pt2): Ellipse = new EllipseImp(cen.x, cen.y, cen.x + radius1, cen.y, radius0)

  def cs1s0(cen: Pt2, v1: Pt2, v0: Pt2): EllipseImp =
  { val radius0: Double = cen.distTo(v0)
    new EllipseImp(cen.x, cen.y, v1.x, v1.y, radius0)
  }

  implicit val slateImplicit: Slate[Ellipse] = (ell, dx, dy) => cs1s0(ell.cen.addXY(dx, dy), ell.pAxes1.addXY(dx, dy), ell.pAxes4.addXY(dx, dy))
  implicit val scaleImplicit: Scale[Ellipse] = (obj: Ellipse, operand: Double) => obj.scale(operand)

  implicit val rotateImplicit: Rotate[Ellipse] =
    (ell: Ellipse, angle: AngleVec) => Ellipse.cs1s0(ell.cen.rotate(angle), ell.pAxes1.rotate(angle), ell.pAxes4.rotate(angle))

  implicit val prolignImplicit: Prolign[Ellipse] = (obj, matrix) => obj.prolign(matrix)

  implicit val xyScaleImplicit: XYScale[Ellipse] = (obj, xOperand, yOperand) => obj.xyScale(xOperand, yOperand)

  implicit val reflectAxesImplicit: ReflectAxes[Ellipse] = new ReflectAxes[Ellipse]
  { override def negYT(obj: Ellipse): Ellipse = obj.negY
    override def negXT(obj: Ellipse): Ellipse = obj.negX
  }

  implicit val shearImplicit: Shear[Ellipse] = new Shear[Ellipse]
  { override def xShearT(obj: Ellipse, yFactor: Double): Ellipse = obj.xShear(yFactor)
    override def yShearT(obj: Ellipse, xFactor: Double): Ellipse = obj.yShear(xFactor)
  }

  /** The implementation class for Ellipses that are not Circles. The Ellipse is encoded as 3 Vec2s or 6 scalars although it is possible to encode an
   * ellipse with 5 scalars. Encoding the Ellipse this way greatly helps human visualisation of transformations upon an ellipse. */
  case class EllipseImp(xCen: Double, yCen: Double, xAxes1: Double, yAxes1: Double, radius2: Double) extends Ellipse
  { override def pAxes4: Pt2 = cen + s0Angle.toVec2(radius2)
    override def xAxis4: Double = pAxes4.x
    override def yAxis4: Double = pAxes1.y
    override def xs2: Double = 2 * xCen - xAxis4
    override def ys2: Double = 2 * yCen - yAxis4

    def xs3: Double = 2 * xCen - xAxes1
    def ys3: Double = 2 * yCen - yAxes1

    override def radius1: Double = cen.distTo(pAxes1)

    def a: Double = radius1.max(radius2)
    def b: Double = radius1.min(radius2)
    override def area: Double = Pi * radius1 * radius2
    override def e: Double = sqrt(a.squared - b.squared) / a
    override def h: Double = (a - b).squared / (a + b).squared

    def boundingRect: BoundingRect =
    { val xd0: Double = radius1.squared * (alignAngle.cos).squared + radius2.squared * (alignAngle.sin).squared
      val xd = xd0.sqrt
      val yd0: Double = radius1.squared * (alignAngle.sin).squared + radius2.squared * (alignAngle.cos).squared
      val yd = yd0.sqrt
      BoundingRect(xCen - xd, xCen + xd, yCen - yd, yCen + yd)
    }

    override def alignAngle: Angle = cen.angleTo(pAxes1)
    def s0Angle = alignAngle.p90
  }
}