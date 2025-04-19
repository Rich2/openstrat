/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pWeb.*, ostrat.Colour.Black

/** A Rectangle aligned to the X and Y axes. It has a leftTop, leftBottom, rightBottom and right Top vertices. The convention is for these to align with
 * vertices 0, 1, 2, 3. However, this can be changed by rotations and reflections.  */
trait Rect extends Rectangle, Rectangularlign, ShapeOrdinaled
{ type ThisT <: Rect

  def vertOrder: Int



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
  override def prolign(matrix: ProlignMatrix): Rect = Rect(width, height, cen.prolign(matrix))
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

  final override def v0x: Double = vertOrder match
  { case 0 | 1 | -3 | -4 => right
    case _ => left
  }

  final override def v0y: Double = vertOrder match
  { case 0 | 3 | -1 | -4 => top
    case _ => bottom
  }

  final override def v0: Pt2 = Pt2(v0x, v0y)

  final override def vLastX: Double = arrayUnsafe(numVerts - 2)
  final override def vLastY: Double = arrayUnsafe(numVerts - 1)
  override def vLast: Pt2 = Pt2(vLastX, vLastY)
  final override def sides: LineSegArr = LineSegArr(side0, side1, side2, side3)

  override def arrayUnsafe: Array[Double] = vertOrder match
  { case 0 => Array[Double](right, top, right, bottom, left, bottom, left, top)
    case -4 => Array[Double](right, top, left, top, left, bottom, right, bottom)
    case 1 => Array[Double](right, bottom, left, bottom, left, top, right, top)
    case -1 => Array[Double](right, bottom, right, top, left, top, left, bottom)
    case 2 => Array[Double](left, bottom, left, top, right, top, right, bottom)
    case -2 => Array[Double](left, bottom, right, bottom, right, top, left, top)
    case 3 => Array[Double](left, top, right, top, right, bottom, left, bottom)
    case _ => Array[Double](left, top, left, bottom, right, bottom, right, top)
  }

  override def xVertsArray: Array[Double] = Array[Double](v0x, v1x, v2x, v3x)
  override def yVertsArray: Array[Double] = Array[Double](v0y, v1y, v2y, v3y)
}

/** Companion object for the [[Rect]] trait contains factory methods for the Rect trait which delegate to the [[RectGen]] class. */
object Rect
{ /** Factory apply method for a rectangle aligned with the X and Y axes. Default height is 1 and default cebtre point is at x = 0, y = 0. There is a name
   * overload that takes the X and Y centre coordinates as [[Double]]s. */
  def apply(width: Double, height: Double = 1, cen: Pt2 = Pt2Z): Rect = RectGen(width, height, cen.x, cen.y)

  /** Factory apply method for a rectangle aligned with the X and Y axes. There is a name overload that has a default height of 1 and takes a [[Pt2]] centre
   * point parameter wth a default of x = 0, y = 0. */
  def apply(width: Double, height: Double, cenX: Double, cenY: Double): Rect = RectGen(width, height, cenX, cenY)

  /** The implicit [[DefaultValue]] type class instace / evidence for [[Rect]] is the [[NoBounds]] object. */
  implicit lazy val defaultEv: DefaultValue[Rect] = new DefaultValue[Rect]
  { override def default: Rect = NoBounds
  }

  /** Creates a [[Rect]] from an Array[Double] */
  def fromArray(array: Array[Double]): Rect = ??? // new RectGen(array)

  /** Construct a [[Rect]] from the left, right, bottom and top values." */
  def lrbt(left: Double, right: Double, bottom: Double, top: Double): Rect = Rect(right -left, top - bottom, (left + right) / 2, (bottom + top) / 2)

  /** Factory method for Rect from width, height and the topRight position parameters. The default position for the topLeft parameter places the top right
   * vertex of the Rect at the origin. */
  def tr(width: Double, height: Double, topRight: Pt2 = Pt2Z): Rect = RectGen(width, height, topRight.x - width / 2, topRight.y - height / 2)

  /** Factory method for Rect from width, height and the topLeft position parameters. The default position for the topLeft parameter places the top left vertex
   * of the Rect at the origin. */
  def tl(width: Double, height: Double, topLeft: Pt2 = Pt2Z): Rect = RectGen(width, height, topLeft.x + width / 2, topLeft.y - height / 2)

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

  implicit val slateImplicit: SlateXY[Rect] = (obj: Rect, dx: Double, dy: Double) => obj.slate(dx, dy)
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
  final class RectGen private(val width: Double, val height: Double, val cenX: Double, val cenY: Double, val vertOrder: Int) extends Rect, PolygonLike[Pt2]
  { type ThisT = RectGen
    override def typeStr: String = "Rect"

    /** Accesses the specifying sequence element by a 0 based index. For [[Sequ]]s this will an alternative name for apply. */
    override def elem(index: Int): Pt2 = index match
    { case 0 => v0
      case 1 => v1
      case 2 => v2
      case _ => v3
    }

