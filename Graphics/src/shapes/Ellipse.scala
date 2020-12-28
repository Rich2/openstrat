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

  final override def cen: Pt2 = xCen pp yCen

  final def pAxes1: Pt2 = xAxes1 pp yAxes1
  final def pAxes2: Pt2 = Pt2(xAxes2, yAxes2)
  override def pAxes3: Pt2 = xAxes3 pp yAxes3

  /** The major radius of this ellipse, often referred to as a in maths. */
  def rMajor: Double

  /** The major radius of this ellipse,often refered to as b in maths. */
  def rMinor: Double

  /** The h value of this ellipse. */
  def h: Double

  def alignAngle: Angle

  /** Eccentricity of ellipse. */
  def e: Double

  def area: Double
  def cxAttrib: XANumeric = XANumeric("cx", xCen)
  def cyAttrib: XANumeric = XANumeric("cy", yCen)
  def rxAttrib: XANumeric = XANumeric("rx", radius1)
  def ryAttrib: XANumeric = XANumeric("ry", radius2)
  def attribs: Arr[XANumeric] = Arr(cxAttrib, cyAttrib, rxAttrib, ryAttrib)
  def boundingRect: BoundingRect

  def fTrans(f: Pt2 => Pt2): Ellipse = Ellipse.cenAxes1axes4(f(cen), f(pAxes1), f(pAxes4))

  /** Translate 2D geometric transformation, on an Ellipse, returns an Ellipse. The return type may be narrowed in sub traits / classes. */
  override def xySlate(xOffset: Double, yOffset: Double): Ellipse

  /** Uniform scaling transformation, on an Ellipse, returns an Ellipse. The return type may be narrowed in sub traits / classes. */
  override def scale(operand: Double): Ellipse

  override def prolign(matrix: ProlignMatrix): Ellipse = fTrans(_.prolign(matrix))
  override def xyScale(xOperand: Double, yOperand: Double): Ellipse = fTrans(_.xyScale(xOperand, yOperand))
  override def rotate(angle: AngleVec): Ellipse = fTrans(_.rotate(angle))

  override def negY: Ellipse

  override def negX: Ellipse

  override def reflect(lineLike: LineLike): Ellipse = fTrans(_.reflect(lineLike))

  override def xShear(operand: Double): Ellipse = fTrans(_.xShear(operand))

  override def yShear(operand: Double): Ellipse = fTrans(_.yShear(operand))
}

/** Companion object for the Ellipse trait contains the EllipseImp implementation class and factory methods for Ellipse that delegate to
 * EllipseImp. */
object Ellipse
{ /** Factory method for an Ellipse. The apply factory methods in this Ellipse companion object default to an [[EllipseImp]] class. */
  def apply(radius1: Double, radius0: Double): Ellipse = new EllipseImp(0, 0, radius1, 0,  radius0)

  /** Factory method for an Ellipse. The apply factory methods in this Ellipse companion object default to an [[EllipseImp]] class. */
  def apply(radius1: Double, radius0: Double, cen: Pt2): Ellipse = new EllipseImp(cen.x, cen.y, cen.x + radius1, cen.y, radius0)

  /** Factory method that creates an ellipse from the centre point, axes point 1 and axes point 4. */
  def cenAxes1axes4(cen: Pt2, axes1: Pt2, axes4: Pt2): EllipseImp =
  { val radius0: Double = cen.distTo(axes4)
    new EllipseImp(cen.x, cen.y, axes1.x, axes1.y, radius0)
  }

  def cenAxes1Radius2(xCen: Double, yCen: Double, xAxes1: Double, yAxes1: Double, radius2: Double): Ellipse = new EllipseImp(xCen, yCen, xAxes1, yAxes1, radius2)

  implicit val slateImplicit: Slate[Ellipse] = (ell, dx, dy) => cenAxes1axes4(ell.cen.addXY(dx, dy), ell.pAxes1.addXY(dx, dy), ell.pAxes4.addXY(dx, dy))
  implicit val scaleImplicit: Scale[Ellipse] = (obj: Ellipse, operand: Double) => obj.scale(operand)

