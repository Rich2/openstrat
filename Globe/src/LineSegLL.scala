/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom; package pglobe
import collection.mutable.ArrayBuffer

/** A 2 dimensional line segment defined in units of latitude and longitude rather than scalars in X and Y. A line on the service of the earth. */
final case class LineSegLL(startSecsLat: Double, startSecsLong: Double, endSecsLat: Double, endSecsLong: Double) extends
  LineSegLike[LatLong] with ElemDbl4
{ def startPt: LatLong = LatLong.secs(startSecsLat, startSecsLong)
  def endPt: LatLong = LatLong.secs(endSecsLat, endSecsLong)

  override def dbl1: Double = startSecsLat
  override def dbl2: Double = startSecsLong
  override def dbl3: Double = endSecsLat
  override def dbl4: Double = endSecsLong
}

/** Companion object for the [[LineSegLL]] class. */
object LineSegLL
{ def apply(startPt: LatLong, endPt: LatLong): LineSegLL = new LineSegLL(startPt.latSecs, startPt.longSecs, endPt.latSecs, endPt.longSecs)

  /** Implicit instance / evidence for [[ArrMapBuilder]] type class. */
  implicit val buildEv: Dbl4ArrMapBuilder[LineSegLL, LineSegLLArr] = new Dbl4ArrMapBuilder[LineSegLL, LineSegLLArr]
  { type BuffT = LineSegLLBuff
    override def fromDblArray(array: Array[Double]): LineSegLLArr = new LineSegLLArr(array)
    def fromDblBuffer(buffer: ArrayBuffer[Double]): LineSegLLBuff = new LineSegLLBuff(buffer)
  }
}

/** Compact immutable Array[Double] based collection class for [[LineSeg]]s. LineSeg is the library's term for a mathematical straight line segment, but what in
 *  common parlance is often just referred to as a line. */
class LineSegLLArr(val unsafeArray: Array[Double]) extends Dbl4Arr[LineSegLL]
{ type ThisT = LineSegLLArr
  def fromArray(array: Array[Double]): LineSegLLArr = new LineSegLLArr(array)
  override def typeStr: String = "LineSegLLArr"
  override def fElemStr: LineSegLL => String = _.toString
  override def newElem(d1: Double, d2: Double, d3: Double, d4: Double): LineSegLL = new LineSegLL(d1, d2, d3, d4)
  //override def ptsTrans(f: Pt2 => Pt2): LineSegLLArr = dataMap(orig => LineSegLL(f(orig.pStart), f(orig.pEnd)))

  //def draw(lineWidth: Double, colour: Colour = Colour.Black): LinesDraw = LinesDraw(this, lineWidth, colour)
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
  implicit val flatBuildEv: ArrFlatBuilder[LineSegLLArr] = new Dbl4ArrFlatBuilder[LineSegLL, LineSegLLArr]
  { type BuffT = LineSegLLBuff
    override def fromDblArray(array: Array[Double]): LineSegLLArr = new LineSegLLArr(array)
    def buffFromBufferDbl(inp: ArrayBuffer[Double]): LineSegLLBuff = new LineSegLLBuff(inp)
  }

  //implicit val transImplicit: AffineTrans[LineSegLLArr] = (obj, f) => obj.dataMap(_.ptsTrans(f))
}

/** Efficient expandable buffer for Line2s. */
class LineSegLLBuff(val unsafeBuffer: ArrayBuffer[Double]) extends AnyVal with Dbl4Buff[LineSegLL]
{ override def typeStr: String = "Line2sBuff"
  override def dblsToT(d1: Double, d2: Double, d3: Double, d4: Double): LineSegLL = new LineSegLL(d1, d2, d3, d4)
}