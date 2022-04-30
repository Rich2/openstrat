/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import collection.mutable.ArrayBuffer

/** Compact immutable Array[Double] based collection class for [[LineSeg]]s. LineSeg is the library's term for a mathematical straight line segment, but what in
 *  common parlance is often just referred to as a line. */
class LineSegArr(val unsafeArray: Array[Double]) extends Dbl4Arr[LineSeg] with AffinePreserve
{ type ThisT = LineSegArr
  def unsafeFromArray(array: Array[Double]): LineSegArr = new LineSegArr(array)
  override def typeStr: String = "LineSegArr"
  override def fElemStr: LineSeg => String = _.str
  override def dataElem(d1: Double, d2: Double, d3: Double, d4: Double): LineSeg = new LineSeg(d1, d2, d3, d4)
  override def ptsTrans(f: Pt2 => Pt2): LineSegArr = dataMap(orig => LineSeg(f(orig.pStart), f(orig.pEnd)))

  def draw(lineWidth: Double, colour: Colour = Colour.Black): LinesDraw = LinesDraw(this, lineWidth, colour)
}

/** Companion object for the LineSegs class. */
object LineSegArr extends Dbl4SeqDefCompanion[LineSeg, LineSegArr]
{
  implicit val factory: Int => LineSegArr = i => new LineSegArr(new Array[Double](i * 4))

  implicit val persistImplicit: Dbl4SeqDefPersist[LineSeg, LineSegArr] = new Dbl4SeqDefPersist[LineSeg, LineSegArr]("Line2s")
  { override def fromArray(value: Array[Double]): LineSegArr = new LineSegArr(value)

    override def showDecT(obj: LineSegArr, way: ShowStyle, maxPlaces: Int, minPlaces: Int): String = ???
  }

  /** Implicit instance /evidence for [[ArrFlatBuilder]] type class instance. */
  implicit val flatBuildEv: ArrFlatBuilder[LineSegArr] = new Dbl4ArrFlatBuilder[LineSeg, LineSegArr]
  { type BuffT = LineSegBuff
    override def fromDblArray(array: Array[Double]): LineSegArr = new LineSegArr(array)
    def fromDblBuffer(inp: ArrayBuffer[Double]): LineSegBuff = new LineSegBuff(inp)
  }

  implicit val transImplicit: AffineTrans[LineSegArr] = (obj, f) => obj.dataMap(_.ptsTrans(f))
}

/** Efficient expandable buffer for Line2s. */
class LineSegBuff(val unsafeBuffer: ArrayBuffer[Double]) extends AnyVal with Dbl4Buff[LineSeg]
{ override def typeStr: String = "Line2sBuff"
  override def dblsToT(d1: Double, d2: Double, d3: Double, d4: Double): LineSeg = new LineSeg(d1, d2, d3, d4)
}