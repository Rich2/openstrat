/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pWeb.*, ostrat.Colour.Black

/** A Rectangle aligned to the X and Y axes. It has a leftTop, leftBottom, rightBottom and right Top vertices. The convention is for these to align with
 * vertices 0, 1, 2, 3. However, this can be changed by rotations and reflections.  */
trait Rect extends Rectangle, Rectangularlign, ShapeOrdinaled
{ type ThisT <: Rect
  override def slate(operand: VecPt2): Rect = Rect(width, height, cen.slate(operand))
  override def slate(xOperand: Double, yOperand: Double): Rect = Rect(width, height, cenX + xOperand, cenY + yOperand)
  override def slateX(xOperand: Double): Rect = Rect(width, height, cenX + xOperand, cenY)
  override def slateY(yOperand: Double): Rect = Rect(width, height, cenX, cenY + yOperand)
  override def scale(operand: Double): Rect = Rect(width * operand, height * operand, cenX * operand, cenY * operand)
  override def negX: Rect = Rect(width, height, -cenX, cenY)
  override def negY: Rect = Rect(width, height, cenX, -cenY)
  override def rotate90: Rect = Rect(height, width, cen.rotate90)
  override def rotate180: Rect = Rect(width, height, -cenX, -cenY)
  override def rotate270: Rect = Rect(height, width, cen.rotate270)
  override def prolign(matrix: AxlignMatrix): Rect = Rect(width, height, cen.prolign(matrix))
  override def scaleXY(xOperand: Double, yOperand: Double): Rect = Rect(width * xOperand, height * yOperand, cenX * xOperand, cenY * yOperand)

  override def activeChildren(id: AnyRef, children: GraphicElems): RectCompound = RectCompound(this, RArr(), active(id) %: children)
  final override def boundingRect: Rect = this
  final override def cenPt: Pt2 = Pt2(cenX, cenY)
  final override def cenVec: Vec2 = Vec2(cenX, cenY)

  /** Adds a margin to this [[Rect]], rectangle aligned with the XY axes, moving the sides out by the given parameter. */
  def addMargin(delta: Double): Rect = Rect(width + 2 * delta, height + 2 * delta, cenX, cenY)

  /** Adds a margin to the left and right of  this [[Rect]], rectangle aligned with the XY axes, moving the sides out by the given parameter. */
  def addHorrMargin(delta: Double): Rect = Rect(width + 2 * delta, height, cenX, cenY)

  /** Adds a margin to top and bottom of this [[Rect]], rectangle aligned with the XY axes, moving the sides out by the given parameter. */
  def addVertMargin(delta: Double): Rect = Rect(width, height, cenX, cenY)

  /** Adds a margin to this [[Rect]], rectangle aligned with the XY axes, moving the sides out by the given parameter. */
  def addHorrVertMargin(horrDelta: Double, vertDelta: Double): Rect = Rect(width + 2 * horrDelta, height + 2 * vertDelta, cenX, cenY)

  /** Creates a union of the [[Rect]]s for bounding rectangles. */
  def ||(operand: Rect): Rect =
  { val newLeft = left.min(operand.left)
    val newRight = right.max(operand.right)
    val newbottom = bottom.min(operand.bottom)
    val newTop = top.max(operand.top)
    Rect(newRight - newLeft, newTop - newbottom, (newLeft + newRight) / 2, (newbottom + newTop) / 2)
  }

  /** The shortest of the width and height dimensions. */
  def widthHeightMin: Double = width.min(height)

  /** The shortest of the width and height dimensions relative to the given ratio. */
  def widthHeightMin(ratio: Double): Double = width.min(height * ratio)

  override def fill(fillfacet: FillFacet): RectangleFill = RectFill(this, fillfacet)
  override def fillInt(intValue: Int): RectFill = RectFill(this, Colour(intValue))
  override def draw(lineWidth: Double, lineColour: Colour): RectDraw = RectDraw(this, lineWidth, lineColour)

  override def fillActiveDraw(fillColour: Colour, pointerID: Any, lineColour: Colour = Black, lineWidth: Double): RectCompound =
    RectCompound(this, RArr(fillColour, DrawFacet(lineColour, lineWidth)), RArr(PolygonActive(this, pointerID)))

  def spacedPts(nx: Int, ny: Int): Pt2Arr = ijToMap(1, nx)(1, ny) { (i, j) =>
    val x = (left * i + right * (nx + 1 - i)) / (nx + 1)
    val y = (bottom * j + top * (ny + 1 - j)) / (ny + 1)
    Pt2(x, y)
  }
  
