/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** Short for polygon trait. The general case can be instantiated with [[PolygonGen]], but it provides the interface for particular sub sets of
 *  polygons such as triangles and square. Mathematically a closed polygon made up of straight line segments. */
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

  @inline def polygonMap(f: Vec2 => Vec2): PolygonGen = vertsMap(f).toPolygon

  @inline override def rotateRadians(radians: Double): Polygon = polygonMap(_.rotateRadians(radians))

  override def shearX(operand: Double): Polygon = ???
  override def shearY(operand: Double): Polygon = ???

  /** Converts this closed Polygon to LineSegs. The LineSegs collection is empty of there are less than 2 vertices. */
  def toLineSegs: Slines =if (length > 1)
  { val res: Slines = Slines(length)
    for (i <- 0 until (length - 1)) res.unsafeSetElem(i, Sline(apply(i), apply(i + 1)))
    res.unsafeSetLast(Sline(apply(length - 1), v0))
    res
  }
  else Slines()

  /** Determines if the parenter point lies inside this Polygon. */
  def ptInside(pt: Vec2): Boolean = toLineSegs.ptInPolygon(pt)

  def polyCentre: Vec2 = boundingRect.cen

  def sline(index: Int): Sline =
  { val index2: Int = ife(index == length -1, 0, index + 1)
    Sline(apply(index), apply(index2))
  }

  def sline0: Sline = sline(0)
  def sline1: Sline = sline(1)
  def sline2: Sline = sline(2)
  def sline3: Sline = sline(3)
  def sline4: Sline = sline(4)
  def sline5: Sline = sline(5)
}

/** Companion object for the Polygon trait. */
object Polygon
{ implicit val eqImplicit: Eq[Polygon] = (p1, p2) => ??? // Eq.arrayImplicit[Double].eqv(p1.arrayUnsafe, p2.arrayUnsafe)
  implicit val persistImplicit: Persist[Polygon] = ???
}