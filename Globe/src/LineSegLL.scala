/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom; package pglobe
import geom._, collection.mutable.ArrayBuffer

/** A 2 dimensional line segment defined in units of latitude and longitude rather than scalars in X and Y. A line on the service of the earth. */
final case class LineSegLL(val dbl1: Double, val dbl2: Double, val dbl3: Double, val dbl4: Double) extends LineSegLikeDbl4[LatLong]
{
//  inline def startSecsLat: Double = dbl1
//  inline def startSecsLong: Double = dbl2
//  inline def endSecsLat: Double = dbl3
//  inline def endSecsLong: Double = dbl4
  def startPt: LatLong = new LatLong(dbl1, dbl2)//startSecsLat, startSecsLong)
  def endPt: LatLong = new LatLong(dbl3, dbl4)//.secs(endSecsLat, endSecsLong)
}

/** Companion object for the [[LineSegLL]] class. */
object LineSegLL
{ def apply(startPt: LatLong, endPt: LatLong): LineSegLL = new LineSegLL(startPt.dbl1, startPt.dbl2, endPt.dbl1, endPt.dbl2)

  /** Implicit instance / evidence for [[ArrMapBuilder]] type class. */
  implicit val buildEv: Dbl4ArrMapBuilder[LineSegLL, LineSegLLArr] = new LineSegArrMapBuilder
}

/** Compact immutable Array[Double] based collection class for [[LineSeg]]s. LineSeg is the library's term for a mathematical straight line segment, but what in
 *  common parlance is often just referred to as a line. */
class LineSegLLArr(val unsafeArray: Array[Double]) extends LineSegLikeDbl4Arr[LatLong, LineSegLL]
{ type ThisT = LineSegLLArr
  def fromArray(array: Array[Double]): LineSegLLArr = new LineSegLLArr(array)
  override def typeStr: String = "LineSegLLArr"
  override def fElemStr: LineSegLL => String = _.toString
  override def newElem(d1: Double, d2: Double, d3: Double, d4: Double): LineSegLL = new LineSegLL(d1, d2, d3, d4)
}

/** Companion object for the LineSegLLs class. */
object LineSegLLArr extends Dbl4SeqLikeCompanion[LineSegLL, LineSegLLArr]
{
  override def fromArray(array: Array[Double]): LineSegLLArr = new LineSegLLArr(array)

  implicit val persistImplicit: Dbl4SeqLikePersist[LineSegLL, LineSegLLArr] = new Dbl4SeqLikePersist[LineSegLL, LineSegLLArr]("Line2s")
  { override def fromArray(value: Array[Double]): LineSegLLArr = new LineSegLLArr(value)
    override def showDecT(obj: LineSegLLArr, way: ShowStyle, maxPlaces: Int, minPlaces: Int): String = ???
  }

  /** Implicit instance /evidence for [[ArrFlatBuilder]] type class instance. */
  implicit val flatBuildEv: ArrFlatBuilder[LineSegLLArr] = new LineSegArrFlatBuilder
}

/** Efficient expandable buffer for Line2s. */
class LineSegLLBuff(val unsafeBuffer: ArrayBuffer[Double]) extends AnyVal with LineSegLikeDbl4Buff[LatLong, LineSegLL]
{ override def typeStr: String = "Line2sBuff"
  override def dblsToT(d1: Double, d2: Double, d3: Double, d4: Double): LineSegLL = new LineSegLL(d1, d2, d3, d4)
}

trait LineSegLLArrCommonBuilder extends Dbl4ArrCommonBuilder[LineSegLLArr]
{ type BuffT = LineSegLLBuff
  final override def fromDblArray(array: Array[Double]): LineSegLLArr = new LineSegLLArr(array)
  final def buffFromBufferDbl(inp: ArrayBuffer[Double]): LineSegLLBuff = new LineSegLLBuff(inp)
}

class LineSegArrMapBuilder extends LineSegLLArrCommonBuilder with Dbl4ArrMapBuilder[LineSegLL, LineSegLLArr]
class LineSegArrFlatBuilder extends LineSegLLArrCommonBuilder with Dbl4ArrFlatBuilder[LineSegLLArr]