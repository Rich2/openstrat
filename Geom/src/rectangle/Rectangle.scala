/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pWeb._

/** The Rectangle trait defines 4 vertices v0, v1, v2 and v3. The leaf classes of this class may or may not be squares and may or may not be aligned
 *  to the X and Y Axes. You can build a Rectangle using the factory methods in the Rectangle companion object. However if your rectangle is a aligned
 *  to the X and Y axis prefer the factory methods on the companion object of the shorter named [[Rect]] trait. */
trait Rectangle extends ShapeCentred with Polygon4Plus
{ type ThisT <: Rectangle
  override def typeStr: String = "Rectangle"

  final override def vertsNum: Int = 4

  /** The X component of the centre. */
  override def cenX: Double = v0x aver v2x

  /** The Y component of the centre. */
  override def cenY: Double = v0y aver v2y

  /** length from v1 to v2 and v3 to v4. */
  def width1: Double = v0.distTo(v1)

  /** length from v2 to v3 and v03 to v1. */
  def width2: Double = v1.distTo(v2)

  @inline final override def cen: Pt2 = cenX pp cenY

  override def fill(fillColour: Colour): RectangleFill = RectangleFill(this, fillColour)
  override def fillInt(intValue: Int): RectangleFill = RectangleFill(this, Colour(intValue))
  override def draw(lineColour: Colour, lineWidth: Double): RectangleDraw = RectangleDraw(this, lineWidth, lineColour)

  override def vertsArray: Array[Double] = Array(cenX, cenY, v0x, v0y, v1x, v1y, v2x, v2y, v3x, v3y)

  def alignAngle: AngleVec
  def widthAttrib: WidthAtt = WidthAtt(width1)
  def heightAttrib: HeightAtt = HeightAtt(width2)
  def xAttrib: XAttrib = XAttrib(v3x)
  def yAttrib: YAttrib = YAttrib(v3y)
  override def attribs: Arr[XANumeric] = Arr(widthAttrib, heightAttrib, xAttrib, yAttrib)

  @inline final override def unsafeVert(index: Int): Pt2 = index match
  { case 0 => v0
    case 1 => v1
    case 2 => v2
    case 3 => v3
    case n => excep("Index: " + n.toString + " out of range. Only 4 vertices in a Rectangle.")
  }

  override def xVert(index: Int): Double = index match
  { case 0 => v0x
    case 1 => v1x
    case 2 => v2x
    case 3 => v3x
    case n => excep("Index " + n.toString + " out of range. Only 4 vertices in rectangle.")
  }

  override def yVert(index: Int): Double = index match
  { case 0 => v0y
    case 1 => v1y
    case 2 => v2y
    case 3 => v3y
    case n => excep("Index " + n.toString + " out of range. Only 4 vertices in rectangle.")
  }

  final override def vertsArrayX: Array[Double] = Array(v0x, v1x, v2x, v3x)
  final override def vertsArrayY: Array[Double] = Array(v0y, v1y, v2y, v3y)
  final override def vertsForeach[U](f: Pt2 => U): Unit = { f(v0); f(v1); f(v2); f(v3); ()}
  final override def vertsTailForeach[U](f: Pt2 => U): Unit = { f(v1); f(v2); f(v3); () }
  override def vertPairsTailForeach[U](f: (Double, Double) => U): Unit = { f(v1x, v1y); f(v2x, v2y); f(v3x, v3y); () }

  def diag1: LineSeg = LineSeg(v2, v0)
  def diag2: LineSeg = LineSeg(v3, v1)
  @inline def diags: LineSegs = LineSegs(diag1, diag2)

  /** Translate 2D geometric transformation on a Rectangle returns a Rectangle. */
  override def slate(offset: Vec2Like): Rectangle = Rectangle.sd2sd4(sd1Cen.slate(offset), sd3Cen.slate(offset), width2)

  /** Translate 2D geometric transformation on a Rectangle returns a Rectangle. */
  override def slateXY(xDelta: Double, yDelta: Double): Rectangle =
    Rectangle.sd2sd4(sd1Cen.addXY(xDelta, yDelta), sd3Cen.addXY(xDelta, yDelta), width2)

