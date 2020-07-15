/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import Colour.Black

/** Closed Polygon made up of straight line segments. The general case can be instantiated with [[PolgonClass]]. */
trait Polygon extends Vec2sLike with Shape with ProlignPreserve
{ type ThisT <: Polygon
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
  def boundingRect: BoundingRect =
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

  override def rotateRadians(radians: Double): Polygon = ???

  override def shearY(operand: Double): Polygon = ???

  def fill(fillColour: Colour): PolygonFill = PolygonFill(this, fillColour)
  def draw(lineWidth: Double = 2, lineColour: Colour = Black): PolygonDraw = PolygonDraw(this, lineWidth, lineColour)
  def fillDraw(fillColour: Colour, lineWidth: Double = 1.0, lineColour: Colour = Black): PolygonFillDraw =
    PolygonFillDraw(this, fillColour, lineWidth, lineColour)

  /** Converts this closed Polygon to LineSegs. The LineSegs collection is empty of there are less than 2 vertices. */
  def toLineSegs: LineSegs =if (length > 1)
  { val res: LineSegs = LineSegs(length)
    for (i <- 0 until (length - 1)) res.unsafeSetElem(i, LineSeg(apply(i), apply(i + 1)))
    res.unsafeSetLast(LineSeg(apply(length - 1), v0))
    res
  }
  else LineSegs()
}

/** Companion object for the Polygon trait. */
object Polygon
{ implicit val eqImplicit: Eq[Polygon] = (p1, p2) => ??? // Eq.arrayImplicit[Double].eqv(p1.arrayUnsafe, p2.arrayUnsafe)
  implicit val persistImplicit: Persist[Polygon] = ???
}