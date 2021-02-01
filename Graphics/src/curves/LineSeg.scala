/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import collection.mutable.ArrayBuffer, Colour.Black

/** Straight line segment. A straight line in every day terminology. Mathematically: 2 dimensional directed, line segment. The name was chosen to
 *  avoid ambiguity. */
final class LineSeg(val xStart: Double, val yStart: Double, val xEnd: Double, val yEnd: Double) extends LineLike with CurveSeg with
  Show2[Pt2, Pt2] with Dbl4Elem with AffinePreserve
{ override type ThisT = LineSeg
  override def typeStr: String = "LineSeg"
  override def name1: String = "startPt"
  override def name2: String = "endPt"
  override implicit def ev1: ShowT[Pt2] = Pt2.persistImplicit
  override implicit def ev2: ShowT[Pt2] = Pt2.persistImplicit
  override def syntaxdepth: Int = 2
  override def el1: Pt2 = startPt
  override def el2: Pt2 = endPt
  override def dbl1: Double = xStart
  override def dbl2: Double = yStart
  override def dbl3: Double = xEnd
  override def dbl4: Double = yEnd
  def startPt: Pt2 = xStart pp yStart
  def endPt: Pt2 = xEnd pp yEnd

  /*override def canEqual(that: Any): Boolean = that match
  { case op: LineSeg => xStart == op.xStart & yStart == op.yStart & xEnd == op.xEnd & yEnd == op.yEnd }*/

  def func4Dou[T](f: (Double, Double, Double, Double) => T): T = f(xStart, yStart, xEnd, yEnd)
  def ptsTrans(f: Pt2 => Pt2): LineSeg = LineSeg(f(pStart), f(pEnd))
  def shortArray: Array[Short] = Array(xStart.toShort, yStart.toShort,xEnd.toShort,yEnd.toShort)
  def toLatLongLine(f: Pt2 => LatLong): LLLineSeg = LLLineSeg(f(pStart), f(pEnd))
  def isHorizontal: Boolean = yStart == yEnd
  def isVertical: Boolean = xStart == xEnd
  /**Checks whether a forward horizontal ray crosses this polygon side. */
  def rayIntersection(pt: Pt2): Boolean = ife3(
    pt.y > yStart & pt.y > yEnd, false, //Check if point is above the polygon side, above beg pt and end pt
    pt.y < yStart & pt.y < yEnd, false, //Check if point is  below the polygon side, below beg pt and end pt
    0.000001 > (yEnd - yStart).abs, false, /* deltaY. If the polygon side is close to horizontal the point is close enough to the perimeter
     of the polygon that the point can measured as outside */
    { val ptDeltaY: Double = pt.y - yStart
      val deltaX: Double = xEnd - xStart //Not entirely sure what's going on here
      val lineX: Double = xStart + (deltaX * ptDeltaY / (yEnd - yStart)) //
      pt.x > lineX
    })

  /** The mid or half way point of this lineSeg. */
  def midPt: Pt2 = Pt2((xStart + xEnd) / 2, (yStart + yEnd) / 2)

  /** The angle of this line segment. */
  def angle: Angle = vec.angle

  /** The angle 90 degrees anti-clock wise from the angle of this directed line segment. The angle one gets by turning left from this Sline. */
  def left90: Angle = angle.p90

  /** The angle 90 degrees clock wise from the angle of this line segment. The angle one gets by turning from from this Sline. */
  def right90: Angle = angle.m90

  /** The relative vector [[Vec2]] of the end point from the start point. */
  def vec: Vec2 = Vec2(xEnd - xStart, yEnd - yStart)

  /** The relative vector [[Vec2]] of the start point from the end point. */
  def revVec: Vec2 = Vec2(xStart - xEnd, yStart - yEnd)

  @inline def length: Double = vec.magnitude

  /** Gives the Vec2 point at the specified distance to the right of the end point. At the end point turn right 90 degrees and then travel the given
   * distance to the point. The Vec2 of that point is returned by this method. */
  def endToRight(distFromEnd: Double): Pt2 = pEnd + right90.toVec2(distFromEnd)

  /** Gives the Vec2 point at the specified distance to the left of the end point. At the end point turn left 90 degrees and then travel the given
   * distance to the point.  The Vec2 of that point is returned by this method.*/
  def endToLeft(distFromEnd: Double): Pt2 = pEnd + left90.toVec2(distFromEnd)

  /** Gives the Vec2 point at the specified distance to the right of the start point. At the start point turn right 90 degrees and then travel the
   *  given distance to the point. The Vec2 of that point is returned by this method. */
  def startToRight(distFromStart: Double): Pt2 = pStart + right90.toVec2(distFromStart)

  /** Gives the Vec2 point at the specified distance to the left of the start point. At the start point turn left 90 degrees and then travel the given
   * distance to the point. The Vec2 of that point is returned by this method. */
  def startToLeft(distFromStart: Double): Pt2 = pStart + left90.toVec2(distFromStart)

  /** Gives the Vec2 point at the specified distance to the right of the mid point. At the mid point turn right 90 degrees and then travel the given
   *  distance to the point. The Vec2 of that point is returned by this method. */
  def midPtToRight(distFromMidPt: Double): Pt2 = midPt + right90.toVec2(distFromMidPt)

  /** Gives the Vec2 point at the specified distance to the left of the mid point. At the mid point turn left 90 degrees and then travel the given
   *  distance to the point. The Vec2 of that point is returned by this method. */
  def midPtToLeft(distFromMidPt: Double): Pt2 = midPt + left90.toVec2(distFromMidPt)

  def draw(colour: Colour = Black, lineWidth: Double = 2): LineSegDraw = LineSegDraw(this, colour, lineWidth)
  def withArrow(colour: Colour = Black, lineWidth: Double = 2): GraphicElems = Arrow.paint(startPt, endPt, Deg25, 20, colour, lineWidth)

  def mirrorPt(pt: Pt2): Pt2 = pt.reflect(this)

  /** Converts this 2 dimensional line segment to an infinite length 2 dimensional line */
  //def toLine: Line = ???
}

