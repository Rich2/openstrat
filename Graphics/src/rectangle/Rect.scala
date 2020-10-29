/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pWeb._

/** A Rectangle aligned to the X and Y axes. */
trait Rect extends Rectangle with Rectangularlign with ShapeAligned
{ @inline final override def x1: Double = xTopRight
  @inline final override def y1: Double = yTopRight
  @inline final override def v1: Vec2 = x1 vv y1
  @inline final override def x2: Double = xBottomRight
  @inline final override def y2: Double = yBottomRight
  @inline final override def v2: Vec2 = x2 vv y2
  @inline final override def cen: Vec2 = xCen vv yCen
  override def rotation: Angle = 0.degs
  @inline final def x3: Double = xBottomLeft
  @inline final def y3: Double = yBottomLeft
  @inline final def v3: Vec2 = bottomLeft
  @inline final def x4: Double = xTopLeft
  @inline final def y4: Double = yTopLeft
  @inline final def v4: Vec2 = topLeft

  final override def s1Cen: Vec2 = Vec2(xCen, yCen + height / 2)
  final override def s2Cen: Vec2 = Vec2(xCen + width / 2, yCen)
  final override def s3Cen: Vec2 = Vec2(xCen, yCen -height / 2)
  final override def s4Cen: Vec2 = Vec2(xCen - width / 2, yCen)

  /** Translate geometric transformation on a Rect returns a Rect. */
  override def slate(offset: Vec2): Rect = Rect(width, height, cen + offset)

  /** Translate geometric transformation on a Rect returns a Rect. */
  override def slate(xOffset: Double, yOffset: Double): Rect = Rect(width, height, xCen + xOffset, yCen + yOffset)

  /** Uniform scaling transformation on a Rect returns a Rect. */
  override def scale(operand: Double): Rect = Rect(width * operand, height * operand, cen * operand)

  /** Mirror, reflection transformation across the X axis on a Rect, returns a Rect. */
  override def negY: Rect = Rect(width, height, cen.negY)

  /** Mirror, reflection transformation across the X axis on a Rect, returns a Rect. */
  override def negX: Rect = Rect(width, height, cen.negX)

  /** Rotate 90 degrees anti clockwise or rotate 270 degrees clockwise 2D geometric transformation on a Rect, returns a Rect. The return type will be
   *  narrowed in sub traits / classes. */
  override def rotate90: Rect = Rect(height, width, cen.rotate90)

  /** Rotate 180 degrees 2D geometric transformation on a Rect, returns a Rect. The return type will be narrowed in sub traits / classes. */
  override def rotate180: Rect = Rect(width, height, cen.rotate180)

  /** Rotate 270 degrees anti clockwise or rotate 90 degrees clockwise 2D geometric transformation on a Rect, returns a Rect. The return type  will be
   *  narrowed in sub traits / classes. */
  override def rotate270: Rect = Rect(height, width, cen.rotate270)

  override def prolign(matrix: ProlignMatrix): Rect = Rect.cenV0(cen.prolign(matrix), v1.prolign(matrix))

  override def xyScale(xOperand: Double, yOperand: Double): Rect = Rect.cenV0(cen.xyScale(xOperand, yOperand), v1.xyScale(xOperand, yOperand))

  override def slateTo(newCen: Vec2): Rect = Rect(width, height, newCen)

  override def activeChildren(id: Any, children: GraphicElems): RectCompound = RectCompound(this, Arr(), active(id) +: children)

  override def fill(fillColour: Colour): RectangleFill = RectFill(this, fillColour)
  override def fillHex(intValue: Int): RectFill = RectFill(this, Colour(intValue))
}

/** Companion object for the [[Rect]] trait contains factory methods for the Rect trait which delegate to the [[RectImp]] class. */
object Rect
{
  def apply(width: Double, height: Double, cen: Vec2 = Vec2Z): Rect = new RectImp(width, height, cen.x, cen.y)
  def apply(width: Double, height: Double, xCen: Double, yCen: Double): Rect = new RectImp(width, height, xCen, yCen)

  /** Factory method for Rect from width, height and the topRight position parameters. The default position for the topLeft parameter places the top
   *  right vertex of the Rect at the origin. */
  def tr(width: Double, height: Double, topRight: Vec2 = Vec2Z): Rect = new RectImp(width, height, topRight.x - width / 2, topRight.y - height / 2)

  /** Factory method for Rect from width, height and the topLeft position parameters. The default position for the topLeft parameter places the top
   *  left vertex of the Rect at the origin. */
  def tl(width: Double, height: Double, topLeft: Vec2 = Vec2Z): Rect = new RectImp(width, height, topLeft.x + width / 2, topLeft.y - height / 2)

  /** Factory method for Rect from width, height and the topLeft position parameters. The default position for the bottomRight parameter places the
   * bottom right vertex of the Rect at the origin. */
  def br(width: Double, height: Double, bottomRight: Vec2 = Vec2Z): Rect = new RectImp(width, height, bottomRight.x - width / 2, bottomRight.y + height / 2)

