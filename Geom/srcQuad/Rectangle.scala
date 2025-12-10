/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pWeb.*

/** The Rectangle trait defines 4 vertices v0, v1, v2 and v3. The leaf classes of this class may or may not be squares and may or may not be aligned to the X
 * and Y Axes. You can build a Rectangle using the factory methods in the Rectangle companion object. However, if your rectangle is a aligned to the X and Y
 * axis prefer the factory methods on the companion object of the shorter named [[Rect]] trait. For SVG purposes this will be output as a polygon to avoid the
 * transforms to rotate a rectangle.W */
trait Rectangle extends ShapeCentred, Quadrilateral
{ type ThisT <: Rectangle
  override def typeStr: String = "Rectangle"

  override def vertsTrans(f: Pt2 => Pt2): Rectangle = Rectangle.from3(f(v0), f(v1), f(v2))
  
  /** length from v1 to v2 and v3 to v4. */
  def width1: Double = v3.distTo(v0)

  /** length from v2 to v3 and v03 to v1. */
  def width2: Double = v1.distTo(v2)

  @inline final override def cen: Pt2 = Pt2(cenX, cenY)

  /** The rotation of this square from alignment with the X and Y axes. */
  def rotation: AngleVec
  
  override def fill(fillfacet: FillFacet): RectangleFill = RectangleFill(this, fillfacet)
  override def fillInt(intValue: Int): RectangleFill = RectangleFill(this, Colour(intValue))
  override def draw(lineWidth: Double, lineColour: Colour): RectangleDraw = RectangleDraw(this, lineWidth, lineColour)

  final def alignAngle: Angle = (v0 >> v1).angle

  def widthAtt: XAtt = WidthAtt(width1)
  def heightAtt: HeightAtt = HeightAtt(width2)

  /** The X component of the bottom left point is used. This becomes the top left point in SVG space. */
  def xAttrib: XXmlAtt = XXmlAtt(v3x)

  /** The Y component of the bottom left point is negated to convert to SVG space and the SVG shape origin of the top left vertex. */
  def yAttrib: YXmlAtt = YXmlAtt(-v2y)
  
  override def slate(operand: VecPt2): Rectangle = vertsTrans(_.slate(operand))
  override def slate(xOperand: Double, yOperand: Double): Rectangle = vertsTrans(_.slate(xOperand, yOperand))
  override def slateX(xOperand: Double): Rectangle = vertsTrans(_.slateX(xOperand))
  override def slateY(yOperand: Double): Rectangle = vertsTrans(_.slateY(yOperand))
  override def scale(operand: Double): Rectangle = vertsTrans(_.scale(operand))
  override def negX: Rectangle = vertsTrans(_.negX)
  override def negY: Rectangle = vertsTrans(_.negY)
  override def prolign(matrix: AxlignMatrix): Rectangle = vertsTrans(_.prolign(matrix))
  override def rotate90: Rectangle = vertsTrans(_.rotate90)
  override def rotate180: Rectangle = vertsTrans(_.rotate180)
  override def rotate270: Rectangle = vertsTrans(_.rotate270)
  override def reflect(lineLike: LineLike): Rectangle = vertsTrans(_.reflect(lineLike))
  override def rotate(rotation: AngleVec): Rectangle = vertsTrans(_.rotate(rotation))
  override def scaleXY(xOperand: Double, yOperand: Double): Rectangle = vertsTrans(_.xyScale(xOperand, yOperand))

  final override def cenX: Double = v2x \/ v0x
  final override def cenY: Double = v2y \/ v0y
  final override def v3x: Double = v0x + v2x - v1x
  final override def v3y: Double = v0y + v2y - v1y

  final override def elem(index: Int): Pt2 = index %% 4 match
  { case 0 => Pt2(v0x, v0y)
    case 1 => Pt2(v1x, v1y)
    case 2 => Pt2(v2x, v2y)
    case _ => v3
  }
}

/** Companion object for the Rectangle trait. Contains [[Rectangle.RectangleGen]] the implementation class for non-specialised rectangles. It also contains
 * various factory methods that delegate to the [[Rectangle.RectangleGen]] class. */
object Rectangle
{  /** apply factory method for rectangle takes the width, height, rotation from alignment with the axes and a centre point. the default value for the centre
   * point is the origin. */
  def apply(width: Double, height: Double, rotation: AngleVec, cen: Pt2 = Origin2): Rectangle =
  { val v0 = Pt2(width, height).rotate(rotation).slate(cen)
    val v1 = Pt2(width, -height).rotate(rotation).slate(cen)
    val v2 = Pt2(-width, -height).rotate(rotation).slate(cen)
    from3(v0, v1, v2)
  }

  def apply(width: Double, height: Double, rotation: AngleVec, cenX: Double, cenY: Double): Rectangle = apply(width, height, rotation, Pt2(cenX, cenY))

  /** Factory apply method to create [[Rectangle]] from its first 3 vertices. */
  def from3(vt0: Pt2, vt1: Pt2, vt2: Pt2): Rectangle = new RectangleGen(vt0.x, vt0.y, vt1.x, vt1.y, vt2.x, vt2.y)

  /** Creates a [[Rectangle]] from axis 1. The default for axis 1 is the left right axis. */
  def axis1(sd4Cen: Pt2, sd2Cen: Pt2, height: Double): Rectangle = ???
  /*{ val rtVec: Vec2 = sd4Cen >/> sd2Cen
    val upVec: Vec2 = rtVec.angle.p90.toVec2(height) / 2
    val cen = sd4Cen \/ sd2Cen
    val verts = Pt2Arr(cen -rtVec + upVec, cen + rtVec + upVec, cen + rtVec - upVec, cen -rtVec - upVec)
    new RectangleGen(verts.arrayUnsafe)
  }*/

