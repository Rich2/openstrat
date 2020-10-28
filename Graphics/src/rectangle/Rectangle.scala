/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pWeb._

/** The Rectangle trait defines 4 vertices v0, v1, v2 and v3. The leaf classes of this class may or may not be squares and may or may not be aligned
 *  to the X and Y Axes. You can build a Rectangle using the factory methods in the Rectangle companion object. However if your rectangle is a aligned
 *  to the X and Y axis prefer the factory methods on the companion object of the shorter named [[Rect]] trait. */
trait Rectangle extends Polygon4Plus
{ final override def vertsNum: Int = 4

  def xLs3Cen: Double
  def yLs3Cen: Double
  def ls3Cen: Vec2

  /** length from v1 to v2 and v3 to v4. */
  def width1: Double

  /** length from v2 to v3 and v03 to v1. */
  def width2: Double

  override def fill(fillColour: Colour): RectangleFill = RectangleFill(this, fillColour)
  override def fillHex(intValue: Int): RectangleFill = RectangleFill(this, Colour(intValue))
  override def draw(lineColour: Colour, lineWidth: Double): RectangleDraw = RectangleDraw(this, lineWidth, lineColour)

  override def ptsArray: Array[Double] = Array(xCen, yCen, x1, y1, x2, y2, x3, y3, x4, y4)

  def v0Mid1: Vec2 = v1.midPtTo(v2)
  def v1Mid2: Vec2 = v2.midPtTo(v3)
  def v2Mid3: Vec2 = v3.midPtTo(v4)
  def v3Mid0: Vec2 = v4.midPtTo(v1)

  def rotation: Angle
  def widthAttrib: WidthAtt = WidthAtt(width1)
  def heightAttrib: HeightAtt = HeightAtt(width2)
  def xAttrib: XAttrib = XAttrib(x4)
  def yAttrib: YAttrib = YAttrib(y4)
  override def attribs: Arr[XANumeric] = Arr(widthAttrib, heightAttrib, xAttrib, yAttrib)

  @inline final override def vert(index: Int): Vec2 = index match
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
  final override def foreachVert(f: Vec2 => Unit): Unit = { f(v1); f(v2); f(v3); f(v4) }
  final override def foreachVertTail[U](f: Vec2 => U): Unit = { f(v2); f(v3); f(v4); () }
  override def foreachPairTail[U](f: (Double, Double) => U): Unit = { f(x2, y2); f(x3, y3); f(x4, y4); () }

  def diag1: LineSeg = LineSeg(v3, v1)
  def diag2: LineSeg = LineSeg(v4, v2)
  @inline def diags: LineSegs = LineSegs(diag1, diag2)

  /** Translate geometric transformation on a Rectangle returns a Rectangle. */
  override def slate(offset: Vec2): Rectangle = Rectangle.cenV0V1(cen + offset, v1 + offset, v2 + offset)

  /** Translate geometric transformation on a Rectangle returns a Rectangle. */
  override def slate(xOffset: Double, yOffset: Double): Rectangle =
    Rectangle.cenV0V1(cen.addXY(xOffset, yOffset), v1.addXY(xOffset, yOffset), v2.addXY(xOffset, yOffset))

  /** Uniform scaling transformation on a Rectangle returns a Rectangle. */
  override def scale(operand: Double): Rectangle = Rectangle.cenV0V1(cen * operand, v1 * operand, v2 * operand)

  /** Mirror, reflection transformation across the X axis on a Rectangle, returns a Rectangle. */
  override def negY: Rectangle = Rectangle.cenV0V1(cen.negY, v1.negY, v2.negY)

  /** Mirror, reflection transformation across the X axis on a Rectangle, returns a Rectangle. */
  override def negX: Rectangle = Rectangle.cenV0V1(cen.negX, v1.negX, v2.negX)

  /** Rotate 90 degrees anti clockwise or rotate 270 degrees clockwise 2D geometric transformation on a Rectangle, returns a Rectangle. The return type
   * will be narrowed in sub traits / classes. */
  override def rotate90: Rectangle = Rectangle.cenV0V1(cen.rotate90, v1.rotate90, v2.rotate90)