  /** Factory method for Rect from width, height and the bottomLeft position parameters. The default position for the bottomLeft parameter places the
   * bottom left vertex of the Rect at the origin. */
  def bl(width: Double, height: Double, bottomLeft: Vec2 = Vec2Z): Rect = new RectImp(width, height, bottomLeft.x + width / 2, bottomLeft.y + height / 2)

  /** Factory method for Rect from width, height and the bottomCentre position parameters. The default position for the bottomCentre parameter places
   *  the bottom centre of the Rect at the origin. */
  def bCen(width: Double, height: Double, bottomCentre: Vec2 = Vec2Z): Rect = new RectImp(width, height, bottomCentre.x, bottomCentre.y + height / 2)

  def cross(width: Double, height: Double, barWidth: Double): Arr[Polygon] = Arr(apply(width, barWidth), apply(barWidth, height))

  def goldenRatio(height: Double): Rectangle = apply(Phi * height, height)

  def colouredBordered(height: Double, colour: Colour, lineWidth: Double = 1): PolygonCompound =
    goldenRatio(height).fillDraw(colour, lineWidth, colour.contrast)

  /** Factory method to create a Rect from the centre point and the v0 point. The v0 point or vertex is y convention the top left vertex of the
   * rectangle, but any of the 4 corner vertices will give the correct constructor values. */
  def cenV0(cen: Vec2, v0: Vec2): Rect = new RectImp((v0.x - cen.x).abs * 2, (v0.y - cen.y).abs * 2, cen.x, cen.y)

  implicit val slateImplicit: Slate[Rect] = (obj: Rect, offset: Vec2) => obj.slate(offset)
  implicit val scaleImplicit: Scale[Rect] = (obj: Rect, operand: Double) => obj.scale(operand)
  implicit val prolignImplicit: Prolign[Rect] = (obj, matrix) => obj.prolign(matrix)
  implicit val slateToImplicit: SlateTo[Rect] = (obj: Rect, newCen: Vec2) => obj.slateTo(newCen)

  implicit val reflectAxesImplicit: TransAxes[Rect] = new TransAxes[Rect]
  { override def negYT(obj: Rect): Rect = obj.negY
    override def negXT(obj: Rect): Rect = obj.negX
    override def rotate90T(obj: Rect): Rect = obj.rotate90
    override def rotate180T(obj: Rect): Rect = obj.rotate180
    override def rotate270T(obj: Rect): Rect = obj.rotate270
  }
  
  /** Implementation class for Rect, a rectangle aligned to the X and Y axes. */
  final case class RectImp(width: Double, height: Double, xCen: Double, yCen: Double) extends Rect
  { override def fTrans(f: Vec2 => Vec2): RectImp = RectImp.cenV0(f(cen), f(v1))
    override def width1 = width
    override def width2: Double = height

    override def attribs: Arr[XANumeric] = ???

    /** Translate geometric transformation on a RectImp returns a RectImp. */
    override def slate(offset: Vec2): RectImp = RectImp(width, height, cen + offset)

    /** Translate geometric transformation on a RectImp returns a RectImp. */
    override def slate(xOffset: Double, yOffset: Double): RectImp = RectImp(width, height, xCen + xOffset, yCen + yOffset)

    /** Uniform scaling transformation on a RectImp returns a RectImp. */
    override def scale(operand: Double): RectImp = RectImp(width * operand, height * operand, cen * operand)
    
    /** Mirror, reflection transformation across the X axis on a Rect, returns a Rect. */
    override def negY: RectImp = RectImp(width, height, cen.negY)

    /** Mirror, reflection transformation across the X axis on a Rect, returns a Rect. */
    override def negX: RectImp = RectImp(width, height, cen.negX)

    override def prolign(matrix: ProlignMatrix): Rect = Rect.cenV0(cen.prolign(matrix), v1.prolign(matrix))

    override def xyScale(xOperand: Double, yOperand: Double): RectImp = RectImp.cenV0(cen.xyScale(xOperand, yOperand), v1.xyScale(xOperand, yOperand))

    //override def fill(fillColour: Colour): ShapeFill = ???

    // override def draw(lineWidth: Double, lineColour: Colour): ShapeDraw = ???
  }

  /** Companion object for the [[Rect.RectImp]] class */
  object RectImp
  { def apply(width: Double, height: Double, cen: Vec2 = Vec2Z): RectImp = new RectImp(width, height, cen.x, cen.y)

    /** Factory method to create a RectImp from the centre point and the v0 point. The v0 point or vertex is y convention the top left vertex of the
     * rectangle, but any of the 4 corner vertices will give the correct constructor values. */
    def cenV0(cen: Vec2, v0: Vec2): RectImp = new RectImp((v0.x - cen.x).abs * 2, (v0.y - cen.y).abs * 2, cen.x, cen.y)
  }
}