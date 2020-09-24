/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** Short for polygon trait. The general case can be instantiated with [[PolygonGen]], but it provides the interface for particular sub sets of
 *  polygons such as triangles and square. Mathematically a closed polygon made up of straight line segments. */
trait Polygon extends Vec2sLike with Shape
{
  def fTrans(f: Vec2 => Vec2): Polygon = vertsMap(f).toPolygon

  override def fill(fillColour: Colour): PolygonFill = PolygonFill(this, fillColour)
  override def draw(lineWidth: Double, lineColour: Colour): PolygonGraphic = ???

  override def fillDraw(fillColour: Colour, lineWidth: Double, lineColour: Colour): PolygonGraphic = ???
  def length: Int
  def xGet(index: Int): Double
  def yGet(index: Int): Double

  /** May throw on a 0 vertices polygon. */
  def x0: Double

  /** May throw on a 0 vertices polygon. */
  def y0: Double

  /** May throw on a 0 vertices polygon. */
  def v0: Vec2

  def elem1sArray: Array[Double]
  def elem2sArray: Array[Double]
  def foreachPairTail[U](f: (Double, Double) => U): Unit

  /** Currently throws, not sure if that is the correct behaviour. Creates a bounding rectangle for a collection of 2d points */
  override def boundingRect: BoundingRect =
  { var minX, maxX = xGet(0)
    var minY, maxY = yGet(0)
    foreachTail{v =>
      minX = minX.min(v.x)
      maxX = maxX.max(v.x)
      minY = minY.min(v.y)
      maxY = maxY.max(v.y)
    }
    BoundingRect(minX, maxX, minY, maxY)
  }

  @inline def polygonMap(f: Vec2 => Vec2): Polygon = vertsMap(f).toPolygon

  /** Translate geometric transformation on a [[Polygon]] returns a [[Polygon]]. */
  override def slate(offset: Vec2): Polygon = polygonMap(_ + offset)

  /** Translate geometric transformation on a [[Polygon]] returns a [[Polygon]]. */
  override def slate(xOffset: Double, yOffset: Double): Polygon = polygonMap(_.addXY(xOffset, yOffset))

  /** Uniform scaling aginst both X and Y axes transformation on a [[polygon]] returning a [[Polygon]]. Use the xyScale method for differential
   *  scaling. */
  override def scale(operand: Double): Polygon = polygonMap(_ * operand)

  /** Mirror, reflection transformation of a Polygon across the line y = yOffset, which is parallel to the X axis, returns a Polygon. */
  override def reflectXOffset(yOffset: Double): Polygon = polygonMap(_.reflectXOffset(yOffset))

  /** Mirror, reflection transformation of a Polygon across the line x = xOffset, which is parallel to the X axis. Returns a Polygon. */
  override def reflectYOffset(xOffset: Double): Polygon = polygonMap(_.reflectYOffset(xOffset))

  /** Mirror, reflection transformation of a Polygon across the X axis, returns a Polygon. */
  override def reflectX: Polygon = polygonMap(_.reflectX)

  /** Mirror, reflection transformation of Polygon across the Y axis, returns a Polygon. */
  override def reflectY: Polygon = polygonMap(_.reflectY)

  /** Prolign 2d transformations, similar transofrmations that retain alignment with the axes. */
  override def prolign(matrix: ProlignMatrix): Polygon = polygonMap(_.prolign(matrix))

  override def reflect(line: Line): Polygon

  override def reflect(line: LineSeg): Polygon

  override def xyScale(xOperand: Double, yOperand: Double): Polygon

  override def rotateRadians(radians: Double): Polygon = polygonMap(_.rotateRadians(radians))

  override def xShear(operand: Double): Polygon = ???
  override def yShear(operand: Double): Polygon = ???

  /** Converts this closed Polygon to LineSegs. The LineSegs collection is empty of there are less than 2 vertices. */
  def toLineSegs: LineSegs =if (length > 1)
  { val res: LineSegs = LineSegs(length)
    for (i <- 0 until (length - 1)) res.unsafeSetElem(i, LineSeg(apply(i), apply(i + 1)))
    res.unsafeSetLast(LineSeg(apply(length - 1), v0))
    res
  }
  else LineSegs()

  /** Determines if the parenter point lies inside this Polygon. */
  def ptInside(pt: Vec2): Boolean = toLineSegs.ptInPolygon(pt)

  def polyCentre: Vec2 = boundingRect.cen

  def sline(index: Int): LineSeg =
  { val index2: Int = ife(index == length -1, 0, index + 1)
    LineSeg(apply(index), apply(index2))
  }

  def sline0: LineSeg = sline(0)
  def sline1: LineSeg = sline(1)
  def sline2: LineSeg = sline(2)
  def sline3: LineSeg = sline(3)
  def sline4: LineSeg = sline(4)
  def sline5: LineSeg = sline(5)
}

/** Companion object for the Polygon trait. */
object Polygon
{ implicit val eqImplicit: Eq[Polygon] = (p1, p2) => ??? // Eq.arrayImplicit[Double].eqv(p1.arrayUnsafe, p2.arrayUnsafe)
  implicit val persistImplicit: Persist[Polygon] = ???

  implicit val slateImplicit: Slate[Polygon] = (obj: Polygon, offset: Vec2) => obj.slate(offset)
  implicit val scaleImplicit: Scale[Polygon] = (obj: Polygon, operand: Double) => obj.scale(operand)
  implicit val rotateImplicit: Rotate[Polygon] = (obj: Polygon, radians: Double) => obj.rotateRadians(radians)
  implicit val prolignImplicit: Prolign[Polygon] = (obj, matrix) => obj.prolign(matrix)
  implicit val XYScaleImplicit: XYScale[Polygon] = (obj, xOperand, yOperand) => obj.xyScale(xOperand, yOperand)

  /*implicit val rotateAxesImplicit: RotateAxes[Polygon] = new RotateAxes[Polygon]
  { override def rotateT90(obj: Polygon): Polygon = obj.rotate90
    override def rotateT180(obj: Polygon): Polygon = obj.rotate180
    override def rotateT270(obj: Polygon): Polygon = obj.rotate270
  }*/

  implicit val mirrorAxisImplicit: ReflectAxisOffset[Polygon] = new ReflectAxisOffset[Polygon]
  { override def reflectXOffsetT(obj: Polygon, yOffset: Double): Polygon = obj.reflectXOffset(yOffset)
    override def reflectYOffsetT(obj: Polygon, xOffset: Double): Polygon = obj.reflectYOffset(xOffset)
  }

  implicit val shearImplicit: Shear[Polygon] = new Shear[Polygon]
  { override def xShearT(obj: Polygon, yFactor: Double): Polygon = obj.xShear(yFactor)
    override def yShearT(obj: Polygon, xFactor: Double): Polygon = obj.yShear(xFactor)
  }
}