  /** Rotate 180 degrees 2D geometric transformation on a Rectangle, returns a Rectangle. The return type will be narrowed in sub traits / classes. */
  override def rotate180: Rectangle = Rectangle.cenV0V1(cen.rotate180, v1.rotate180, v2.rotate180)

  /** Rotate 270 degrees anti clockwise or rotate 90 degrees clockwise 2D geometric transformation on a Rectangle, returns a Rectangle. The return type
   * will be narrowed in sub traits / classes. */
  override def rotate270: Rectangle = Rectangle.cenV0V1(cen.rotate270, v1.rotate270, v2.rotate270)

  override def prolign(matrix: ProlignMatrix): Rectangle = Rectangle.cenV0V1(cen.prolign(matrix), v1.prolign(matrix), v2.prolign(matrix))

  override def reflect(lineLike: LineLike): Rectangle = Rectangle.cenV0V1(cen.reflect(lineLike), v1.reflect(lineLike), v2.reflect(lineLike))

  override def rotate(angle: Angle): Rectangle = Rectangle.cenV0V1(cen.rotate(angle), v1.rotate(angle), v2.rotate(angle))

  override def xyScale(xOperand: Double, yOperand: Double): Rectangle =
    Rectangle.cenV0V1(cen.xyScale(xOperand, yOperand), v1.xyScale(xOperand, yOperand), v2.xyScale(xOperand, yOperand))

  override def slateTo(newCen: Vec2): Rectangle = ???
}

/** This perhaps should be changed to Rectangle. Some methods need renaming or possibly even deleting */
object Rectangle
{
  def apply(width: Double, height: Double, rotation: Angle, cen: Vec2 = Vec2Z): Rectangle =
  { val v0 = cen.addXY(width / 2, height / 2).rotate(rotation)
    val v1 = cen.addXY(width / 2, - height / 2).rotate(rotation)
    new RectangleImp(cen.x, cen.y, v0.x, v0.y, v1.x, v1.y)
  }

  def cenV0V1(cen: Vec2, v0: Vec2, v1: Vec2): Rectangle = new RectangleImp(cen.x, cen.y, v0.x, v0.y, v1.x, v1.y)

  def curvedCorners(width: Double, height: Double, radius: Double, cen: Vec2 = Vec2Z): PolyCurve =
  { val w = width / 2
    val h = height / 2
    val s1 = PolyCurve(
        LineTail(w - radius,          h), ArcTail(w - radius vv h - radius, w vv h -radius),
        LineTail(w,          radius - h), ArcTail(w - radius vv radius - h, w - radius vv -h),
        LineTail(radius - w,         -h), ArcTail(radius - w vv radius - h, -w vv radius -h),
        LineTail(- w,        h - radius), ArcTail(radius - w vv h - radius, radius - w vv h))
     s1.slate(cen)
  }

  def curvedCornersCentred(width: Double, height: Double, radius: Double, posn: Vec2 = Vec2Z): PolyCurveCentred =
    PolyCurveCentred(posn, curvedCorners(width, height, radius).slate(posn))
  def curvedGoldenRatio(height: Double, radius: Double, posn: Vec2 = Vec2Z): PolyCurve =
    curvedCorners(height * Phi, height, radius, posn)
  def curvedGoldenRatioCentred(height: Double, radius: Double, posn: Vec2 = Vec2Z): PolyCurveCentred =
    curvedCornersCentred(height * Phi, height, radius, posn)


  def fromAxis(centreLine: LineSeg, height: Double): PolygonImp =
  { val hAngle: Angle = centreLine.angle
    val offset: Vec2 = hAngle.toVec2(height * 0.5)
    PolygonImp(centreLine.pStart + offset, centreLine.pEnd + offset, centreLine.pEnd - offset, centreLine.pStart - offset)
  }

  implicit val slateImplicit: Slate[Rectangle] = (obj: Rectangle, offset: Vec2) => obj.slate(offset)
  implicit val scaleImplicit: Scale[Rectangle] = (obj: Rectangle, operand: Double) => obj.scale(operand)
  implicit val rotateImplicit: Rotate[Rectangle] = (obj: Rectangle, angle: Angle) => obj.rotate(angle)
  implicit val prolignImplicit: Prolign[Rectangle] = (obj, matrix) => obj.prolign(matrix)

