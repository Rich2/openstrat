/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import collection.mutable.ArrayBuffer

/** A 2 dimensional line segment measured in metres, equivalent of the [[LineSeg]] class. A straight line between two points on a 2 dimensional flat
 *  surface. */
class LineSegM2(xStartMetres: Double, yStartMetres: Double, xEndMetres: Double, yEndMetres: Double) extends LineSegLikeDbl4[PtM2] with Dbl4Elem
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

/** Companion object for line segments in a 2 dimensional space measured in metres. Conatains an apply method, an implicit ArrMap builder instance and
 * an extension method. */
object LineSegM2
{
  def apply(startDist2: PtM2, endDist2: PtM2): LineSegM2 =
    new LineSegM2(startDist2.xMetresNum, startDist2.yMetresNum, endDist2.xMetresNum, endDist2.yMetresNum)

  implicit class LineSegMExtensions(val thisSeg: LineSegM2)
  {
    def /(operand: Length): LineSeg = LineSeg(thisSeg.startPt / operand, thisSeg.endPt / operand)
  }

  /** [[Show]] type class instance / evidence for [[LineSegM2]]. */
  implicit val showEv: Show2[PtM2, PtM2, LineSegM2] = Show2[PtM2, PtM2, LineSegM2]("LineSegM2", "start", _.startPt, "end", _.endPt)

  /** [[Unshow]] type class instance / evidence for [[LineSegM2]]. */
  implicit val unshowEv: Unshow2[PtM2, PtM2, LineSegM2] = Unshow2[PtM2, PtM2, LineSegM2]("Line2", "start", "end", apply)

  /** Implicit instance / evidence for [[BuilderArrMap]] type class. */
  implicit val buildEv: BuilderArrDbl4Map[LineSegM2, LineSegM2Arr] = new BuilderArrDbl4Map[LineSegM2, LineSegM2Arr]
  { type BuffT = LineSegM2Buff
    override def fromDblArray(array: Array[Double]): LineSegM2Arr = new LineSegM2Arr(array)
    def buffFromBufferDbl(buffer: ArrayBuffer[Double]): LineSegM2Buff = new LineSegM2Buff(buffer)
  }
}

/** Compact immutable Array[Double] based collection class for [[LineSegM2]]s. A mathematical
 *  straight line segment measured in metres. */
class LineSegM2Arr(val unsafeArray: Array[Double]) extends Dbl4Arr[LineSegM2]
{ type ThisT = LineSegM2Arr
  def fromArray(array: Array[Double]): LineSegM2Arr = new LineSegM2Arr(array)
  override def typeStr: String = "LineSegMArr"
  override def fElemStr: LineSegM2 => String = _.toString
  override def newElem(d1: Double, d2: Double, d3: Double, d4: Double): LineSegM2 = new LineSegM2(d1, d2, d3, d4)
}

/** Companion object for the [[LineSegM2]]s class. */
object LineSegM2Arr extends CompanionSeqLikeDbl4[LineSegM2, LineSegM2Arr]
{
  override def fromArray(array: Array[Double]): LineSegM2Arr = new LineSegM2Arr(array)

  /** Implicit instance /evidence for [[BuilderArrFlat]] type class instance. */
  implicit val flatBuildEv: BuilderArrFlat[LineSegM2Arr] = new BuilderArrDbl4Flat[LineSegM2Arr]
  { type BuffT = LineSegM2Buff
    override def fromDblArray(array: Array[Double]): LineSegM2Arr = new LineSegM2Arr(array)
    def buffFromBufferDbl(inp: ArrayBuffer[Double]): LineSegM2Buff = new LineSegM2Buff(inp)
  }

  /** [[Show]] type class instance / evidence for [[LineSegM2Arr]]. */
  implicit lazy val showEv: ShowSequ[LineSegM2, LineSegM2Arr] = ShowSequ[LineSegM2, LineSegM2Arr]()

  /** [[Unshow]] type class instance / evidence for [[LineSegM2Arr]]. */
  implicit lazy val unshowEv: UnshowSequ[LineSegM2, LineSegM2Arr] = UnshowSequ[LineSegM2, LineSegM2Arr]()
}

/** Efficient expandable buffer for [[LineSegM2]]s. */
class LineSegM2Buff(val unsafeBuffer: ArrayBuffer[Double]) extends AnyVal with BuffDbl4[LineSegM2]
{ override def typeStr: String = "Line2sBuff"
  override def newElem(d1: Double, d2: Double, d3: Double, d4: Double): LineSegM2 = new LineSegM2(d1, d2, d3, d4)
}