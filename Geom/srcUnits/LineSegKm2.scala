/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import collection.mutable.ArrayBuffer

/** A 2 dimensional line segment measured in metres, equivalent of the [[LineSeg]] class. A straight line between two points on a 2 dimensional flat
 *  surface. */
class LineSegKm2(val xStartKilometresNum: Double, val yStartKilometresNum: Double, xEndMetres: Double, yEndMetres: Double) extends LineSegLength2[PtKm2] with
  LineSegLikeDbl4[PtKm2] with Dbl4Elem
{ def xStart: Kilometres = Kilometres(xStartKilometresNum)
  def yStart: Kilometres = Kilometres(yStartKilometresNum)
  def xEnd: Metres = Metres(xEndMetres)
  def yEnd: Metres = Metres(yEndMetres)
  def startPt: PtKm2 = ???//PtKM2(xStart, yStart)
  def endPt: PtKm2 = ??? //PtKM2(xEnd, yEnd)

  override def xStartMetresNum: Double = xStartKilometresNum * 1000
  override def yStartMetresNum: Double = yStartKilometresNum * 1000

  override def dbl1: Double = xStartKilometresNum
  override def dbl2: Double = yStartKilometresNum
  override def dbl3: Double = xEndMetres
  override def dbl4: Double = yEndMetres
}

/** Companion object for line segments in a 2 dimensional space measured in metres. Conatains an apply method, an implicit ArrMap builder instance and
 * an extension method. */
object LineSegKm2
{
  def apply(startDist2: PtKm2, endDist2: PtKm2): LineSegKm2 = ???
    //new LineSegKM2(startDist2.xMetresNum, startDist2.yMetresNum, endDist2.xMetresNum, endDist2.yMetresNum)

  implicit class LineSegMExtensions(val thisSeg: LineSegKm2)
  {
    def /(operand: MetricLength): LineSeg = ???// LineSeg(thisSeg.startPt / operand, thisSeg.endPt / operand)
  }

  /** [[Show]] type class instance / evidence for [[LineSegKm2]]. */
  implicit val showEv: Show2[PtKm2, PtKm2, LineSegKm2] = ???//Show2[PtKM2, PtKM2, LineSegKM2]("LineSegKM2", "start", _.startPt, "end", _.endPt)

  /** [[Unshow]] type class instance / evidence for [[LineSegKm2]]. */
  implicit val unshowEv: Unshow2[PtKm2, PtKm2, LineSegKm2] = ???// Unshow2[PtKM2, PtKM2, LineSegKM2]("Line2", "start", "end", apply)

  /** Implicit instance / evidence for [[BuilderArrMap]] type class. */
  implicit val buildEv: BuilderArrDbl4Map[LineSegKm2, LineSegKm2Arr] = new BuilderArrDbl4Map[LineSegKm2, LineSegKm2Arr]
  { type BuffT = LineSegKm2Buff
    override def fromDblArray(array: Array[Double]): LineSegKm2Arr = new LineSegKm2Arr(array)
    def buffFromBufferDbl(buffer: ArrayBuffer[Double]): LineSegKm2Buff = new LineSegKm2Buff(buffer)
  }
}

/** Compact immutable Array[Double] based collection class for [[LineSegKm2]]s. A mathematical
 *  straight line segment measured in metres. */
class LineSegKm2Arr(val arrayUnsafe: Array[Double]) extends Dbl4Arr[LineSegKm2]
{ type ThisT = LineSegKm2Arr
  def fromArray(array: Array[Double]): LineSegKm2Arr = new LineSegKm2Arr(array)
  override def typeStr: String = "LineSegMArr"
  override def fElemStr: LineSegKm2 => String = _.toString
  override def newElem(d1: Double, d2: Double, d3: Double, d4: Double): LineSegKm2 = new LineSegKm2(d1, d2, d3, d4)
}

/** Companion object for the [[LineSegKm2]]s class. */
object LineSegKm2Arr extends CompanionSeqLikeDbl4[LineSegKm2, LineSegKm2Arr]
{
  override def fromArray(array: Array[Double]): LineSegKm2Arr = new LineSegKm2Arr(array)

  /** Implicit instance /evidence for [[BuilderArrFlat]] type class instance. */
  implicit val flatBuildEv: BuilderArrFlat[LineSegKm2Arr] = new BuilderArrDbl4Flat[LineSegKm2Arr]
  { type BuffT = LineSegKm2Buff
    override def fromDblArray(array: Array[Double]): LineSegKm2Arr = new LineSegKm2Arr(array)
    def buffFromBufferDbl(inp: ArrayBuffer[Double]): LineSegKm2Buff = new LineSegKm2Buff(inp)
  }

  /** [[Show]] type class instance / evidence for [[LineSegKm2Arr]]. */
  implicit lazy val showEv: ShowSequ[LineSegKm2, LineSegKm2Arr] = ShowSequ[LineSegKm2, LineSegKm2Arr]()

  /** [[Unshow]] type class instance / evidence for [[LineSegKm2Arr]]. */
  implicit lazy val unshowEv: UnshowSeq[LineSegKm2, LineSegKm2Arr] = UnshowSeq[LineSegKm2, LineSegKm2Arr]()
}

/** Efficient expandable buffer for [[LineSegKm2]]s. */
class LineSegKm2Buff(val unsafeBuffer: ArrayBuffer[Double]) extends AnyVal with BuffDbl4[LineSegKm2]
{ override def typeStr: String = "LineSegKm2Buff"
  override def newElem(d1: Double, d2: Double, d3: Double, d4: Double): LineSegKm2 = new LineSegKm2(d1, d2, d3, d4)
}