  implicit val reflectAxesImplicit: TransAxes[Rectangle] = new TransAxes[Rectangle]
  { override def negYT(obj: Rectangle): Rectangle = obj.negY
    override def negXT(obj: Rectangle): Rectangle = obj.negX
    override def rotate90T(obj: Rectangle): Rectangle = obj.rotate90
    override def rotate180T(obj: Rectangle): Rectangle = obj.rotate180
    override def rotate270T(obj: Rectangle): Rectangle = obj.rotate270
  }

  /** A rectangle class that has position and may not be aligned to the X and Y axes. */
  final class RectangleImp(val xCen: Double, val yCen: Double, val x1: Double, val y1: Double, val x2: Double, val y2: Double) extends RectCenV0
  { type ThisT = RectangleImp
    override def v2: Vec2 = Vec2(x2, y2)
    
    override def fTrans(f: Vec2 => Vec2): RectangleImp = RectangleImp.cenV0V1(f(cen), f(v1), f(v2))

    override def productArity: Int = 5

    override def productElement(n: Int): Any = ???

    override def rotation: Angle = (v1 - v4).angle
    
    /** Translate geometric transformation on a RectangleImp returns a RectangleImp. */
    override def slate(offset: Vec2): RectangleImp = RectangleImp.cenV0V1(cen + offset, v1 + offset, v2 + offset)
    
    override def rotate(angle: Angle): RectangleImp = ???
   // override def reflectX: RectImp = RectImp.v0v1(v1.reflectX, v0.reflectX, width)
   // override def reflectY: RectImp = RectImp.v0v1(v3.reflectY, v2.reflectY, width)
    //override def reflectXOffset(yOffset: Double): RectImp = RectImp.v0v1(v1.reflectXOffset(yOffset), v0.reflectXOffset(yOffset), width)
   // override def reflectYOffset(xOffset: Double): RectImplement = RectImplement.v0v1(v1.reflectYOffset(xOffset), v0.reflectYOffset(xOffset), width)

    //override def reflect(line: Line): RectImp = RectImp.v0v1(v1.reflect(line), v0.reflect(line), width)

   // override def reflect(line: LineSeg): RectImp = ???

    //override def xyScale(xOperand: Double, yOperand: Double): Polygon = ???

    //override def fill(fillColour: Colour): ShapeFill = ???

   // override def draw(lineWidth: Double, lineColour: Colour): ShapeDraw = ???
  }

  object RectangleImp
  {
    /** The standard factory method for producing a Rect from width, height, position and rotation. position and rotation take default values */
    def apply(width: Double, height: Double, cen: Vec2, rotation: Angle = 0.degs): RectangleImp =
    { val v0 = Vec2(cen.x + width, cen.y + height).rotate(rotation)
      val v1 = Vec2(cen.x + width, cen.y - height).rotate(rotation)
      new RectangleImp(cen.x, cen.y, v0.x, v0.y, v1.x, v1.y)
    }
    /** The standard factory method for producing a Rect from width, height, the x position, the y position  and the rotation. Rotation has a default
     *  value of 0 degrees. If you want the default position of a rectangle centred at 0, 0, then use the apply method. */
    def xy(width: Double, height: Double, xCen: Double, yCen: Double, rotation: Angle = 0.degs): RectangleImp =
    { val v0 = Vec2(xCen + width, yCen + height).rotate(rotation)
      val v1 = Vec2(xCen + width, yCen - height).rotate(rotation)
      new RectangleImp(xCen,  yCen, v0.x, v0.y, v1.x, v1.y)
    }

    /** Factory method for creating a [[RectangleImp]] rectangle from the points cen, v0 and v1 */    
    def cenV0V1(cen: Vec2, v0: Vec2, v1: Vec2): RectangleImp = new RectangleImp(cen.x, cen.y, v0.x, v0.y, v1.x, v1.y)
  }
}