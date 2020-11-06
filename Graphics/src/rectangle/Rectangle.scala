/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pWeb._

/** The Rectangle trait defines 4 vertices v0, v1, v2 and v3. The leaf classes of this class may or may not be squares and may or may not be aligned
 *  to the X and Y Axes. You can build a Rectangle using the factory methods in the Rectangle companion object. However if your rectangle is a aligned
 *  to the X and Y axis prefer the factory methods on the companion object of the shorter named [[Rect]] trait. */
trait Rectangle extends Polygon4Plus
{ final override def vertsNum: Int = 4

  /** length from v1 to v2 and v3 to v4. */
  def width1: Double

  /** length from v2 to v3 and v03 to v1. */
  def width2: Double

  override def fill(fillColour: Colour): RectangleFill = RectangleFill(this, fillColour)
  override def fillHex(intValue: Int): RectangleFill = RectangleFill(this, Colour(intValue))
  override def draw(lineColour: Colour, lineWidth: Double): RectangleDraw = RectangleDraw(this, lineWidth, lineColour)

  override def ptsArray: Array[Double] = Array(xCen, yCen, x1, y1, x2, y2, x3, y3, x4, y4)

  def rotation: Angle
  def widthAttrib: WidthAtt = WidthAtt(width1)
  def heightAttrib: HeightAtt = HeightAtt(width2)
  def xAttrib: XAttrib = XAttrib(x4)
  def yAttrib: YAttrib = YAttrib(y4)
  override def attribs: Arr[XANumeric] = Arr(widthAttrib, heightAttrib, xAttrib, yAttrib)

  @inline final override def vert(index: Int): Pt2 = index match
  { case 1 => v1
    case 2 => v2
    case 3 => v3
    case 4 => v4
    case n => excep("Index: " + n.toString + " out of range. Only 4 vertices in a Rectangle.")
  }

  override def xVert(index: Int): Double = index match
  { case 1 => x1
    case 2 => x2
    case 3 => x3
    case 4 => x4
    case n => excep("Index " + n.toString + " out of range. Only 4 vertices in rectangle.")
  }

  override def yVert(index: Int): Double = index match
  { case 1 => y1
    case 2 => y2
    case 3 => y3
    case 4 => y4
    case n => excep("Index " + n.toString + " out of range. Only 4 vertices in rectangle.")
  }

  final override def xVertsArray: Array[Double] = Array(x1, x2, x3, x4)
  final override def yVertsArray: Array[Double] = Array(y1, y2, y3, y4)
  final override def foreachVert[U](f: Pt2 => U): Unit = { f(v1); f(v2); f(v3); f(v4); ()}
  final override def foreachVertTail[U](f: Pt2 => U): Unit = { f(v2); f(v3); f(v4); () }
  override def foreachPairTail[U](f: (Double, Double) => U): Unit = { f(x2, y2); f(x3, y3); f(x4, y4); () }

  def diag1: LineSeg = LineSeg(v3, v1)
  def diag2: LineSeg = LineSeg(v4, v2)
  @inline def diags: LineSegs = LineSegs(diag1, diag2)

  /** Translate geometric transformation on a Rectangle returns a Rectangle. */
  override def slate(offset: Vec2Like): Rectangle = Rectangle.s2s4(s2Cen + offset, s4Cen + offset, width2)

  /** Translate geometric transformation on a Rectangle returns a Rectangle. */
  override def slate(xOffset: Double, yOffset: Double): Rectangle =
    Rectangle.s2s4(s2Cen.addXY(xOffset, yOffset), s4Cen.addXY(xOffset, yOffset), width2)

  /** Uniform scaling transformation on a Rectangle returns a Rectangle. */
  override def scale(operand: Double): Rectangle = Rectangle.s2s4(s2Cen * operand, s4Cen * operand, width2 * operand)

  /** Mirror, reflection transformation across the X axis on a Rectangle, returns a Rectangle. */
  override def negY: Rectangle = Rectangle.s2s4(s2Cen.negY, s4Cen.negY, width2)

  /** Mirror, reflection transformation across the X axis on a Rectangle, returns a Rectangle. */
  override def negX: Rectangle = Rectangle.s2s4(s2Cen.negX, s4Cen.negX, width2)

  override def prolign(matrix: ProlignMatrix): Rectangle = Rectangle.s2s4v1(s2Cen.prolign(matrix), s4Cen.prolign(matrix), v1.prolign(matrix))

  override def reflect(lineLike: LineLike): Rectangle = Rectangle.s2s4(s2Cen.reflect(lineLike), s4Cen.reflect(lineLike), width2)

  override def rotate(angle: Angle): Rectangle = Rectangle.s2s4(s2Cen.rotate(angle), s4Cen.rotate(angle), width2)