  implicit val rotateImplicit: Rotate[Ellipse] =
    (ell: Ellipse, angle: AngleVec) => Ellipse.cenAxes1axes4(ell.cen.rotate(angle), ell.pAxes1.rotate(angle), ell.pAxes4.rotate(angle))

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
  final case class EllipseImp(xCen: Double, yCen: Double, xAxes1: Double, yAxes1: Double, radius2: Double) extends Ellipse with AxisFree
  { override type ThisT = EllipseImp
    override def xAxes2: Double = 2 * xCen - xAxis4
    override def yAxes2: Double = 2 * yCen - yAxis4
    override def xAxes3: Double = 2 * xCen - xAxes1
    override def yAxes3: Double = 2 * yCen - yAxes1
    override def pAxes4: Pt2 = cen + s0Angle.toVec2(radius2)
    override def xAxis4: Double = pAxes4.x
    override def yAxis4: Double = pAxes1.y
    override def radius1: Double = cen.distTo(pAxes1)
    override def cenP1: Vec2 = cen >> pAxes1
    override def cenP2: Vec2 = cen >> pAxes2
    override def cenP3: Vec2 = cen >> pAxes3
    override def cenP4: Vec2 = cen >> pAxes4
    override def rMajor: Double = radius1.max(radius2)
    override def rMinor: Double = radius1.min(radius2)
    override def area: Double = Pi * radius1 * radius2
    override def e: Double = sqrt(rMajor.squared - rMinor.squared) / rMajor
    override def h: Double = (rMajor - rMinor).squared / (rMajor + rMinor).squared

    def boundingRect: BoundingRect =
    { val xd0: Double = radius1.squared * (alignAngle.cos).squared + radius2.squared * (alignAngle.sin).squared
      val xd = xd0.sqrt
      val yd0: Double = radius1.squared * (alignAngle.sin).squared + radius2.squared * (alignAngle.cos).squared
      val yd = yd0.sqrt
      BoundingRect(xCen - xd, xCen + xd, yCen - yd, yCen + yd)
    }

    override def alignAngle: Angle = cen.angleTo(pAxes1)
    def s0Angle = alignAngle.p90

    /** Translate 2D geometric transformation, on an EllipseImp, returns an EllipseImp. */
    override def xySlate(xOffset: Double, yOffset: Double): EllipseImp =
      EllipseImp(xCen + xOffset, yCen + yOffset, xAxes1 + xOffset, yAxes1 + yOffset, radius2)

    /** Uniform scaling 2D geometric transformation, on an EllipseImp, returns an EllipseImp. */
    override def scale(operand: Double): EllipseImp =
      EllipseImp(xCen * operand, yCen * operand, xAxes1 * operand, yAxes1 * operand, radius2 * operand)

    override def reflect(lineLike: LineLike): EllipseImp =
      EllipseImp.cenAxes1Axes4(cen.reflect(lineLike), pAxes1.reflect(lineLike), pAxes4.reflect(lineLike))

    override def xShear(operand: Double): EllipseImp =
      EllipseImp.cenAxes1Axes4(cen.xShear(operand), pAxes1.xShear(operand), pAxes4.xShear(operand))

    override def yShear(operand: Double): EllipseImp =
      EllipseImp.cenAxes1Axes4(cen.yShear(operand), pAxes1.yShear(operand), pAxes4.yShear(operand))
  }

  /** Companion object for the EllipseImp class, contains factory methods. */
  object EllipseImp
  {
    def apply(cen: Pt2, pAxes1: Pt2, radius2: Double): EllipseImp = new EllipseImp(cen.x, cen.y, pAxes1.x, pAxes1.y, radius2)
    def cenAxes1Axes4(cen: Pt2, pAxes1: Pt2, pAxes4: Pt2): EllipseImp = new EllipseImp(cen.x, cen.y, pAxes1.x, pAxes1.y, cen.distTo(pAxes4))
  }
}