/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import collection.mutable.ArrayBuffer

/** A 2 dimensional line segment measured in metres, equivalent of the [[LineSeg]] class. A straight line between two points on a 2 dimensional flat
 *  surface. */
class LineSegM(xStartMetres: Double, yStartMetres: Double, xEndMetres: Double, yEndMetres: Double) extends LineSegLike[PtM2] with ElemDbl4
{ def xStart: Length = Length(xStartMetres)
  def yStart: Length = Length(yStartMetres)
  def xEnd: Length = Length(xEndMetres)
  def yEnd: Length = Length(yEndMetres)
  def startPt: PtM2 = PtM2(xStart, yStart)
  def endPt: PtM2 = PtM2(xEnd, yEnd)

  override def dbl1: Double = xStartMetres
  override def dbl2: Double = yStartMetres
  override def dbl3: Double = xEndMetres
  override def dbl4: Double = yEndMetres
}

object LineSegM
{
  def apply(startDist2: PtM2, endDist2: PtM2): LineSegM =
    new LineSegM(startDist2.xMetresNum, startDist2.yMetresNum, endDist2.xMetresNum, endDist2.yMetresNum)

  implicit class LineSegMExtensions(val thisSeg: LineSegM)
  {
    def /(operand: Length): LineSeg = LineSeg(thisSeg.startPt / operand, thisSeg.endPt / operand)
  }

  /** Implicit instance / evidence for [[ArrBuilder]] type class. */
  implicit val buildEv: Dbl4ArrBuilder[LineSegM, LineSegMArr] = new Dbl4ArrBuilder[LineSegM, LineSegMArr]
  { type BuffT = LineSegMBuff
    override def fromDblArray(array: Array[Double]): LineSegMArr = new LineSegMArr(array)
    def fromDblBuffer(buffer: ArrayBuffer[Double]): LineSegMBuff = new LineSegMBuff(buffer)
  }
}

/** Compact immutable Array[Double] based collection class for [[LineSeg]]s. LineSeg is the library's term for a mathematical straight line segment, but what in
 *  common parlance is often just referred to as a line. */
class LineSegMArr(val unsafeArray: Array[Double]) extends Dbl4Arr[LineSegM]
{ type ThisT = LineSegMArr
  def unsafeFromArray(array: Array[Double]): LineSegMArr = new LineSegMArr(array)
  override def typeStr: String = "LineSegMArr"
  override def fElemStr: LineSegM => String = _.toString
  override def newElem(d1: Double, d2: Double, d3: Double, d4: Double): LineSegM = new LineSegM(d1, d2, d3, d4)
}

/** Companion object for the LineSegMs class. */
object LineSegMArr extends Dbl4SeqLikeCompanion[LineSegM, LineSegMArr]
{
  override def fromArray(array: Array[Double]): LineSegMArr = new LineSegMArr(array)

  implicit val persistImplicit: Dbl4SeqLikePersist[LineSegM, LineSegMArr] = new Dbl4SeqLikePersist[LineSegM, LineSegMArr]("Line2s")
  { override def fromArray(value: Array[Double]): LineSegMArr = new LineSegMArr(value)

    override def showDecT(obj: LineSegMArr, way: ShowStyle, maxPlaces: Int, minPlaces: Int): String = ???

  }

  /** Implicit instance /evidence for [[ArrFlatBuilder]] type class instance. */
  implicit val flatBuildEv: ArrFlatBuilder[LineSegMArr] = new Dbl4ArrFlatBuilder[LineSegM, LineSegMArr]
  { type BuffT = LineSegMBuff
    override def fromDblArray(array: Array[Double]): LineSegMArr = new LineSegMArr(array)
    def fromDblBuffer(inp: ArrayBuffer[Double]): LineSegMBuff = new LineSegMBuff(inp)
  }

  //implicit val transImplicit: AffineTrans[LineSegMArr] = (obj, f) => obj.dataMap(_.ptsTrans(f))
}

/** Efficient expandable buffer for Line2s. */
class LineSegMBuff(val unsafeBuffer: ArrayBuffer[Double]) extends AnyVal with Dbl4Buff[LineSegM]
{ override def typeStr: String = "Line2sBuff"
  override def dblsToT(d1: Double, d2: Double, d3: Double, d4: Double): LineSegM = new LineSegM(d1, d2, d3, d4)
}