  override def xyScale(xOperand: Double, yOperand: Double): Rectangle =
    Rectangle.s2s4v1(s2Cen.xyScale(xOperand, yOperand), s4Cen.xyScale(xOperand, yOperand), v1.xyScale(xOperand, yOperand))

  override def slateTo(newCen: Pt2): Rectangle = ???
}

/** This perhaps should be changed to Rectangle. Some methods need renaming or possibly even deleting */
object Rectangle
{
  def apply(width: Double, height: Double, rotation: Angle, cen: Pt2 = Vec2Z): Rectangle =
  { val s2Cen: Pt2 = cen.addX(width / 2).rotate(rotation)
    val s4Cen: Pt2 = cen.subX(width / 2).rotate(rotation)
    new RectangleImp(s2Cen.x, s2Cen.y, s4Cen.x, s4Cen.y, height)
  }

  def s2s4(s2Cen: Pt2, s4Cen: Pt2, height: Double): Rectangle = new RectangleImp(s2Cen.x, s2Cen.y, s4Cen.x, s4Cen.y, height)
  def s2s4v1(s2Cen: Pt2, s4Cen: Pt2, v1: Pt2): Rectangle = new RectangleImp(s2Cen.x, s2Cen.y, s4Cen.x, s4Cen.y, s2Cen.distTo(v1) * 2)

  def curvedCorners(width: Double, height: Double, radius: Double, cen: Pt2 = Vec2Z): PolyCurve =
  { val w = width / 2
    val h = height / 2
    val s1 = PolyCurve(
        LineTail(w - radius,          h), ArcTail(w - radius pp h - radius, w pp h -radius),
        LineTail(w,          radius - h), ArcTail(w - radius pp radius - h, w - radius pp -h),
        LineTail(radius - w,         -h), ArcTail(radius - w pp radius - h, -w pp radius -h),
        LineTail(- w,        h - radius), ArcTail(radius - w pp h - radius, radius - w pp h))
     s1.slate(cen)
  }

  def curvedCornersCentred(width: Double, height: Double, radius: Double, posn: Pt2 = Vec2Z): PolyCurveCentred =
    PolyCurveCentred(posn, curvedCorners(width, height, radius).slate(posn))
  def curvedGoldenRatio(height: Double, radius: Double, posn: Pt2 = Vec2Z): PolyCurve =
    curvedCorners(height * Phi, height, radius, posn)
  def curvedGoldenRatioCentred(height: Double, radius: Double, posn: Pt2 = Vec2Z): PolyCurveCentred =
    curvedCornersCentred(height * Phi, height, radius, posn)


  def fromAxis(centreLine: LineSeg, height: Double): PolygonImp =
  { val hAngle: Angle = centreLine.angle
    val offset: Pt2 = hAngle.toVec2(height * 0.5)
    PolygonImp(centreLine.pStart + offset, centreLine.pEnd + offset, centreLine.pEnd -*- offset, centreLine.pStart -*- offset)
  }

  implicit val slateImplicit: Slate[Rectangle] = (obj: Rectangle, offset: Vec2Like) => obj.slate(offset)
  implicit val scaleImplicit: Scale[Rectangle] = (obj: Rectangle, operand: Double) => obj.scale(operand)
  implicit val rotateImplicit: Rotate[Rectangle] = (obj: Rectangle, angle: Angle) => obj.rotate(angle)
  implicit val prolignImplicit: Prolign[Rectangle] = (obj, matrix) => obj.prolign(matrix)
  implicit val reflectImplicit: Reflect[Rectangle] = (obj: Rectangle, lineLike: LineLike) => obj.reflect(lineLike)

  implicit val reflectAxesImplicit: ReflectAxes[Rectangle] = new ReflectAxes[Rectangle]
  { override def negYT(obj: Rectangle): Rectangle = obj.negY
    override def negXT(obj: Rectangle): Rectangle = obj.negX
  }

  /** A rectangle class that has position and may not be aligned to the X and Y axes. */
  final class RectangleImp(val xS2Cen: Double, val yS2Cen: Double, val xS4Cen: Double, val yS4Cen: Double, val width2: Double) extends RectS2S4
  {
    override def fTrans(f: Pt2 => Pt2): RectangleImp = RectangleImp.s2s4v1(f(s2Cen), f(s4Cen), f(v1))

    override def productArity: Int = 5

    override def productElement(n: Int): Any = ???
  }

  object RectangleImp
  { def s2s4v1(s2Cen: Pt2, s4Cen: Pt2, v1: Pt2): RectangleImp = new RectangleImp(s2Cen.x, s2Cen.y, s4Cen.x, s4Cen.y, s2Cen.distTo(v1) * 2)
  }
}