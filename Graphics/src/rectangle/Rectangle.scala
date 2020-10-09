/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pWeb._

/** The Rectangle trait defines 4 vertices v0, v1, v2 and v3. The leaf classes of this class may or may not be squares and may or may not be aligned
 *  to the X and Y Axes. You can build a Rectangle using the factory methods in the Rectangle companion object. However if your rectangle is a aligned
 *  to the X and Y axis prefer the factory methods on the companion object of the shorter named [[Rect]] trait. */
trait Rectangle extends Polygon
{ final override def length: Int = 4
  def x0: Double
  def y0: Double
  def x1: Double
  def y1: Double
  def v1: Vec2
  def x2: Double
  def y2: Double
  def v2: Vec2
  def x3: Double
  def y3: Double
  def v3: Vec2
  
  def xLs3Cen: Double
  def yLs3Cen: Double
  def ls3Cen: Vec2
  
  /** length from v1 to v2 and v3 to v4. */
  def width1: Double
  
  /** length from v2 to v3 and v03 to v1. */
  def width2: Double
  
  override def fill(fillColour: Colour): RectangleFill = RectangleFill(this, fillColour)
  override def draw(lineWidth: Double, lineColour: Colour): RectangleDraw = RectangleDraw(this, lineWidth, lineColour)
  
  override def ptsArray: Array[Double] = Array(x0, y0, x1, y1, x2, y2, x3, y3)
  
  def v0Mid1: Vec2 = v0.midPtTo(v1)
  def v1Mid2: Vec2 = v1.midPtTo(v2)
  def v2Mid3: Vec2 = v2.midPtTo(v3)
  def v3Mid0: Vec2 = v3.midPtTo(v0)
  
  def rotation: Angle
  def widthAttrib: WidthAtt = WidthAtt(width1)
  def heightAttrib: HeightAtt = HeightAtt(width2)
  def xAttrib: XAttrib = XAttrib(x3)
  def yAttrib: YAttrib = YAttrib(y3)
  override def attribs: Arr[XANumeric] = Arr(widthAttrib, heightAttrib, xAttrib, yAttrib)  
  
  @inline final override def apply(index: Int): Vec2 = index match 
  { case 0 => v0
    case 1 => v1
    case 2 => v2
    case 3 => v3
    case n => excep("Index: " + n.toString + " out of range. Only 4 vertices in a Rectangle.")  
  }

  override def xGet(index: Int): Double = index match
  { case 0 => x0
    case 1 => x1
    case 2 => x2
    case 3 => x3
    case n => excep("Index " + n.toString + " out of range. Only 4 vertices in rectangle.")
  }

  override def yGet(index: Int): Double = index match
  { case 0 => y0
    case 1 => y1
    case 2 => y2
    case 3 => y3
    case n => excep("Index " + n.toString + " out of range. Only 4 vertices in rectangle.")
  }

  final override def elem1sArray: Array[Double] = Array(x0, x1, x2, x3)
  final override def elem2sArray: Array[Double] = Array(y0, y1, y2, y3)
  final override def foreach[U](f: Vec2 => U): Unit = { f(v0); f(v1); f(v2); f(v3); () }
  final override def foreachTail[U](f: Vec2 => U): Unit = { f(v1); f(v2); f(v3); () }
  override def foreachPairTail[U](f: (Double, Double) => U): Unit = { f(x1, y1); f(x2, y2); f(x3, y3); () }
  
  /** Translate geometric transformation on a Rectangle returns a Rectangle. */
  override def slate(offset: Vec2): Rectangle = Rectangle.cenV0V1(cen + offset, v0 + offset, v1 + offset)

  /** Translate geometric transformation on a Rectangle returns a Rectangle. */
  override def slate(xOffset: Double, yOffset: Double): Rectangle =
    Rectangle.cenV0V1(cen.addXY(xOffset, yOffset), v0.addXY(xOffset, yOffset), v1.addXY(xOffset, yOffset))

  /** Uniform scaling transformation on a Rectangle returns a Rectangle. */
  override def scale(operand: Double): Rectangle = Rectangle.cenV0V1(cen * operand, v0 * operand, v1 * operand)

  /** Mirror, reflection transformation across the X axis on a Rectangle, returns a Rectangle. */
  override def negY: Rectangle = Rectangle.cenV0V1(cen.negY, v0.negY, v1.negY)

  /** Mirror, reflection transformation across the X axis on a Rectangle, returns a Rectangle. */
  override def negX: Rectangle = Rectangle.cenV0V1(cen.negX, v0.negX, v1.negX)

  /** Rotate 90 degrees anti clockwise or rotate 270 degrees clockwise 2D geometric transformation on a Rectangle, returns a Rectangle. The return type
   * will be narrowed in sub traits / classes. */
  override def rotate90: Rectangle = Rectangle.cenV0V1(cen.rotate90, v0.rotate90, v1.rotate90)

