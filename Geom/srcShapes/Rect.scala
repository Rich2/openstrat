/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pWeb._

/** A Rectangle aligned to the X and Y axes. */
trait Rect extends Rectangle with Rectangularlign with ShapeOrdinaled
{ type ThisT <: Rect

  override def vertsTrans(f: Pt2 => Pt2): Rect = Rect.fromArray(unsafeMap(f))

  /** Translate geometric transformation on a Rect returns a Rect. */
  override def slate(offset: Vec2Like): Rect = vertsTrans(_.slate(offset))

  /** Translate geometric transformation on a Rect returns a Rect. */
  override def slateXY(xDelta: Double, yDelta: Double): Rect = vertsTrans(_.xySlate(xDelta,yDelta))

  /** Uniform scaling transformation on a Rect returns a Rect. */
  override def scale(operand: Double): Rect = vertsTrans(_.scale(operand))

  /** Mirror, reflection transformation across the X axis on a Rect, returns a Rect. */
  override def negY: Rect = Rect.fromArray(unsafeNegY)

  /** Mirror, reflection transformation across the X axis on a Rect, returns a Rect. */
  override def negX: Rect = Rect.fromArray(unsafeNegX)

  override def rotate90: Rect = vertsTrans(_.rotate90)
  override def rotate180: Rect = vertsTrans(_.rotate180)
  override def rotate270: Rect = vertsTrans(_.rotate270)

  override def prolign(matrix: ProlignMatrix): Rect = vertsTrans(_.prolign(matrix))

  override def scaleXY(xOperand: Double, yOperand: Double): Rect = vertsTrans(_.xyScale(xOperand, yOperand))

  override def activeChildren(id: AnyRef, children: GraphicElems): RectCompound = RectCompound(this, RArr(), active(id) %: children)

  override def fill(fillColour: Colour): RectangleFill = RectFill(this, fillColour)
  override def fillInt(intValue: Int): RectFill = RectFill(this, Colour(intValue))

  final override def boundingRect: Rect = this

  final override def cenPt: Pt2 = Pt2(cenX, cenY)

  final override def cenVec: Vec2 = Vec2(cenX, cenY)

  override def draw(lineColour: Colour, lineWidth: Double): RectDraw = RectDraw(this, lineWidth, lineColour)
}

/** Companion object for the [[Rect]] trait contains factory methods for the Rect trait which delegate to the [[RectImp]] class. */
object Rect
{ /** Factory apply method for a rectangle aligned with the X and Y axes. Default height is 1 and default cebtre point is at x = 0, y = 0. There is a
   *  name overload that takes the X and Y centre coordinates as [[Double]]s. */
  def apply(width: Double, height: Double = 1, cen: Pt2 = Pt2Z): Rect = RectImp(width, height, cen.x, cen.y)

  /** Factory apply method for a rectangle aligned with the X and Y axes. There is a name overload that has a default height of 1 and takes a [[Pt2]]
   *  centre point paremeter wth a default of x = 0, y = 0. */
  def apply(width: Double, height: Double, xCen: Double, cenY: Double): Rect = RectImp(width, height, xCen, cenY)

  /** Creates a [[Rect]] from an Array[Double] */
  def fromArray(array: Array[Double]): Rect = new RectImp(array)

  /** Construct a [[Rect]] from the left, right, bottom and top values." */
  def lrbt(left: Double, right: Double, bottom: Double, top: Double): Rect = Rect(right -left, top - bottom, (left + right) / 2, (bottom + top) / 2)

  /** Factory method for Rect from width, height and the topRight position parameters. The default position for the topLeft parameter places the top
   *  right vertex of the Rect at the origin. */
  def tr(width: Double, height: Double, topRight: Pt2 = Pt2Z): Rect = RectImp(width, height, topRight.x - width / 2, topRight.y - height / 2)

  /** Factory method for Rect from width, height and the topLeft position parameters. The default position for the topLeft parameter places the top
   *  left vertex of the Rect at the origin. */
  def tl(width: Double, height: Double, topLeft: Pt2 = Pt2Z): Rect = RectImp(width, height, topLeft.x + width / 2, topLeft.y - height / 2)

  /** Factory method for Rect from width, height and the topLeft position parameters. The default position for the bottomRight parameter places the
   * bottom right vertex of the Rect at the origin. */
  def br(width: Double, height: Double, bottomRight: Pt2 = Pt2Z): Rect = RectImp(width, height, bottomRight.x - width / 2, bottomRight.y + height / 2)

  /** Factory method for Rect from width, height and the bottomLeft position parameters. The default position for the bottomLeft parameter places the
   * bottom left vertex of the Rect at the origin. */
  def bl(width: Double, height: Double, bottomLeft: Pt2 = Pt2Z): Rect = RectImp(width, height, bottomLeft.x + width / 2, bottomLeft.y + height / 2)

