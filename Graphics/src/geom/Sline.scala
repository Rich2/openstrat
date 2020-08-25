/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import collection.mutable.ArrayBuffer, Colour.Black

/** A straight line in every day terminology. Mathematically: 2 dimensional directed, line segment. We have created a new short name that avoids
 *  ambiguity. */
class Sline(val xStart: Double, val yStart: Double, val xEnd: Double, val yEnd: Double) extends ProdDbl4 with CurveLikeOld
{ override type ThisT = Sline
  override def toString: String = Sline.persistImplicit.show(this)
  override def _1 = xStart
  override def _2 = yStart
  override def _3 = xEnd
  override def _4 = yEnd

  /** The start point of this SLine. */
 // @inline def vStart: Vec2 = xStart vv yStart

  /** The end point of this Sline. */
  //@inline def vEnd: Vec2 = xEnd vv yEnd

  override def canEqual(that: Any): Boolean = that match
  { case op: Sline => xStart == op.xStart & yStart == op.yStart & xEnd == op.xEnd & yEnd == op.yEnd }

  def func4Dou[T](f: (Double, Double, Double, Double) => T): T = f(xStart, yStart, xEnd, yEnd)
  def fTrans(f: Vec2 => Vec2): Sline = Sline(f(pStart), f(pEnd))
  def shortArray: Array[Short] = Array(xStart.toShort, yStart.toShort,xEnd.toShort,yEnd.toShort)
  def toLatLongLine(f: Vec2 => LatLong): LLLineSeg = LLLineSeg(f(pStart), f(pEnd))
  def isHorizontal: Boolean = yStart == yEnd
  def isVertical: Boolean = xStart == xEnd
  /**Checks whether a forward horizontal ray crosses this polygon side. */
  def rayIntersection(pt: Vec2): Boolean = ife3(
    pt.y > yStart & pt.y > yEnd, false, //Check if point is above the polygon side, above beg pt and end pt
    pt.y < yStart & pt.y < yEnd, false, //Check if point is  below the polygon side, below beg pt and end pt
    0.000001 > (yEnd - yStart).abs, false, /* deltaY. If the polygon side is close to horizontal the point is close enough to the perimeter
     of the polygon that the point can measured as outside */
    { val ptDeltaY: Double = pt.y - yStart
      val deltaX: Double = xEnd - xStart //Not entirely sure what's going on here
      val lineX: Double = xStart + (deltaX * ptDeltaY / (yEnd - yStart)) //
      pt.x > lineX
    })

  def midPt: Vec2 = (pStart + pEnd) / 2

  /** The angle of this line segment. */
  def angle: Angle = (pEnd - pStart).angle

  /** The angle 90 degrees anti-clock wise from the angle of this directed line segment. The angle one gets by turning left from this Sline. */
  def left90: Angle = angle + 90.degs

  /** The angle 90 degrees clock wise from the angle of this line segement. The angle one gets by turning from from this Sline. */
  def right90: Angle = angle - 90.degs

  /** The relative position of the end point from the start point. */
  @inline def delta: Vec2 = pEnd - pStart

  /** Gives the Vec2 point at the specified distance to the right of the end point. At the end point turn right 90 degrees and then travel the given
   * distance to the point. The Vec2 of that point is returned by this method. */
  def endToRight(distFromEnd: Double): Vec2 = pEnd + right90.toVec2(distFromEnd)

  /** Gives the Vec2 point at the specified distance to the left of the end point. At the end point turn left 90 degrees and then travel the given
   * distance to the point.  The Vec2 of that point is returned by this method.*/
  def endToLeft(distFromEnd: Double): Vec2 = pEnd + left90.toVec2(distFromEnd)

  /** Gives the Vec2 point at the specified distance to the right of the start point. At the start point turn right 90 degrees and then travel the
   *  given distance to the point. The Vec2 of that point is returned by this method. */
  def startToRight(distFromStart: Double): Vec2 = pStart + right90.toVec2(distFromStart)

  /** Gives the Vec2 point at the specified distance to the left of the start point. At the start point turn left 90 degrees and then travel the given
   * distance to the point. The Vec2 of that point is returned by this method. */
  def startToLeft(distFromStart: Double): Vec2 = pStart + left90.toVec2(distFromStart)

  /** Gives the Vec2 point at the specified distance to the right of the mid point. At the mid point turn right 90 degrees and then travel the given
   *  distance to the point. The Vec2 of that point is returned by this method. */
  def midPtToRight(distFromMidPt: Double): Vec2 = midPt + right90.toVec2(distFromMidPt)

  /** Gives the Vec2 point at the specified distance to the left of the mid point. At the mid point turn left 90 degrees and then travel the given
   *  distance to the point. The Vec2 of that point is returned by this method. */
  def midPtToLeft(distFromMidPt: Double): Vec2 = midPt + left90.toVec2(distFromMidPt)

  def draw(lineWidth: Double, colour: Colour = Black): LineDraw = LineDraw(xStart, yStart, xEnd, yEnd, lineWidth, colour)

  def mirrorPt(pt: Vec2): Vec2 = pt.reflect(this)

  /** Converts this 2 dimensional line segment to an infinite length 2 dimensional line */
  //def toLine: Line = ???
}

/** Companion object for the LineSeg class. */
object Sline
{ /** Factory apply method for LineSeg. */
  @inline def apply(pStart: Vec2, pEnd: Vec2): Sline = new Sline(pStart.x, pStart.y, pEnd.x, pEnd.y)

  @inline def apply(xStart: Double, yStart: Double, xEnd: Double, yEnd: Double): Sline = new Sline(xStart, yStart, xEnd, yEnd)

  implicit val persistImplicit: Persist[Sline] with Eq[Sline] =
    new Persist2[Vec2, Vec2, Sline]("Line2", "pStart", _.pStart, "pEnd", _.pEnd, Sline(_, _))

  implicit val line2sBuildImplicit: ArrProdDbl4Build[Sline, Slines] = new ArrProdDbl4Build[Sline, Slines]
  { type BuffT = Line2sBuff
    override def fromDblArray(array: Array[Double]): Slines = new Slines(array)
    def fromDblBuffer(inp: ArrayBuffer[Double]): Line2sBuff = new Line2sBuff(inp)
  }

  implicit def transimplicit: AffineTrans[Sline] = (obj: Sline, f: Vec2 => Vec2) => Sline(f(obj.pStart), f(obj.pEnd))
}

object HLine
{ /** Creates a horizontal Line2 */
  @inline def apply(y: Double, xStart: Double, yEnd: Double): Sline = new Sline(xStart, y, xStart, y)
}
object VLine
{ /** Creates a vertical Line2 */
  @inline def apply(x: Double, yStart: Double, yEnd: Double): Sline = new Sline(x, yStart, x, yEnd)
}