  final override def width: Double = (v0x - v2x).abs
  override def rotation: AngleVec = 0.degsVec
}

/** Companion object for the [[Rect]] trait contains factory methods for the Rect trait which delegate to the [[RectGen]] class. */
object Rect
{ /** Factory apply method for a rectangle aligned with the X and Y axes. Default height is 1 and default centre point is at x = 0, y = 0. There is a name
   * overload that takes the X and Y centre coordinates as [[Double]]s. */
  def apply(width: Double, height: Double = 1, cen: Pt2 = Pt2Z): Rect = RectGen(width, height, cen.x, cen.y)

  /** Factory apply method for a rectangle aligned with the X and Y axes. There is a name overload that has a default height of 1 and takes a [[Pt2]] centre
   * point parameter wth a default of x = 0, y = 0. */
  def apply(width: Double, height: Double, cenX: Double, cenY: Double): Rect = RectGen(width, height, cenX, cenY)

  /** The implicit [[DefaultValue]] type class instance / evidence for [[Rect]] is the [[NoBounds]] object. */
  implicit lazy val defaultEv: DefaultValue[Rect] = new DefaultValue[Rect]
  { override def default: Rect = NoBounds
  }

  /** Creates a [[Rect]] from an Array[Double] */
  def fromArray(array: Array[Double]): Rect = ??? // new RectGen(array)

  /** Construct a [[Rect]] from the left, right, bottom and top values." */
  def lrbt(left: Double, right: Double, bottom: Double, top: Double): Rect = new RectGen(right, top, right, bottom, left, bottom)

  /** Factory method for Rect from width, height and the topRight position parameters. The default position for the topLeft parameter places the top right
   * vertex of the Rect at the origin. */
  def tr(width: Double, height: Double, topRight: Pt2 = Pt2Z): Rect =
    new RectGen(topRight.x, topRight.y, topRight.x, topRight.y - height, topRight.x - width, topRight.y - height)

  /** Factory method for Rect from width, height and the topLeft position parameters. The default position for the topLeft parameter places the top left vertex
   * of the Rect at the origin. */
  def tl(width: Double, height: Double, topLeft: Pt2 = Pt2Z): Rect =
    new RectGen(topLeft.x + width, topLeft.y, topLeft.x + width, topLeft.y - height, topLeft.x, topLeft.y - height)

  /** Factory method for Rect from width, height and the topLeft position parameters. */
  def tl(width: Double, height: Double, xTopLeft: Double, yTopLeft: Double): Rect = RectGen(width, height, xTopLeft + width / 2, yTopLeft - height / 2)

  /** Factory method for Rect from width, height and the topLeft position parameters. The default position for the bottomRight parameter places the bottom right
   * vertex of the Rect at the origin. */
  def br(width: Double, height: Double, bottomRight: Pt2 = Pt2Z): Rect = RectGen(width, height, bottomRight.x - width / 2, bottomRight.y + height / 2)

  /** Factory method for Rect from width, height and the bottomLeft position parameters. The default position for the bottomLeft parameter places the bottom
   * left vertex of the Rect at the origin. */
  def bl(width: Double, height: Double, bottomLeft: Pt2 = Pt2Z): Rect = RectGen(width, height, bottomLeft.x + width / 2, bottomLeft.y + height / 2)

  /** Factory method for Rect from width, height and the bottomLeft position parameters. The default position for the bottomLeft parameter places the bottom
   * left vertex of the Rect at the origin. */
  def bl(width: Double, height: Double, xBL: Double, yBL: Double): Rect = RectGen(width, height, xBL + width / 2, yBL + height / 2)

  /** Factory method for Rect from width, height and the bottomCentre position parameters. The default position for the bottomCentre parameter places the bottom
   * centre of the Rect at the origin. */
  def bCen(width: Double, height: Double, bottomCentre: Pt2 = Pt2Z): Rect = RectGen(width, height, bottomCentre.x, bottomCentre.y + height / 2)

  def cross(width: Double, height: Double, barWidth: Double): RArr[Polygon] = RArr(apply(width, barWidth), apply(barWidth, height))

  def goldenRatio(height: Double): Rectangle = apply(Phi * height, height)

  def colouredBordered(height: Double, colour: Colour, lineWidth: Double = 1): PolygonCompound =
    goldenRatio(height).fillDraw(colour, colour.contrast, lineWidth)

