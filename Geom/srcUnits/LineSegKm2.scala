/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import collection.mutable.ArrayBuffer

/** A 2 dimensional line segment measured in metres, equivalent of the [[LineSeg]] class. A straight line between two points on a 2 dimensional flat
 *  surface. */
class LineSegKm2(val xStartKilometresNum: Double, val yStartKilometresNum: Double, val xEndKilometresNum: Double, val yEndKilometresNum: Double) extends
  LineSegLength2[PtKm2] with LineSegLikeDbl4[PtKm2] with Dbl4Elem
{ def xStart: Kilometres = Kilometres(xStartKilometresNum)
  def yStart: Kilometres = Kilometres(yStartKilometresNum)
  def xEnd: Kilometres = Kilometres(xEndKilometresNum)
  def yEnd: Kilometres = Kilometres(yEndKilometresNum)
  def startPt: PtKm2 = PtKm2.kilometresNum(xStartKilometresNum, yStartKilometresNum)
  def endPt: PtKm2 = PtKm2.kilometresNum(xEndKilometresNum, yEndKilometresNum)

  override def /(operand: Length): LineSeg = LineSeg(xStartKilometresNum / operand.metresNum, yStartKilometresNum / operand.metresNum,
    xEndKilometresNum / operand.metresNum, yEndKilometresNum / operand.metresNum)

  override def xStartMetresNum: Double = xStartKilometresNum * 1000
  override def yStartMetresNum: Double = yStartKilometresNum * 1000
  override def xEndMetresNum: Double = xEndKilometresNum * 1000
  override def yEndMetresNum: Double = yEndKilometresNum * 1000
  override def dbl1: Double = xStartKilometresNum
  override def dbl2: Double = yStartKilometresNum
  override def dbl3: Double = xEndKilometresNum
  override def dbl4: Double = yEndKilometresNum
}

/** Companion object for line segments in a 2 dimensional space measured in metres. Conatains an apply method, an implicit ArrMap builder instance and
 * an extension method. */
object LineSegKm2
{ /** Factory apply method for constructing [[LineSegKm2]]s from the start and end points. There is an apply overload to construct from the X and Y components of
   * the start and end points. To construct from scalar quantities use the metresNum method. */
  def apply(startPt: PtKm2, endPt: PtKm2): LineSegKm2 = new LineSegKm2(startPt.xMetresNum, startPt.yMetresNum, endPt.xMetresNum, endPt.yMetresNum)

  /** Factory apply method for constructing [[LineSegKm2]]s from the X and Y components of the start and end points. There is an apply overload to construct
   * from the start and end points.To construct from scalar quantities use the metresNum method. */
  def apply(xStartPt: Length, yStartPt: Length, xEndPt: Length, yEndPt: Length): LineSegKm2 =
    new LineSegKm2(xStartPt.kilometresNum, yStartPt.kilometresNum, xEndPt.kilometresNum, yEndPt.kilometresNum)

  /** Factory method for constructing [[LineSegKm2]] from scalar quantities. To construct from [[PtLength2]] quantities use the apply methods. */
  def kilometresNum(xStartKilometresNum: Double, yStartKilometresNum: Double, xEndKilometresNum: Double, yEndKilometresNum: Double): LineSegM2 =
    new LineSegM2(xStartKilometresNum, yStartKilometresNum, xEndKilometresNum, yEndKilometresNum)

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
