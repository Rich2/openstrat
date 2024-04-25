/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pWeb._

/** A 4 sided [[Polygon]]. */
trait Quadrilateral extends Polygon4Plus
{ type ThisT <: Quadrilateral
  final override def numVerts: Int = 4
}

/** The Rectangle trait defines 4 vertices v0, v1, v2 and v3. The leaf classes of this class may or may not be squares and may or may not be aligned
 *  to the X and Y Axes. You can build a Rectangle using the factory methods in the Rectangle companion object. However if your rectangle is a aligned
 *  to the X and Y axis prefer the factory methods on the companion object of the shorter named [[Rect]] trait. For SVG purposes this will be output
 *  as a polygon to avoid the transforms to rotate a rectangle.W */
trait Rectangle extends ShapeCentred with Quadrilateral
{ type ThisT <: Rectangle
  override def typeStr: String = "Rectangle"

  override def vertsTrans(f: Pt2 => Pt2): Rectangle = Rectangle.fromArray(unsafeMap(f))

  /** The X component of the centre. */
  override def cenX: Double = v0x \/ v2x

  /** The Y component of the centre. */
  override def cenY: Double = v0y \/ v2y

  /** length from v1 to v2 and v3 to v4. */
  def width1: Double = v3.distTo(v0)

  /** length from v2 to v3 and v03 to v1. */
  def width2: Double = v1.distTo(v2)

  @inline final override def cen: Pt2 = cenX pp cenY

  override def fill(fillColour: Colour): RectangleFill = RectangleFill(this, fillColour)
  override def fillInt(intValue: Int): RectangleFill = RectangleFill(this, Colour(intValue))
  override def draw(lineWidth: Double, lineColour: Colour): RectangleDraw = RectangleDraw(this, lineWidth, lineColour)

  final def alignAngle: Angle = (v0 >> v1).angle

  def widthAtt: XmlAtt = WidthAtt(width1)
  def heightAtt: HeightAtt = HeightAtt(width2)
  def xAttrib: XXmlAtt = XXmlAtt(v3x)
  def yAttrib: YXmlAtt = YXmlAtt(v2y)

  def diag1: LineSeg = LineSeg(v2, v0)
  def diag2: LineSeg = LineSeg(v3, v1)
  @inline def diags: LineSegArr = LineSegArr(diag1, diag2)

  /** Translate 2D geometric transformation on a Rectangle returns a Rectangle. */
  override def slate(offset: VecPt2): Rectangle = vertsTrans(_.slate(offset))

  /** Translate 2D geometric transformation on a Rectangle returns a Rectangle. */
  override def slateXY(xDelta: Double, yDelta: Double): Rectangle = vertsTrans(_.xySlate(xDelta, yDelta))

  /** Uniform scaling 2D geometric transformation on a Rectangle returns a Rectangle. */
  override def scale(operand: Double): Rectangle = vertsTrans(_.scale(operand))

  /** Mirror, reflection 2D geometric transformation across the X axis on a Rectangle, returns a Rectangle. */
  override def negY: Rectangle = Rectangle.fromArray(unsafeNegY)

  /** Mirror, reflection 2D geometric transformation across the X axis on a Rectangle, returns a Rectangle. */
  override def negX: Rectangle = Rectangle.fromArray(unsafeNegX)

  override def prolign(matrix: ProlignMatrix): Rectangle = vertsTrans(_.prolign(matrix))

  override def rotate90: Rectangle = vertsTrans(_.rotate90)
  override def rotate180: Rectangle = vertsTrans(_.rotate180)
  override def rotate270: Rectangle = vertsTrans(_.rotate270)

  override def reflect(lineLike: LineLike): Rectangle = vertsTrans(_.reflect(lineLike))

  override def rotate(angle: AngleVec): Rectangle = vertsTrans(_.rotate(angle))

  override def scaleXY(xOperand: Double, yOperand: Double): Rectangle = vertsTrans(_.xyScale(xOperand, yOperand))
}

/** Companion object for the Rectangle trait. Contains [[Rectangle.RectangleImp]] the implementation class for non specialised rectangles. It also
 *  contains various factory methods that delegate to the [[Rectangle.RectangleImp]] class. */
object Rectangle
{
  /** apply factory method for rectangle takes the width, height, rotation from alignment with the axes and a centre point. the default value for the
   * the centre point is the origin. */
  def apply(width: Double, height: Double, rotation: AngleVec, cen: Pt2 = Pt2Z): Rectangle =
  { val rtVec = rotation.toVec(width / 2)
    val upVec = (rotation + 90.degsVec).toVec(height / 2)
    vecsCen(rtVec, upVec, cen)
  }

