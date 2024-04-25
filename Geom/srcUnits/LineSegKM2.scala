/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import collection.mutable.ArrayBuffer

/** A 2 dimensional line segment measured in metres, equivalent of the [[LineSeg]] class. A straight line between two points on a 2 dimensional flat
 *  surface. */
class LineSegKM2(xStartMetres: Double, yStartMetres: Double, xEndMetres: Double, yEndMetres: Double) extends LineSegLength2[PtKM2] with LineSegLikeDbl4[PtKM2] with
  Dbl4Elem
{ def xStart: Metres = Metres(xStartMetres)
  def yStart: Metres = Metres(yStartMetres)
  def xEnd: Metres = Metres(xEndMetres)
  def yEnd: Metres = Metres(yEndMetres)
  def startPt: PtKM2 = ???// PtKM2(xStart, yStart)
  def endPt: PtKM2 = ??? //PtKM2(xEnd, yEnd)

  override def dbl1: Double = xStartMetres
  override def dbl2: Double = yStartMetres
  override def dbl3: Double = xEndMetres
  override def dbl4: Double = yEndMetres
}

/** Companion object for line segments in a 2 dimensional space measured in metres. Conatains an apply method, an implicit ArrMap builder instance and
 * an extension method. */
object LineSegKM2
{
  def apply(startDist2: PtKM2, endDist2: PtKM2): LineSegKM2 = ???
    //new LineSegKM2(startDist2.xMetresNum, startDist2.yMetresNum, endDist2.xMetresNum, endDist2.yMetresNum)

  implicit class LineSegMExtensions(val thisSeg: LineSegKM2)
  {
    def /(operand: MetricLength): LineSeg = ???// LineSeg(thisSeg.startPt / operand, thisSeg.endPt / operand)
  }

  /** [[Show]] type class instance / evidence for [[LineSegKM2]]. */
  implicit val showEv: Show2[PtKM2, PtKM2, LineSegKM2] = ???//Show2[PtKM2, PtKM2, LineSegKM2]("LineSegKM2", "start", _.startPt, "end", _.endPt)

  /** [[Unshow]] type class instance / evidence for [[LineSegKM2]]. */
  implicit val unshowEv: Unshow2[PtKM2, PtKM2, LineSegKM2] = ???// Unshow2[PtKM2, PtKM2, LineSegKM2]("Line2", "start", "end", apply)

  /** Implicit instance / evidence for [[BuilderArrMap]] type class. */
  implicit val buildEv: BuilderArrDbl4Map[LineSegKM2, LineSegKM2Arr] = new BuilderArrDbl4Map[LineSegKM2, LineSegKM2Arr]
  { type BuffT = LineSegKM2Buff
    override def fromDblArray(array: Array[Double]): LineSegKM2Arr = new LineSegKM2Arr(array)
    def buffFromBufferDbl(buffer: ArrayBuffer[Double]): LineSegKM2Buff = new LineSegKM2Buff(buffer)
  }
}

/** Compact immutable Array[Double] based collection class for [[LineSegKM2]]s. A mathematical
 *  straight line segment measured in metres. */
class LineSegKM2Arr(val arrayUnsafe: Array[Double]) extends Dbl4Arr[LineSegKM2]
{ type ThisT = LineSegKM2Arr
  def fromArray(array: Array[Double]): LineSegKM2Arr = new LineSegKM2Arr(array)
  override def typeStr: String = "LineSegMArr"
  override def fElemStr: LineSegKM2 => String = _.toString
  override def newElem(d1: Double, d2: Double, d3: Double, d4: Double): LineSegKM2 = new LineSegKM2(d1, d2, d3, d4)
}

/** Companion object for the [[LineSegKM2]]s class. */
object LineSegKM2Arr extends CompanionSeqLikeDbl4[LineSegKM2, LineSegKM2Arr]
{
  override def fromArray(array: Array[Double]): LineSegKM2Arr = new LineSegKM2Arr(array)

  /** Implicit instance /evidence for [[BuilderArrFlat]] type class instance. */
  implicit val flatBuildEv: BuilderArrFlat[LineSegKM2Arr] = new BuilderArrDbl4Flat[LineSegKM2Arr]
  { type BuffT = LineSegKM2Buff
    override def fromDblArray(array: Array[Double]): LineSegKM2Arr = new LineSegKM2Arr(array)
    def buffFromBufferDbl(inp: ArrayBuffer[Double]): LineSegKM2Buff = new LineSegKM2Buff(inp)
  }

  /** [[Show]] type class instance / evidence for [[LineSegKM2Arr]]. */
  implicit lazy val showEv: ShowSequ[LineSegKM2, LineSegKM2Arr] = ShowSequ[LineSegKM2, LineSegKM2Arr]()

  /** [[Unshow]] type class instance / evidence for [[LineSegKM2Arr]]. */
  implicit lazy val unshowEv: UnshowSeq[LineSegKM2, LineSegKM2Arr] = UnshowSeq[LineSegKM2, LineSegKM2Arr]()
}

/** Efficient expandable buffer for [[LineSegKM2]]s. */
class LineSegKM2Buff(val unsafeBuffer: ArrayBuffer[Double]) extends AnyVal with BuffDbl4[LineSegKM2]
{ override def typeStr: String = "Line2sBuff"
  override def newElem(d1: Double, d2: Double, d3: Double, d4: Double): LineSegKM2 = new LineSegKM2(d1, d2, d3, d4)
}
