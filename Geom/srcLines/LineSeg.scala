/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import Colour.Black, collection.mutable.ArrayBuffer, reflect.ClassTag

/** Straight line segment. A straight line in every day terminology. Mathematically: 2 dimensional directed, line segment. The name was chosen to
 *  avoid ambiguity. */
final class LineSeg(val startX: Double, val startY: Double, val endX: Double, val endY: Double) extends LineSegLikeDbl4[Pt2] with LineLike with
CurveSeg with Show2[Pt2, Pt2] with AffinePreserve
{ override type ThisT = LineSeg
  override def typeStr: String = "LineSeg"
  override def name1: String = "startPt"
  override def name2: String = "endPt"
  override implicit def persist1: ShowT[Pt2] = Pt2.persistImplicit
  override implicit def persist2: ShowT[Pt2] = Pt2.persistImplicit
  override def syntaxDepth: Int = 2
  override def show1: Pt2 = startPt
  override def show2: Pt2 = endPt
  override def dbl1: Double = startX
  override def dbl2: Double = startY
  override def dbl3: Double = endX
  override def dbl4: Double = endY
  def startPt: Pt2 = startX pp startY
  def endPt: Pt2 = endX pp endY

  /*override def canEqual(that: Any): Boolean = that match
  { case op: LineSeg => xStart == op.xStart & yStart == op.yStart & xEnd == op.xEnd & yEnd == op.yEnd }*/

  def func4Dou[T](f: (Double, Double, Double, Double) => T): T = f(startX, startY, endX, endY)
  def ptsTrans(f: Pt2 => Pt2): LineSeg = LineSeg(f(pStart), f(pEnd))
  def shortArray: Array[Short] = Array(startX.toShort, startY.toShort,endX.toShort,endY.toShort)
  def isHorizontal: Boolean = startY == endY
  def isVertical: Boolean = startX == endX

  /** Checks whether a forward horizontal ray crosses this polygon side. */
  def rayIntersection(pt: Pt2): Boolean = None match{
    case _ if pt.y > startY & pt.y > endY => false //Check if point is above the polygon side, above beg pt and end pt
    case _ if pt.y < startY & pt.y < endY => false //Check if point is  below the polygon side, below beg pt and end pt
    case _ if 0.000001 > (endY - startY).abs => false /* deltaY. If the polygon side is close to horizontal the point is close enough to the perimeter
     of the polygon that the point can measured as outside */
    case _ => { val ptDeltaY: Double = pt.y - startY
      val deltaX: Double = endX - startX //Not entirely sure what's going on here
      val lineX: Double = startX + (deltaX * ptDeltaY / (endY - startY)) //
      pt.x > lineX
    }
  }

  /** The mid or half way point of this lineSeg. */
  def midPt: Pt2 = Pt2((startX + endX) / 2, (startY + endY) / 2)

  /** The angle of this line segment. */
  def angle: Angle = vec.angle

  /** The angle 90 degrees anti-clock wise from the angle of this directed line segment. The angle one gets by turning left from this Sline. */
  def left90: Angle = angle.p90

  /** The angle 90 degrees clock wise from the angle of this line segment. The angle one gets by turning from from this Sline. */
  def right90: Angle = angle.m90

  /** The relative vector [[Vec2]] of the end point from the start point. */
  def vec: Vec2 = Vec2(endX - startX, endY - startY)

  /** The relative vector [[Vec2]] of the start point from the end point. */
  def revVec: Vec2 = Vec2(startX - endX, startY - endY)

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

  def draw(lineWidth: Double = 2, lineColour: Colour = Black): LineSegDraw = LineSegDraw(this, lineColour, lineWidth)
  def withArrow(colour: Colour = Black, lineWidth: Double = 2): RArr[GraphicSvgElem] = Arrow.paint(startPt, endPt, DegVec25, 20, colour, lineWidth)

  def mirrorPt(pt: Pt2): Pt2 = pt.reflect(this)

  /** Converts this 2 dimensional line segment to an infinite length 2 dimensional line */
  //def toLine: Line = ???
}

/** Companion object for the LineSeg class. Contains factory apply methods and implicit instances for [[LineSeg]]s. */
object LineSeg
{ /** Factory apply method for LineSeg. */
  @inline def apply(pStart: Pt2, pEnd: Pt2): LineSeg = new LineSeg(pStart.x, pStart.y, pEnd.x, pEnd.y)

  @inline def apply(xStart: Double, yStart: Double, xEnd: Double, yEnd: Double): LineSeg = new LineSeg(xStart, yStart, xEnd, yEnd)