  /** Rotate 180 degrees 2D geometric transformation on a Rectangle, returns a Rectangle. The return type will be narrowed in sub traits / classes. */
  override def rotate180: Rectangle = Rectangle.cenV0V1(cen.rotate180, v0.rotate180, v1.rotate180)

  /** Rotate 270 degrees anti clockwise or rotate 90 degrees clockwise 2D geometric transformation on a Rectangle, returns a Rectangle. The return type
   * will be narrowed in sub traits / classes. */
  override def rotate270: Rectangle = Rectangle.cenV0V1(cen.rotate270, v0.rotate270, v1.rotate270)

  override def prolign(matrix: ProlignMatrix): Rectangle = Rectangle.cenV0V1(cen.prolign(matrix), v0.prolign(matrix), v1.prolign(matrix))

  override def reflect(lineLike: LineLike): Rectangle = Rectangle.cenV0V1(cen.reflect(lineLike), v0.reflect(lineLike), v1.reflect(lineLike))

  override def rotate(angle: Angle): Rectangle = Rectangle.cenV0V1(cen.rotate(angle), v0.rotate(angle), v1.rotate(angle))

  override def xyScale(xOperand: Double, yOperand: Double): Rectangle =
    Rectangle.cenV0V1(cen.xyScale(xOperand, yOperand), v0.xyScale(xOperand, yOperand), v1.xyScale(xOperand, yOperand))

  override def slateTo(newCen: Vec2): Rectangle = ???
}

/** This perhaps should be changed to Rectangle. Some methods need renaming or possibly even deleting */
object Rectangle
{
  def apply(width: Double, height: Double, cen: Vec2 = Vec2Z, rotation: Angle = 0.degs): Rectangle =
  { val v0 = cen.addXY(width / 2, height / 2).rotate(rotation)
    val v1 = cen.addXY(width / 2, - height / 2).rotate(rotation)
    new RectangleImp(cen.x, cen.y, v0.x, v0.y, v1.x, v1.y)
  }
  
  /** Defaults to a centre of x = 0, y = 0 and then defaults to a height of 1.0. Clockwise, topLeft is vertice 0. */
  def applyOld(width: Double, height: Double = 1, cen: Vec2 = Vec2Z): PolygonImp =
  { val x = cen.x; val y = cen.y
    PolygonImp(
        x - width / 2 vv y + height / 2,
        x + width / 2 vv y + height / 2,
        x + width / 2 vv y - height / 2,
        x - width / 2 vv y - height / 2
    )
  }
  
  def cenV0V1(cen: Vec2, v0: Vec2, v1: Vec2): Rectangle = new RectangleImp(cen.x, cen.y, v0.x, v0.y, v1.x, v1.y)
  
  /** A rectangle measured from its top left */
  def fromTL(width: Double, height: Double, tlVec: Vec2 = Vec2Z): PolygonImp = PolygonImp(
      tlVec.x         vv tlVec.y,
      tlVec.x + width vv tlVec.y,
      tlVec.x + width vv tlVec.y - height,
      tlVec.x         vv tlVec.y -height)
   
  /*def fromBL(width: Double, height: Double, v: Vec2): PolygonImp = PolygonImp(
      v.x vv v.y + height,
      v.x + width vv v.y + height,
      v.x + width vv v.y,
      v.x vv v.y)*/

  /** Measured from bottom centre */      
  def fromBC(width: Double, height: Double, bottomCentre: Vec2 = Vec2Z): PolygonImp =
  {
    val x = bottomCentre.x; val y = bottomCentre.y
    PolygonImp(
        x - width / 2 vv y + height ,
        x + width / 2 vv y + height ,
        x + width / 2 vv y,
        x - width / 2 vv y)
  } 
  
  def goldenRatio(height: Double): Rectangle = apply(Phi * height, height)
  //@deprecated def crossOld(width: Double, height: Double, barWidth: Double): ArrOld[Polygon] = ArrOld(apply(width, barWidth), apply(barWidth, height))
  def cross(width: Double, height: Double, barWidth: Double): Arr[Polygon] = Arr(apply(width, barWidth), apply(barWidth, height))
  
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
  def colouredBordered(height: Double, colour: Colour, lineWidth: Double = 1): PolygonCompound =
    goldenRatio(height).fillDraw(colour, lineWidth, colour.contrast)
  
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
  final class RectangleImp(val xCen: Double, val yCen: Double, val x0: Double, val y0: Double, val x1: Double, val y1: Double) extends RectCenV0
  { type ThisT = RectangleImp
    override def v1: Vec2 = Vec2(x1, y1)
    
    override def fTrans(f: Vec2 => Vec2): RectangleImp = RectangleImp.cenV0V1(f(cen), f(v0), f(v1))

    override def productArity: Int = 5

    override def productElement(n: Int): Any = ???

    override def rotation: Angle = (v0 - v3).angle
    
    /** Translate geometric transformation on a RectangleImp returns a RectangleImp. */
    override def slate(offset: Vec2): RectangleImp = RectangleImp.cenV0V1(cen + offset, v0 + offset, v1 + offset)
    
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