  /** Uniform scaling 2D geometric transformation on a Rectangle returns a Rectangle. */
  override def scale(operand: Double): Rectangle = Rectangle.sd2sd4(sd1Cen.scale(operand), sd3Cen.scale(operand), width2 * operand)

  /** Mirror, reflection 2D geometric transformation across the X axis on a Rectangle, returns a Rectangle. */
  override def negY: Rectangle = Rectangle.sd2sd4(sd1Cen.negY, sd3Cen.negY, width2)

  /** Mirror, reflection 2D geometric transformation across the X axis on a Rectangle, returns a Rectangle. */
  override def negX: Rectangle = Rectangle.sd2sd4(sd1Cen.negX, sd3Cen.negX, width2)

  override def prolign(matrix: ProlignMatrix): Rectangle = Rectangle.s2s4v1(sd1Cen.prolign(matrix), sd3Cen.prolign(matrix), v0.prolign(matrix))

  override def rotate90: Rectangle = Rectangle.sd2sd4(sd1Cen.rotate90, sd3Cen.rotate90, width2)
  override def rotate180: Rectangle = Rectangle.sd2sd4(sd1Cen.rotate180, sd3Cen.rotate180, width2)
  override def rotate270: Rectangle = Rectangle.sd2sd4(sd1Cen.rotate270, sd3Cen.rotate270, width2)

  override def reflect(lineLike: LineLike): Rectangle = Rectangle.sd2sd4(sd1Cen.reflect(lineLike), sd3Cen.reflect(lineLike), width2)

  override def rotate(angle: AngleVec): Rectangle = Rectangle.sd2sd4(sd1Cen.rotate(angle), sd3Cen.rotate(angle), width2)

  override def scaleXY(xOperand: Double, yOperand: Double): Rectangle =
    Rectangle.s2s4v1(sd1Cen.xyScale(xOperand, yOperand), sd3Cen.xyScale(xOperand, yOperand), v0.xyScale(xOperand, yOperand))
}

/** Companion object fot the Rectangle trait. Contains [[Rectangle.RectangleImp]] the implementation class for non specialised rectangles. It also
 *  contains various factory methods that delegate to the [[Rectangle.RectangleImp]] class. */
object Rectangle
{
  def apply(width: Double, height: Double, rotation: AngleVec, cen: Pt2 = Pt2Z): Rectangle =
  { val s2Cen: Pt2 = cen.addX(width / 2).rotate(rotation)
    val s4Cen: Pt2 = cen.subX(width / 2).rotate(rotation)
    ??? //new RectangleImp(s2Cen.x, s2Cen.y, s4Cen.x, s4Cen.y, height)
  }

  def sd2sd4(sd2Cen: Pt2, sd4Cen: Pt2, height: Double): Rectangle =
    ??? //new RectangleImp(sd2Cen.x, sd2Cen.y, sd4Cen.x, sd4Cen.y, height)

  def s2s4v1(s2Cen: Pt2, s4Cen: Pt2, v1: Pt2): Rectangle =
    ??? //new RectangleImp(s2Cen.x, s2Cen.y, s4Cen.x, s4Cen.y, s2Cen.distTo(v1) * 2)

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
    val offset = hAngle.toVec2(height * 0.5)
    PolygonGen(centreLine.pStart + offset, centreLine.pEnd + offset, centreLine.pEnd - offset, centreLine.pStart - offset)
  }

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
  final class RectangleImp(val unsafeArray: Array[Double]) extends Rectangle//S2S4
  { override type ThisT = RectangleImp
    override def unsafeFromArray(array: Array[Double]): RectangleImp = new RectangleImp(array)

    //val sd1CenX: Double, val sd1CenY: Double, val sd3CenX: Double, val sd3CenY: Double, val width2: Double
    override def vertsTrans(f: Pt2 => Pt2): RectangleImp = RectangleImp.s2s4v1(f(sd1Cen), f(sd3Cen), f(v0))

   // override def productArity: Int = 5
   // override def productElement(n: Int): Any = ???

    override def alignAngle: AngleVec = ???
  }

  object RectangleImp
  {
    def s2s4v1(s2Cen: Pt2, s4Cen: Pt2, v1: Pt2): RectangleImp =
      ??? //new RectangleImp(s2Cen.x, s2Cen.y, s4Cen.x, s4Cen.y, s2Cen.distTo(v1) * 2)
  }
}