  def vecsCen(rtVec: Vec2, upVec: Vec2, cen: Pt2): Rectangle = ???// new RectangleGen(unsafeVecsCen(rtVec: Vec2, upVec: Vec2, cen))

  /** Creates Rectangle from 2 vectors and centre point. The 2 vectors are the half axies from the centre point to th e right and to the top. */
  //def unsafeVecsCen(rtVec: Vec2, upVec: Vec2, cen: Pt2): Array[Double] = ???
    //Pt2Arr(cen -rtVec + upVec, cen + rtVec + upVec, cen + rtVec - upVec, cen -rtVec - upVec).arrayUnsafe

//  def fromArray(array: Array[Double]): Rectangle = new RectangleGen(array)

  def curvedCorners(width: Double, height: Double, radius: Double, cen: Pt2 = Origin2): ShapeGenOld =
  { val w = width / 2
    val h = height / 2
    val s1 = ShapeGenOld(
        LineTail(w - radius,          h), ArcTail(w - radius,h - radius, w,h -radius),
        LineTail(w,          radius - h), ArcTail(w - radius,radius - h, w -radius,- h),
        LineTail(radius - w,         -h), ArcTail(radius - w,radius - h, -w,radius - h),
        LineTail(- w,        h - radius), ArcTail(radius - w,h - radius, radius - w,h))
     s1.slate(cen)
  }

  def curvedCornersCentred(width: Double, height: Double, radius: Double, posn: Pt2 = Origin2): PolyCurveCentred =
    PolyCurveCentred(posn, curvedCorners(width, height, radius).slate(posn))
  def curvedGoldenRatio(height: Double, radius: Double, posn: Pt2 = Origin2): ShapeGenOld =
    curvedCorners(height * Phi, height, radius, posn)
  def curvedGoldenRatioCentred(height: Double, radius: Double, posn: Pt2 = Origin2): PolyCurveCentred =
    curvedCornersCentred(height * Phi, height, radius, posn)

  def fromAxis(centreLine: LSeg2, height: Double): PolygonGen = ???
//  { val hAngle: Angle = centreLine.angle
//    val offset = (hAngle + 90.degsVec).toVec2(height * 0.5)
//    PolygonGen(centreLine.pStart + offset, centreLine.pEnd + offset, centreLine.pEnd - offset, centreLine.pStart - offset)
//  }

  def fromAxisRatio(centreLine: LSeg2, ratio: Double): PolygonGen = fromAxis(centreLine, centreLine.length * ratio)

  /** Implicit [[Slate2]] type class instance evidence for [[Rectangle]]. */
  given slate2Ev: Slate2[Rectangle] = new Slate2[Rectangle]
  { override def slate(obj: Rectangle, operand: VecPt2): Rectangle = obj.slate(operand)
    override def slateXY(obj: Rectangle, xOperand: Double, yOperand: Double): Rectangle = obj.slate(xOperand, yOperand)
    override def slateX(obj: Rectangle, xOperand: Double): Rectangle = obj.slateX(xOperand)
    override def slateY(obj: Rectangle, yOperand: Double): Rectangle = obj.slateY(yOperand)
  }

  /** Implicit [[Scale]] type class instance evidence for [[Rectangle]]. */ 
  given scaleEv: Scale[Rectangle] = (obj: Rectangle, operand: Double) => obj.scale(operand)
  
  /** Implicit [[Rotate]] type class instance evidence for [[Rectangle]]. */
  given rotateEv: Rotate[Rectangle] = (obj: Rectangle, angle: AngleVec) => obj.rotate(angle)
  
  /** Implicit [[Prolign]] type class instance evidence for [[Rectangle]]. */
  given prolignEv: Prolign[Rectangle] = (obj, matrix) => obj.prolign(matrix)
  
  /** Implicit [[Reflect]] type class instance evidence for [[Rectangle]]. */
  given reflectEv: Reflect[Rectangle] = (obj: Rectangle, lineLike: LineLike) => obj.reflect(lineLike)

  /** Implicit [[TransAxes]] type class instance evidence for [[Rectangle]]. */
  given transAxesEv: TransAxes[Rectangle] = new TransAxes[Rectangle]
  { override def negYT(obj: Rectangle): Rectangle = obj.negY
    override def negXT(obj: Rectangle): Rectangle = obj.negX
    override def rotate90(obj: Rectangle): Rectangle = obj.rotate90
    override def rotate180(obj: Rectangle): Rectangle = obj.rotate180
    override def rotate270(obj: Rectangle): Rectangle = obj.rotate270
  }

  /** A rectangle class that has position and may not be aligned to the X and Y axes. */
  final class RectangleGen(val v0x: Double, val v0y: Double, val v1x: Double, val v1y: Double, val v2x: Double, val v2y: Double) extends Rectangle
  { override type ThisT = RectangleGen
    override def vertsTrans(f: Pt2 => Pt2): RectangleGen = RectangleGen.from3(f(v0), f(v1), f(v2))
    override def rotation: AngleVec = ???
  }

  object RectangleGen
  { /** Factory method to construct [[RectangleGen]] from the vertices 0, 1 and 2. */
    def from3(v0: Pt2, v1: Pt2, v2: Pt2): RectangleGen = new RectangleGen(v0.x, v0.y, v1.x, v1.y, v2.x, v2.y)    
  }
}