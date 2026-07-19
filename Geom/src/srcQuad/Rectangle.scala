/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

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

  /** The rotation of this square from alignment with the X and Y axes. */
  def rotation: AngleVec

  /** the length of the diagonals of this [[Rectangle]]. */
  def diagLen: Double = ((v0x - v2x).squared + (v0y - v2y).squared).sqrt

  /** The axis from midpoint of side2 to midpoint of side 0. */
  def axis1: LSeg2 = LSeg2(v2x \/ v3x, v2y \/ v3y, v0x \/ v1x, v0y \/ v1y)

  /** The axis from midpoint of side1 to midpoint of side 3. */
  def axis2: LSeg2 = LSeg2(v2x \/ v1x, v2y \/ v1y, v3x \/ v0x, v3y \/ v0y)
  
  /** gives the diagonals of this rectangle, but as rectangles. this is because they maybe easier to scale than line widths. */
  def diagRectangles(childWidth: Double): RArr[Rectangle] =
  { val r1 = Rectangle.axis1(v2, v0 ,childWidth)
    val r2 = Rectangle.axis1(v3, v1 ,childWidth)
    RArr(r1, r2)
  }

  @inline final override def cen: Pt2 = Pt2(cenX, cenY)  
  override def fill(fillfacet: FillFacet): RectangleFill = RectangleFill(this, fillfacet)
  override def fillInt(intValue: Int): RectangleFill = RectangleFill(this, Colour(intValue))
  override def draw(lineWidth: Double, lineColour: Colour): RectangleDraw = RectangleDraw(this, lineWidth, lineColour)

  final def alignAngle: Angle = (v0 >> v1).angle
  
  /** Rotates this Rectangle about its centre. */
  def rotateCen(rotation: AngleVec): Rectangle = vertsTrans{vt => vt.slateFrom(cen).rotate(rotation).slate(cen) }

  /** Rotates this Rectangle positively or anti-clockwise about its centre, by the given number of degrees. */
  def rotateDegsCen(rotationNum: Double): Rectangle = vertsTrans { vt => vt.slateFrom(cen).rotate(rotationNum.degsVec).slate(cen) }

  /** Rotates this Rectangle negatively or clockwise about its centre, by the given number of degrees. */
  def clkDegsCen(rotationNum: Double): Rectangle = vertsTrans { vt => vt.slateFrom(cen).rotate(rotationNum.degsVec).slate(cen) }
  
  override def slate(operand: VecPt2): Rectangle = vertsTrans(_.slate(operand))
  override def slate(xOperand: Double, yOperand: Double): Rectangle = vertsTrans(_.slate(xOperand, yOperand))
  override def slateFrom(operand: VecPt2): Rectangle = vertsTrans(_.slateFrom(operand))
  override def slateFrom(xOperand: Double, yOperand: Double): Rectangle = vertsTrans(_.slateFrom(xOperand, yOperand))
  override def slateX(xOperand: Double): Rectangle = vertsTrans(_.slateX(xOperand))
  override def slateY(yOperand: Double): Rectangle = vertsTrans(_.slateY(yOperand))
  override def scale(operand: Double): Rectangle = vertsTrans(_.scale(operand))
  override def negX: Rectangle = vertsTrans(_.negX)
  override def negY: Rectangle = vertsTrans(_.negY)
  override def prolign(matrix: AxlignMatrix): Rectangle = vertsTrans(_.prolign(matrix))
  override def rotate90: Rectangle = vertsTrans(_.rotate90)
  override def rotate180: Rectangle = vertsTrans(_.rotate180)
  override def rotate270: Rectangle = vertsTrans(_.rotate270)
  override def mirror(lineLike: LineLike): Rectangle = vertsTrans(_.mirror(lineLike))
  override def rotate(rotation: AngleVec): Rectangle = vertsTrans(_.rotate(rotation))

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
  { val v0 = Pt2(width / 2, height / 2).rotate(rotation).slate(cen)
    val v1 = Pt2(width / 2, -height / 2).rotate(rotation).slate(cen)
    val v2 = Pt2(-width / 2, -height / 2).rotate(rotation).slate(cen)
    from3(v0, v1, v2)
  }

  def apply(width: Double, height: Double, rotation: AngleVec, cenX: Double, cenY: Double): Rectangle = apply(width, height, rotation, Pt2(cenX, cenY))

  /** Factory apply method to create [[Rectangle]] from its first 3 vertices. */
  def from3(vt0: Pt2, vt1: Pt2, vt2: Pt2): Rectangle = new RectangleGen(vt0.x, vt0.y, vt1.x, vt1.y, vt2.x, vt2.y)

  /** Creates a [[Rectangle]] from axis 1. The default for axis 1 is the left right axis. */
  def axis1(sd4Cen: Pt2, sd2Cen: Pt2, height: Double): Rectangle =
  { val cen = sd4Cen.midPt(sd2Cen)
    val ori = sd4Cen.angleVecTo(sd2Cen)
    val width = sd4Cen.distTo(sd2Cen)
    val r1 = Rect(width, height, cen)
    r1.rotateCen(ori)
  }


  def vecsCen(rtVec: Vec2, upVec: Vec2, cen: Pt2): Rectangle = ???// new RectangleGen(unsafeVecsCen(rtVec: Vec2, upVec: Vec2, cen))

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

  /** Implicit [[Slate2]] type class instance evidence for [[Rectangle]]. */
  given slate2Ev: Slate2[Rectangle] = new Slate2[Rectangle]
  { override def slateT(obj: Rectangle, operand: VecPt2): Rectangle = obj.slate(operand)
    override def slateXY(obj: Rectangle, xOperand: Double, yOperand: Double): Rectangle = obj.slate(xOperand, yOperand)
    override def slateFrom(obj: Rectangle, operand: VecPt2): Rectangle = obj.slateFrom(operand)
    override def slateFromXY(obj: Rectangle, xOperand: Double, yOperand: Double): Rectangle = obj.slateFrom(xOperand, yOperand)
    override def slateX(obj: Rectangle, xOperand: Double): Rectangle = obj.slateX(xOperand)
    override def slateY(obj: Rectangle, yOperand: Double): Rectangle = obj.slateY(yOperand)
  }

  /** Implicit [[Scale]] type class instance evidence for [[Rectangle]]. */ 
  given scaleEv: Scale[Rectangle] = (obj: Rectangle, operand: Double) => obj.scale(operand)
  
  /** Implicit [[Rotate]] type class instance evidence for [[Rectangle]]. */
  given rotateEv: Rotate[Rectangle] = (obj: Rectangle, angle: AngleVec) => obj.rotate(angle)
  
  /** Implicit [[Prolign]] type class instance evidence for [[Rectangle]]. */
  given prolignEv: Prolign[Rectangle] = (obj, matrix) => obj.prolign(matrix)
  
  /** Implicit [[Mirror]] type class instance evidence for [[Rectangle]]. */
  given reflectEv: Mirror[Rectangle] = (obj: Rectangle, lineLike: LineLike) => obj.mirror(lineLike)

  /** Implicit [[MirrorAxes]] type class instance evidence for [[Rectangle]]. */
  given transAxesEv: MirrorAxes[Rectangle] = new MirrorAxes[Rectangle]
  { override def negYT(obj: Rectangle): Rectangle = obj.negY
    override def negXT(obj: Rectangle): Rectangle = obj.negX
    override def rotate90(obj: Rectangle): Rectangle = obj.rotate90
    override def rotate180(obj: Rectangle): Rectangle = obj.rotate180
    override def rotate270(obj: Rectangle): Rectangle = obj.rotate270
  }

  given transSimilarEv: Sim2Trans[Rectangle] = new Sim2Trans[Rectangle]
  { override def mirrorT(obj: Rectangle, line: LineLike): Rectangle = ???
    override def rotateT(obj: Rectangle, angle: AngleVec): Rectangle = obj.rotate(angle)
    override def slate(obj: Rectangle, operand: VecPt2): Rectangle = obj.slate(operand)
    override def scale(obj: Rectangle, operand: Double): Rectangle = ???
  }

  /** Implicit [[Drawing]] type class instance / evidence for [[Rectangle]]. */
  given drawingEv: Drawing[Rectangle, RectangleDraw] = (obj, lineWidth, colour) => obj.draw(lineWidth, colour)

  /** Implicit [[Filling]] type class evidence for [[Rectangle]]. */
  given fillingEv: Filling[Rectangle, RectangleFill] = (obj, fillFactet) => obj.fill(fillFactet)
  
  /** A rectangle class that has position and may not be aligned to the X and Y axes. */
  final class RectangleGen(val v0x: Double, val v0y: Double, val v1x: Double, val v1y: Double, val v2x: Double, val v2y: Double) extends Rectangle
  { override type ThisT = RectangleGen
    override def vertsTrans(f: Pt2 => Pt2): RectangleGen = RectangleGen.from3(f(v0), f(v1), f(v2))
    override def rotation: AngleVec = v2.angleVecTo(v1)
  }

  object RectangleGen
  { /** Factory method to construct [[RectangleGen]] from the vertices 0, 1 and 2. */
    def from3(v0: Pt2, v1: Pt2, v2: Pt2): RectangleGen = new RectangleGen(v0.x, v0.y, v1.x, v1.y, v2.x, v2.y)    
  }
}