    /** The number of data elements in the defining sequence. These collections use underlying mutable Arrays and ArrayBuffers. The length of the underlying Array
     * will be a multiple of this number. For [[Sequ]]s this will be an alternative name for length. */
    

    /** Sets / mutates an element in the Arr at the given index. This method should rarely be needed by end users, but is used by the initialisation and factory
     * methods. */
    override def setElemUnsafe(index: Int, newElem: Pt2): Unit = ???

//    override def vertsTrans(f: Pt2 => Pt2): RectGen = ??? // mapRectImp(f)
    override def width1: Double = width
    override def width2: Double = height

    override def attribs: RArr[XmlAtt] = RArr(xAttrib, yAttrib, widthAtt, heightAtt)

    override def slate(operand: VecPt2): RectGen = new RectGen(width, height, cenX + operand.x, cenY + operand.y, vertOrder)
    override def slate(xOperand: Double, yOperand: Double): RectGen = new RectGen(width, height, cenX + xOperand, cenY + yOperand, vertOrder)

    override def scale(operand: Double): RectGen = new RectGen(width * operand, height * operand, cenX * operand, cenY * operand, vertOrder)
    override def negX: RectGen = new RectGen(width, height, -cenX, cenY, 0)
    override def negY: RectGen = new RectGen(width, height, cenX, -cenY, 0)
    override def prolign(matrix: ProlignMatrix): Rect = ??? // vertsTrans(_.prolign(matrix))

    override def scaleXY(xOperand: Double, yOperand: Double): RectGen =
      new RectGen(width * xOperand, height * yOperand, cenX * xOperand, cenY * yOperand, vertOrder)

    override def vLast: Pt2 = Pt2(vLastX, vLastY)
    override def side0: LineSeg = LineSeg(v0x, v0y, vertX(1), vertY(1))
    override def sd0CenX: Double = v0x \/ vertX(1)
    override def sd0CenY: Double = v0y \/ vertY(1)
    override def sd0Cen: Pt2 = Pt2(sd0CenX, sd0CenY)
    override def vertX(index: Int): Double = arrayUnsafe(index * 2)
    override def vertY(index: Int): Double = arrayUnsafe(index * 2 + 1)
    override def unsafeNegX: Array[Double] = ??? // arrayD1Map(d => -d)
    override def unsafeNegY: Array[Double] = ??? // arrayD2Map(d => -d)
  }

  /** Companion object for the [[Rect.RectGen]] class. */
  object RectGen
  { /** Factory method for Rect.RectImp class. */
    def apply(width: Double, height: Double, cen: Pt2 = Pt2Z): RectGen = new RectGen(width, height, cen.x, cen.y, 0)
    /*{ val w: Double = width / 2
      val h: Double = height / 2
      val array: Array[Double] = Array[Double](
        cen.x + w, cen.y + h,
        cen.x + w, cen.y - h,
        cen.x - w, cen.y - h,
        cen.x - w, cen.y + h
      )
      new RectGen(array)
    }*/

    def apply(width: Double, height: Double, cenX: Double, cenY: Double): RectGen = new RectGen(width, height, cenX, cenY, 0)
    /*{ val w: Double = width / 2
      val h: Double = height / 2
      val array: Array[Double] = Array[Double](
        cenX + w, cenY + h,
        cenX + w, cenY - h,
        cenX - w, cenY - h,
        cenX - w, cenY + h,
      )
      new RectGen(array)
    }*/

    //def fromArray(array: Array[Double]): RectGen = new RectGen(array)
  }
}

object NoBounds extends Rect, PolygonLike[Pt2]
{ override type ThisT = Rect
  override def width: Double = -1
  override def height: Double = -1


  override def cenX: Double = v0x \/ v2x
  override def cenY: Double = v0y \/ v2y

//  override def v0x: Double = arrayUnsafe(0)
//  override def v0y: Double = arrayUnsafe(1)
//  override def v0: Pt2 = Pt2(arrayUnsafe(0), arrayUnsafe(1))
//  override def vLastX: Double = arrayUnsafe(numVerts - 2)
//  override def vLastY: Double = arrayUnsafe(numVerts - 1)
  override def vLast: Pt2 = Pt2(vLastX, vLastY)

  override def vertOrder: Int = 0
  override def side0: LineSeg = LineSeg(v0x, v0y, vertX(1), vertY(1))
  override def sd0CenX: Double = v0x \/ vertX(1)
  override def sd0CenY: Double = v0y \/ vertY(1)
  override def sd0Cen: Pt2 = Pt2(sd0CenX, sd0CenY)
  override def vertX(index: Int): Double = arrayUnsafe(index * 2)
  override def vertY(index: Int): Double = arrayUnsafe(index * 2 + 1)
  override def unsafeNegX: Array[Double] = Array()
  override def unsafeNegY: Array[Double] = Array()
  override def elem(index: Int): Pt2 = ???
//  override def numElems: Int = 0
  override def setElemUnsafe(index: Int, newElem: Pt2): Unit = ???
}