  /** Factory method for Rect from width, height and the bottomCentre position parameters. The default position for the bottomCentre parameter places
   *  the bottom centre of the Rect at the origin. */
  def bCen(width: Double, height: Double, bottomCentre: Pt2 = Pt2Z): Rect = RectImp(width, height, bottomCentre.x, bottomCentre.y + height / 2)

  def cross(width: Double, height: Double, barWidth: Double): RArr[Polygon] = RArr(apply(width, barWidth), apply(barWidth, height))

  def goldenRatio(height: Double): Rectangle = apply(Phi * height, height)

  def colouredBordered(height: Double, colour: Colour, lineWidth: Double = 1): PolygonCompound =
    goldenRatio(height).fillDraw(colour, colour.contrast, lineWidth)

  /** Factory method to create a Rect from the centre point and the v0 point. The v0 point or vertex is y convention the top left vertex of the
   * rectangle, but any of the 4 corner vertices will give the correct constructor values. */
  def cenV0(cen: Pt2, v0: Pt2): Rect = ???// new RectImp((v0.x - cen.x).abs * 2, (v0.y - cen.y).abs * 2, cen.x, cen.y)

  implicit val slateImplicit: Slate[Rect] = (obj: Rect, dx: Double, dy: Double) => obj.slateXY(dx, dy)
  implicit val scaleImplicit: Scale[Rect] = (obj: Rect, operand: Double) => obj.scale(operand)
  implicit val prolignImplicit: Prolign[Rect] = (obj, matrix) => obj.prolign(matrix)

  implicit val reflectAxesImplicit: TransAxes[Rect] = new TransAxes[Rect]
  { override def negYT(obj: Rect): Rect = obj.negY
    override def negXT(obj: Rect): Rect = obj.negX
    override def rotate90(obj: Rect): Rect = obj.rotate90
    override def rotate180(obj: Rect): Rect = obj.rotate180
    override def rotate270(obj: Rect): Rect = obj.rotate270
  }

  /** Implementation class for Rect, a rectangle aligned to the X and Y axes. */
  final class RectImp(val unsafeArray: Array[Double]) extends Rect
  { type ThisT = RectImp

    override def fromArray(array: Array[Double]): RectImp = new RectImp(array)

    override def typeStr: String = "Rect"
    def mapRectImp(f: Pt2 => Pt2): RectImp = RectImp.fromArray(unsafeMap(f))

    def width: Double = (v0x - v3x).abs
    def height: Double = (v0y - v1y).abs
    override def cenX: Double = v0x aver v2x
    override def cenY: Double = v0y aver v2y
    override def vertsTrans(f: Pt2 => Pt2): RectImp = mapRectImp(f)
    override def width1: Double = width
    override def width2: Double = height

    override def attribs: RArr[XmlAtt] = RArr(xAttrib, yAttrib, widthAtt, heightAtt)

    /** Translate geometric transformation on a RectImp returns a RectImp. */
    override def slateXY(xDelta: Double, yDelta: Double): RectImp = mapRectImp(_.xySlate(xDelta, yDelta))

    /** Translate geometric transformation on a RectImp returns a RectImp. */
    override def slate(offset: Vec2Like): RectImp = mapRectImp(_.slate(offset))

    /** Uniform scaling transformation on a RectImp returns a RectImp. */
    override def scale(operand: Double): RectImp = mapRectImp(_.scale(operand))

    /** Mirror, reflection transformation across the X axis on a Rect, returns a Rect. */
    override def negY: RectImp = RectImp.fromArray(unsafeNegY)

    /** Mirror, reflection transformation across the X axis on a Rect, returns a Rect. */
    override def negX: RectImp = RectImp.fromArray(unsafeNegX)

    override def prolign(matrix: ProlignMatrix): Rect = vertsTrans(_.prolign(matrix))

    override def scaleXY(xOperand: Double, yOperand: Double): RectImp = mapRectImp(_.xyScale(xOperand, yOperand))
  }

  /** Companion object for the [[Rect.RectImp]] class */
  object RectImp
  { /** Factory method for Rect.RectImp class. */
    def apply(width: Double, height: Double, cen: Pt2 = Pt2Z): RectImp =
    { val w: Double = width / 2
      val h: Double = height / 2
      val array: Array[Double] = Array[Double](
        cen.x + w, cen.y + h,
        cen.x + w, cen.y - h,
        cen.x - w, cen.y - h,
        cen.x - w, cen.y + h
      )
      new RectImp(array)
    }

    def apply(width: Double, height: Double, cenX: Double, cenY: Double): RectImp =
    { val w: Double = width / 2
      val h: Double = height / 2
      val array: Array[Double] = Array[Double](
        cenX + w, cenY + h,
        cenX + w, cenY - h,
        cenX - w, cenY - h,
        cenX - w, cenY + h,
      )
      new RectImp(array)
    }

    def fromArray(array: Array[Double]): RectImp = new RectImp(array)
  }
}