  /** Factory method to create a Rect from the centre point and the v0 point. The v0 point or vertex is y convention the top left vertex of the rectangle, but
   * any of the 4 corner vertices will give the correct constructor values. */
  def cenV0(cen: Pt2, v0: Pt2): Rect = ???// new RectImp((v0.x - cen.x).abs * 2, (v0.y - cen.y).abs * 2, cen.x, cen.y)

  def bounding(inp: Arr[?]): Rect = inp.foldLeft{(acc, el) => el match
    { case be: BoundedElem => acc || be.boundingRect
      case _ => acc
    }
  }

  /** Implicit [[Slate2]] type class instance / evidence for [[Rect]]. */
  implicit val slate2Ev: Slate2[Rect] = new Slate2[Rect]
  { override def slate(obj: Rect, operand: VecPt2): Rect = obj.slate(operand)
    override def slateXY(obj: Rect, xOperand: Double, yOperand: Double): Rect = obj.slate(xOperand, yOperand)
  }

  /** Implicit [[Scale]] type class instance / evidence for [[Rect]]. */
  implicit val scaleEv: Scale[Rect] = (obj: Rect, operand: Double) => obj.scale(operand)

  /** Implicit [[Prolign]] type class instance / evidence for [[Rect]]. */
  implicit val prolignEv: Prolign[Rect] = (obj, matrix) => obj.prolign(matrix)

  /** Implicit [[TransAxes]] type class instance / evidence for [[Rect]]. */
  implicit val transAxesEv: TransAxes[Rect] = new TransAxes[Rect]
  { override def negYT(obj: Rect): Rect = obj.negY
    override def negXT(obj: Rect): Rect = obj.negX
    override def rotate90(obj: Rect): Rect = obj.rotate90
    override def rotate180(obj: Rect): Rect = obj.rotate180
    override def rotate270(obj: Rect): Rect = obj.rotate270
  }

  /** General implementation class for Rect, a rectangle aligned to the X and Y axes. */
  final class RectGen (val v0x: Double, val v0y: Double, val v1x: Double, val v1y: Double, val v2x: Double, val v2y: Double) extends Rect
  { type ThisT = RectGen
    override def typeStr: String = "Rect"
    override def width1: Double = width
    override def width2: Double = height
    override def height: Double = (v0y - v2y).abs

    override def attribs: RArr[XmlAtt] = RArr(xAttrib, yAttrib, widthAtt, heightAtt)

    override def slate(operand: VecPt2): RectGen = slate(operand.x, operand.y)
    
    override def slate(xOperand: Double, yOperand: Double): RectGen =
      new RectGen(v0x + xOperand, v0y + yOperand, v1x + xOperand, v1y + yOperand, v2x + xOperand, v2y + yOperand)

    override def scale(operand: Double): RectGen = new RectGen(v0x * operand, v0y * operand, v1x * operand, v1y * operand, v2x * operand, v2y * operand)
    override def negX: RectGen = new RectGen(-v0x, v0y, -v1x, v1y, -v2x, v2y)
    override def negY: RectGen = new RectGen(v0x, -v0y, v1x, -v1y, v2x, -v2y)
    override def prolign(matrix: AxlignMatrix): Rect = ??? // vertsTrans(_.prolign(matrix))

    override def scaleXY(xOperand: Double, yOperand: Double): RectGen =
      new RectGen(v0x * xOperand, v0y * yOperand, v1x * xOperand, v1y * yOperand, v2x * xOperand, v2y * yOperand)
  }

  /** Companion object for the [[Rect.RectGen]] class. */
  object RectGen
  { /** Factory method for constructing [[RectGen]] class, a general case of a [[Rect]]. */
    def apply(width: Double, height: Double, cen: Pt2 = Pt2Z, vertOrder: Int = 0): RectGen =
    { val hw = width / 2
      val hh = height / 2
      new RectGen(cen.x + hw, cen.y + hh, cen.x + hw, cen.y - hh, cen.x - hw, cen.y - hh)
    }
    
    def apply(width: Double, height: Double, cenX: Double, cenY: Double): RectGen =
    { val hw = width / 2
      val hh = height / 2
      new RectGen(cenX + hw, cenY + hh, cenX + hw, cenY - hh, cenX - hw, cenY - hh)
    }

    def apply(width: Double, height: Double, cenX: Double, cenY: Double, vertOrder: Int): RectGen = ???
  }
}

object NoBounds extends Rect
{ override type ThisT = Rect
  override def height: Double = -1
  override def v0x: Double = 0
  override def v0y: Double = 0
  override def v1x: Double = 0
  override def v1y: Double = 0
  override def v2x: Double = 0
  override def v2y: Double = 0
}