  /** Creates a horizontal LineSeg. */
  def horr(y: Double, xStart: Double, yEnd: Double): LineSeg = new LineSeg(xStart, y, xStart, y)

  /** Creates a vertical LineSeg. */
  @inline def vert(x: Double, yStart: Double, yEnd: Double): LineSeg = new LineSeg(x, yStart, x, yEnd)

  implicit val persistImplicit: Persist[LineSeg] =  PersistShow2[Pt2, Pt2, LineSeg]("Line2", "pStart", "pEnd", apply)
  implicit val eqTImplicit: EqT[LineSeg] = Eq2T[Pt2, Pt2, LineSeg](_.pStart, _.pEnd)

  /** Implicit instance / evidence for [[ArrMapBuilder]] type class. */
  implicit val arrMapbuilderEv: LineSegArrMapBuilder = new LineSegArrMapBuilder

  implicit def pairArrMapBuilderEv[B2](implicit ct: ClassTag[B2]): LineSegPairArrMapBuilder[B2] = new LineSegPairArrMapBuilder[B2]

  implicit def transimplicit: AffineTrans[LineSeg] = (obj: LineSeg, f: Pt2 => Pt2) => LineSeg(f(obj.pStart), f(obj.pEnd))
}

/** Compact immutable Array[Double] based collection class for [[LineSeg]]s. [[LineSeg]] is the library's term for a mathematical straight line
 *  segment, but what in common parlance is often just referred to as a line. */
class LineSegArr(val unsafeArray: Array[Double]) extends AnyVal with LineSegLikeDbl4Arr[Pt2, LineSeg] with Dbl4Arr[LineSeg] with AffinePreserve with
Drawable
{ type ThisT = LineSegArr
  def fromArray(array: Array[Double]): LineSegArr = new LineSegArr(array)
  override def typeStr: String = "LineSegArr"
  override def fElemStr: LineSeg => String = _.str
  override def newElem(d1: Double, d2: Double, d3: Double, d4: Double): LineSeg = new LineSeg(d1, d2, d3, d4)
  override def ptsTrans(f: Pt2 => Pt2): LineSegArr = map(orig => LineSeg(f(orig.pStart), f(orig.pEnd)))

  /** Draws the [[LineSeg]]s with the given width and colour. */
  override def draw(lineWidth: Double = 2, colour: Colour = Black): LinesDraw = LinesDraw(this, lineWidth, colour)
}

/** Companion object for the LineSegs class. */
object LineSegArr extends Dbl4SeqLikeCompanion[LineSeg, LineSegArr]
{
  override def fromArray(array: Array[Double]): LineSegArr = new LineSegArr(array)

  implicit val persistImplicit: Dbl4SeqLikePersist[LineSeg, LineSegArr] = new Dbl4SeqLikePersist[LineSeg, LineSegArr]("Line2s")
  { override def fromArray(value: Array[Double]): LineSegArr = new LineSegArr(value)

    override def showDecT(obj: LineSegArr, way: ShowStyle, maxPlaces: Int, minPlaces: Int): String = ???
  }

  /** Implicit instance /evidence for [[ArrFlatBuilder]] type class instance. */
  implicit val arrFlatBuildEv: ArrFlatBuilder[LineSegArr] = new LineSegArrFlatBuilder

  implicit val transImplicit: AffineTrans[LineSegArr] = (obj, f) => obj.map(_.ptsTrans(f))
}

/** Efficient expandable buffer for Line2s. */
class LineSegBuff(val unsafeBuffer: ArrayBuffer[Double]) extends AnyVal with Dbl4Buff[LineSeg]
{ override def typeStr: String = "Line2sBuff"
  override def newElem(d1: Double, d2: Double, d3: Double, d4: Double): LineSeg = new LineSeg(d1, d2, d3, d4)
}

/** Companion object for [[LineSegBuff]] trait, contains factory apply method. */
object LineSegBuff
{ def apply(length: Int = 4): LineSegBuff = new LineSegBuff(new ArrayBuffer[Double](length))
}

trait LineSegArrCommonBuilder extends Dbl4ArrCommonBuilder[LineSegArr]
{ type BuffT = LineSegBuff
  override def fromDblArray(array: Array[Double]): LineSegArr = new LineSegArr(array)
  def buffFromBufferDbl(inp: ArrayBuffer[Double]): LineSegBuff = new LineSegBuff(inp)
}

class LineSegArrMapBuilder extends LineSegArrCommonBuilder with Dbl4ArrMapBuilder[LineSeg, LineSegArr]
class LineSegArrFlatBuilder extends LineSegArrCommonBuilder with Dbl4ArrFlatBuilder[LineSegArr]