  /** Creates a [[Rectangle]] from axis 1. The default for axis 1 is the left right axis. */
  def axis1(sd4Cen: Pt2, sd2Cen: Pt2, height: Double): Rectangle =
  { val rtVec: Vec2 = sd4Cen >/> sd2Cen
    val upVec: Vec2 = rtVec.angle.p90.toVec2(height) / 2
    val cen = sd4Cen \/ sd2Cen
    val verts = Pt2Arr(cen -rtVec + upVec, cen + rtVec + upVec, cen + rtVec - upVec, cen -rtVec - upVec)
    new RectangleImp(verts.arrayUnsafe)
  }

  def vecsCen(rtVec: Vec2, upVec: Vec2, cen: Pt2): Rectangle = new RectangleImp(unsafeVecsCen(rtVec: Vec2, upVec: Vec2, cen))

  /** Creates Rectangle from 2 vectors and centre point. The 2 vectors are the half axies from the centre point to th e right and to the top. */
  def unsafeVecsCen(rtVec: Vec2, upVec: Vec2, cen: Pt2): Array[Double] =
    Pt2Arr(cen -rtVec + upVec, cen + rtVec + upVec, cen + rtVec - upVec, cen -rtVec - upVec).arrayUnsafe

  def fromArray(array: Array[Double]): Rectangle = new RectangleImp(array)

  def curvedCorners(width: Double, height: Double, radius: Double, cen: Pt2 = Pt2Z): ShapeGenOld =
  { val w = width / 2
    val h = height / 2
    val s1 = ShapeGenOld(
        LineTail(w - radius,          h), ArcTail(w - radius pp h - radius, w pp h -radius),
        LineTail(w,          radius - h), ArcTail(w - radius pp radius - h, w - radius pp -h),
        LineTail(radius - w,         -h), ArcTail(radius - w pp radius - h, -w pp radius -h),
        LineTail(- w,        h - radius), ArcTail(radius - w pp h - radius, radius - w pp h))
     s1.slate(cen)
  }

  def curvedCornersCentred(width: Double, height: Double, radius: Double, posn: Pt2 = Pt2Z): PolyCurveCentred =
    PolyCurveCentred(posn, curvedCorners(width, height, radius).slate(posn))
  def curvedGoldenRatio(height: Double, radius: Double, posn: Pt2 = Pt2Z): ShapeGenOld =
    curvedCorners(height * Phi, height, radius, posn)
  def curvedGoldenRatioCentred(height: Double, radius: Double, posn: Pt2 = Pt2Z): PolyCurveCentred =
    curvedCornersCentred(height * Phi, height, radius, posn)

  def fromAxis(centreLine: LineSeg, height: Double): PolygonGen =
  { val hAngle: Angle = centreLine.angle
    val offset = (hAngle + 90.degsVec).toVec2(height * 0.5)
    PolygonGen(centreLine.pStart + offset, centreLine.pEnd + offset, centreLine.pEnd - offset, centreLine.pStart - offset)
  }

  def fromAxisRatio(centreLine: LineSeg, ratio: Double): PolygonGen = fromAxis(centreLine, centreLine.length * ratio)

  implicit val slateImplicit: Slate[Rectangle] = (obj: Rectangle, dx: Double, dy: Double) => obj.slateXY(dx, dy)
  implicit val scaleImplicit: Scale[Rectangle] = (obj: Rectangle, operand: Double) => obj.scale(operand)
  implicit val rotateImplicit: Rotate[Rectangle] = (obj: Rectangle, angle: AngleVec) => obj.rotate(angle)
  implicit val prolignImplicit: Prolign[Rectangle] = (obj, matrix) => obj.prolign(matrix)
  implicit val reflectImplicit: Reflect[Rectangle] = (obj: Rectangle, lineLike: LineLike) => obj.reflect(lineLike)

  implicit val reflectAxesImplicit: TransAxes[Rectangle] = new TransAxes[Rectangle]
  { override def negYT(obj: Rectangle): Rectangle = obj.negY
    override def negXT(obj: Rectangle): Rectangle = obj.negX
    override def rotate90(obj: Rectangle): Rectangle = obj.rotate90
    override def rotate180(obj: Rectangle): Rectangle = obj.rotate180
    override def rotate270(obj: Rectangle): Rectangle = obj.rotate270
  }

  /** A rectangle class that has position and may not be aligned to the X and Y axes. */
  final class RectangleImp(val arrayUnsafe: Array[Double]) extends Rectangle//S2S4
  { override type ThisT = RectangleImp
    override def fromArray(array: Array[Double]): RectangleImp = new RectangleImp(array)
    override def vertsTrans(f: Pt2 => Pt2): RectangleImp = RectangleImp.s2s4v1(f(sd1Cen), f(sd3Cen), f(v0))
  }

  object RectangleImp
  {
    def s2s4v1(s2Cen: Pt2, s4Cen: Pt2, v1: Pt2): RectangleImp =
      ??? //new RectangleImp(s2Cen.x, s2Cen.y, s4Cen.x, s4Cen.y, s2Cen.distTo(v1) * 2)
  }
}