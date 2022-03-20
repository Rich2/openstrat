/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import collection.mutable.ArrayBuffer

/** Compact immutable Array[Double] based collection class for [[LineSeg]]s. LineSeg is the library's term for a mathematical straight line segment, but what in
 *  common parlance is often just referred to as a line. */
class LineSegs(val unsafeArray: Array[Double]) extends ArrDbl4s[LineSeg] with AffinePreserve
{ type ThisT = LineSegs
  def unsafeFromArray(array: Array[Double]): LineSegs = new LineSegs(array)
  override def typeStr: String = "Line2s"
  override def fElemStr: LineSeg => String = _.str
  //override def toString: String = Line2s.PersistImplict.show(this)
  override def dataElem(d1: Double, d2: Double, d3: Double, d4: Double): LineSeg = new LineSeg(d1, d2, d3, d4)
  override def ptsTrans(f: Pt2 => Pt2): LineSegs = dataMap(orig => LineSeg(f(orig.pStart), f(orig.pEnd)))

  def draw(lineWidth: Double, colour: Colour = Colour.Black): LinesDraw = LinesDraw(this, lineWidth, colour)
}

/** Companion object for the LineSegs class. */
object LineSegs extends DataDbl4sCompanion[LineSeg, LineSegs]
{
  implicit val factory: Int => LineSegs = i => new LineSegs(new Array[Double](i * 4))

  implicit val persistImplicit: DataDbl4sPersist[LineSeg, LineSegs] = new DataDbl4sPersist[LineSeg, LineSegs]("Line2s")
  { override def fromArray(value: Array[Double]): LineSegs = new LineSegs(value)

    override def showDecT(obj: LineSegs, way: ShowStyle, maxPlaces: Int, minPlaces: Int): String = ???
  }

  implicit val arrArrBuildImplicit: ArrFlatBuilder[LineSegs] = new ArrDbl4sFlatBuilder[LineSeg, LineSegs]
  { type BuffT = LineSegBuff
    override def fromDblArray(array: Array[Double]): LineSegs = new LineSegs(array)
    def fromDblBuffer(inp: ArrayBuffer[Double]): LineSegBuff = new LineSegBuff(inp)
  }

  implicit val transImplicit: AffineTrans[LineSegs] = (obj, f) => obj.dataMap(_.ptsTrans(f))
}

/** Efficient expandable buffer for Line2s. */
class LineSegBuff(val unsafeBuffer: ArrayBuffer[Double]) extends AnyVal with Dbl4Buff[LineSeg]
{ override def typeStr: String = "Line2sBuff"
  override def dblsToT(d1: Double, d2: Double, d3: Double, d4: Double): LineSeg = new LineSeg(d1, d2, d3, d4)
}