/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pWeb._

/** Rectangle trait. The leaf classes of this class may or may not be squares and may or may not be aligned to the X and Y Axes. */
trait Rect extends Rectangular with Polygon
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
  def rotation: Angle
  def widthAttrib: WidthAtt = WidthAtt(width)
  def heightAttrib: HeightAtt = HeightAtt(height)
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

  /** Translate geometric transformation on a Shape returns a Shape. */
  override def slate(offset: Vec2): Rect = ???

  /** Translate geometric transformation. */
  override def slate(xOffset: Double, yOffset: Double): Rect = ???

  /** Uniform scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves Circles and
   * Squares. Use the xyScale method for differential scaling. */
  override def scale(operand: Double): Rect = ???

  /** Mirror, reflection transformation across the line y = yOffset, which is parallel to the X axis. */
  override def reflectXOffset(yOffset: Double): Rect = ???

  /** Mirror, reflection transformation of a Rect across the line x = xOffset, which is parallel to the X axis, returns a Rect. */
  override def reflectYOffset(xOffset: Double): Rect = ???

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def reflectX: Rect = ???

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def reflectY: Rect = ???

  override def prolign(matrix: ProlignMatrix): Rect = ???

  override def reflect(line: Line): Rect = ???

  override def reflect(line: LineSeg): Rect = ???

  override def rotateRadians(radians: Double): Rect = ???
  //override def xyScale(xOperand: Double, yOperand: Double): Polygon = ???
}

/** This perhaps should be changed to Rectangle. Some methods need renaming or possibly even deleting */
object Rect
{
  def apply(width: Double, height: Double, cen: Vec2 = Vec2Z, rotation: Angle = 0.degs): Rect =
  { val v0 = cen.addXY(width / 2, height / 2).rotate(rotation)
    val v1 = cen.addXY(width / 2, - height / 2).rotate(rotation)
    new RectImplement(v0.x, v0.y, v1.x, v1.y, width)
  }
  
  /** Defaults to a centre of x = 0, y = 0 and then defaults to a height of 1.0. Clockwise, topLeft is vertice 0. */
  def applyOld(width: Double, height: Double = 1, cen: Vec2 = Vec2Z): PolygonGen =
  { val x = cen.x; val y = cen.y
    PolygonGen(
        x - width / 2 vv y + height / 2,
        x + width / 2 vv y + height / 2,
        x + width / 2 vv y - height / 2,
        x - width / 2 vv y - height / 2
    )
  }
  
  def v0v1(v0: Vec2, v1: Vec2, width: Double): Rect = new RectImplement(v0.x, v0.y, v1.x, v1.y, width)

  def scale(widthOverHeightRatio: Double, scale: Double, cen: Vec2 = Vec2Z): PolygonGen = applyOld(widthOverHeightRatio * scale, scale, cen)
  
  /** A rectangle measured from its top left */
  def fromTL(width: Double, height: Double, tlVec: Vec2 = Vec2Z): PolygonGen = PolygonGen(
      tlVec.x         vv tlVec.y,
      tlVec.x + width vv tlVec.y,
      tlVec.x + width vv tlVec.y - height,
      tlVec.x         vv tlVec.y -height)         
   
  def fromBL(width: Double, height: Double, v: Vec2): PolygonGen = PolygonGen(
      v.x vv v.y + height,
      v.x + width vv v.y + height,
      v.x + width vv v.y,
      v.x vv v.y)

  /** Measured from bottom centre */      
  def fromBC(width: Double, height: Double, bottomCentre: Vec2 = Vec2Z): PolygonGen =
  {
    val x = bottomCentre.x; val y = bottomCentre.y
    PolygonGen(
        x - width / 2 vv y + height ,
        x + width / 2 vv y + height ,
        x + width / 2 vv y,
        x - width / 2 vv y)
  } 
  
  def gRatio(height: Double): PolygonGen = applyOld(Phi * height, height)
  //@deprecated def crossOld(width: Double, height: Double, barWidth: Double): ArrOld[Polygon] = ArrOld(apply(width, barWidth), apply(barWidth, height))
  def cross(width: Double, height: Double, barWidth: Double): Arr[Polygon] = Arr(applyOld(width, barWidth), applyOld(barWidth, height))
  
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
  def colouredBordered(height: Double, colour: Colour, lineWidth: Double = 1): PolygonFillDraw =
    gRatio(height).fillDrawOld(colour, lineWidth, colour.contrast)
  
  def fromAxis(centreLine: LineSeg, height: Double): PolygonGen =
  { val hAngle: Angle = centreLine.angle
    val offset: Vec2 = hAngle.toVec2(height * 0.5)
    PolygonGen(centreLine.pStart + offset, centreLine.pEnd + offset, centreLine.pEnd - offset, centreLine.pStart - offset)
  }

  /** A rectangle class that has position and may not be aligned to the X and Y axes. */
  final class RectImplement(val x0: Double, val y0: Double, val x1: Double, val y1: Double, val width: Double) extends RectV0V1
  { type ThisT = RectImplement
    override def height: Double = (v1 - v2).magnitude
    override def fTrans(f: Vec2 => Vec2): RectImplement = RectImplement.points(f(cen), f(v0), f(v1))

    override def productArity: Int = 5

    override def productElement(n: Int): Any = ???

    override def rotation: Angle = (v0 - v3).angle

    override def rotateRadians(radians: Double): RectImplement = ???
    override def reflectX: RectImplement = RectImplement.v0v1(v1.reflectX, v0.reflectX, width)
    override def reflectY: RectImplement = RectImplement.v0v1(v1.reflectY, v0.reflectY, width)
    override def reflectXOffset(yOffset: Double): RectImplement = RectImplement.v0v1(v1.reflectXOffset(yOffset), v0.reflectXOffset(yOffset), width)
    override def reflectYOffset(xOffset: Double): RectImplement = RectImplement.v0v1(v1.reflectYOffset(xOffset), v0.reflectYOffset(xOffset), width)

    override def reflect(line: Line): RectImplement = RectImplement.v0v1(v1.reflect(line), v0.reflect(line), width)

    override def reflect(line: LineSeg): RectImplement = ???

    override def xyScale(xOperand: Double, yOperand: Double): Polygon = ???

    //override def fill(fillColour: Colour): ShapeFill = ???

    override def drawOld(lineWidth: Double, lineColour: Colour): ShapeDraw = ???
  }

  object RectImplement
  {
    /** The standard factory method for producing a Rect from width, height, position and rotation. position and rotation take default values */
    def apply(width: Double, height: Double, posn: Vec2, rotation: Angle = 0.degs): RectImplement = new RectImplement(posn.x, 0, 0, 0, width)

    /** The standard factory method for producing a Rect from width, height, the x position, the y position  and the rotation. Rotation has a default
     *  value of 0 degrees. If you want the default position of a rectangle centred at 0, 0, then use the apply method. */
    def xy(width: Double, height: Double, xCen: Double, yCen: Double, rotation: Angle = 0.degs): RectImplement = new RectImplement(xCen, 0, 0, 0, 0)

    /** Factory method for creating a [[RectImplement]] rectangle from the points v0, v1, and the width. */
    def v0v1(v0: Vec2, v1: Vec2, width: Double): RectImplement = new RectImplement(v0.x, v0.y, v1.x, v1.y, width)
    def points(cen: Vec2, topRight: Vec2, bottomRight: Vec2): RectImplement = ??? // new Rect(cen.x, cen.y, topRight.x, topRight.y, bottomRight.x, bottomRight.y)
  }
}