/** Companion object for the LineSeg class. */
object LineSeg
{ /** Factory apply method for LineSeg. */
  @inline def apply(pStart: Pt2, pEnd: Pt2): LineSeg = new LineSeg(pStart.x, pStart.y, pEnd.x, pEnd.y)

  @inline def apply(xStart: Double, yStart: Double, xEnd: Double, yEnd: Double): LineSeg = new LineSeg(xStart, yStart, xEnd, yEnd)

  /** Creates a horizontal LineSeg. */
  def horr(y: Double, xStart: Double, yEnd: Double): LineSeg = new LineSeg(xStart, y, xStart, y)

  /** Creates a vertical LineSeg. */
  @inline def vert(x: Double, yStart: Double, yEnd: Double): LineSeg = new LineSeg(x, yStart, x, yEnd)

  implicit val persistImplicit: Persist[LineSeg] =  new Persist2er[Pt2, Pt2, LineSeg]("Line2", "pStart", "pEnd", apply)
  implicit val eqTImplicit: EqT[LineSeg] = Eq2T[Pt2, Pt2, LineSeg](_.pStart, _.pEnd)

  implicit val line2sBuildImplicit: Dbl4sArrBuilders[LineSeg, LineSegs] = new Dbl4sArrBuilders[LineSeg, LineSegs]
  { type BuffT = Line2sBuff
    override def fromDblArray(array: Array[Double]): LineSegs = new LineSegs(array)
    def fromDblBuffer(inp: ArrayBuffer[Double]): Line2sBuff = new Line2sBuff(inp)
  }

  implicit def transimplicit: AffineTrans[LineSeg] = (obj: LineSeg, f: Pt2 => Pt2) => LineSeg(f(obj.pStart), f(obj.pEnd))
}