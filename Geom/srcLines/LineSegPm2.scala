/* Copyright 2025 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import collection.mutable.ArrayBuffer

/** A 2-dimensional line segment measured in [[Picometres]], equivalent of the [[LineSeg]] class. A straight line between two points on a 2-dimensional flat
 *  surface. */
class LineSegPm2(val xStartPicometresNum: Double, val yStartPicometresNum: Double, val xEndPicometresNum: Double, val yEndPicometresNum: Double) extends
  LineSegLen2[PtPm2] with LineSegLikeDbl4[PtPm2] with Dbl4Elem
{ def xStart: Picometres = Picometres(xStartPicometresNum)
  def yStart: Picometres = Picometres(yStartPicometresNum)
  def xEnd: Picometres = Picometres(xEndPicometresNum)
  def yEnd: Picometres = Picometres(yEndPicometresNum)
  def startPt: PtPm2 = PtPm2(xStartPicometresNum, yStartPicometresNum)
  def endPt: PtPm2 = PtPm2(xEndPicometresNum, yEndPicometresNum)

  override def slate(operand: VecPtLen2): LineSegPm2 = LineSegPm2(xStartPicometresNum + operand.xPicometresNum, yStartPicometresNum + operand.yPicometresNum,
    xEndPicometresNum + operand.xPicometresNum, yEndPicometresNum + operand.yPicometresNum)

  override def slate(xOperand: Length, yOperand: Length): LineSegPm2 = LineSegPm2(xStartPicometresNum + xOperand.picometresNum,
    yStartPicometresNum + yOperand.picometresNum, xEndPicometresNum + xOperand.picometresNum, yEndPicometresNum + yOperand.picometresNum)

  override def slateX(xOperand: Length): LineSegPm2 = LineSegPm2(xStartPicometresNum + xOperand.picometresNum, yStartPicometresNum,
    xEndPicometresNum + xOperand.picometresNum, yEndPicometresNum)

  override def slateY(yOperand: Length): LineSegPm2 = LineSegPm2(xStartPicometresNum, yStartPicometresNum + yOperand.picometresNum, xEndPicometresNum,
    yEndPicometresNum + yOperand.picometresNum)

  override def scale(operand: Double): LineSegPm2 =
    LineSegPm2(xStartPicometresNum * operand, yStartPicometresNum * operand, xEndPicometresNum * operand, yEndPicometresNum * operand)

  override def mapGeom2(operand: Length): LineSeg = LineSeg(xStartPicometresNum / operand.picometresNum, yStartPicometresNum / operand.picometresNum,
    xEndPicometresNum / operand.picometresNum, yEndPicometresNum / operand.picometresNum)

  override def xStartFemtometresNum: Double = xStartPicometresNum * 1e-15
  override def yStartFemtometresNum: Double = yStartPicometresNum * 1e-15
  override def xEndFemtometresNum: Double = xEndPicometresNum * 1e-15
  override def yEndFemtometresNum: Double = yEndPicometresNum * 1e-15
  override def xStartMetresNum: Double = xStartPicometresNum * 1e-12
  override def yStartMetresNum: Double = yStartPicometresNum * 1e-12
  override def xEndMetresNum: Double = xEndPicometresNum * 1e-12
  override def yEndMetresNum: Double = yEndPicometresNum * 1e-12
  override def xStartKilometresNum: Double = xStartPicometresNum * 1e-15
  override def yStartKilometresNum: Double = yStartPicometresNum * 1e-15
  override def xEndKilometresNum: Double = xEndPicometresNum * 1e-15
  override def yEndKilometresNum: Double = yEndPicometresNum * 1e-15
  override def dbl1: Double = xStartPicometresNum
  override def dbl2: Double = yStartPicometresNum
  override def dbl3: Double = xEndPicometresNum
  override def dbl4: Double = yEndPicometresNum
}

/** Companion object for line segments in a 2-dimensional space measured in metres. Conatains an apply method, an implicit ArrMap builder instance and an
 * extension method. */
object LineSegPm2
{ /** Factory apply method for constructing [[LineSegPm2]]s from the start and end points. There is an apply overload to construct from the X and Y components
   * of the start and end points, specified as [[Double]] values of picometres. */
  def apply(startPt: PtLen2, endPt: PtLen2): LineSegPm2 =
    new LineSegPm2(startPt.xPicometresNum, startPt.yPicometresNum, endPt.xPicometresNum, endPt.yPicometresNum)

  /** Factory apply method for constructing [[LineSegPm2]]s from the X and Y components of the start and end points. There is an apply overload to construct
   * from the start and end points.To construct from scalar quantities use the metresNum method. */
  def apply(xStartPt: Length, yStartPt: Length, xEndPt: Length, yEndPt: Length): LineSegPm2 =
    new LineSegPm2(xStartPt.picometresNum, yStartPt.picometresNum, xEndPt.picometresNum, yEndPt.picometresNum)

  /** Factory method for constructing [[LineSegPm2]] from scalar quantities. To construct from [[PtLen2]] quantities use the apply methods. */
  def apply(xStartPicometresNum: Double, yStartPicometresNum: Double, xEndPicometresNum: Double, yEndPicometresNum: Double): LineSegPm2 =
    new LineSegPm2(xStartPicometresNum, yStartPicometresNum, xEndPicometresNum, yEndPicometresNum)

  /** [[Show]] and [[Unshow]] type class instances / evidence for [[LineSegPm2]]. */
  implicit val persistEv: Persist2Both[PtPm2, PtPm2, LineSegPm2] =
    Persist2Both[PtPm2, PtPm2, LineSegPm2]("LineSegKM2", "start", _.startPt, "end", _.endPt, (p1, p2) => LineSegPm2(p1, p2))

  /** Implicit instance / evidence for [[BuilderArrMap]] type class. */
  implicit val buildEv: BuilderArrDbl4Map[LineSegPm2, LineSegPm2Arr] = new BuilderArrDbl4Map[LineSegPm2, LineSegPm2Arr]
  { type BuffT = LineSegPm2Buff
    override def fromDblArray(array: Array[Double]): LineSegPm2Arr = new LineSegPm2Arr(array)
    def buffFromBufferDbl(buffer: ArrayBuffer[Double]): LineSegPm2Buff = new LineSegPm2Buff(buffer)
  }
}

/** Compact immutable Array[Double] based collection class for [[LineSegPm2]]s. A mathematical straight line segment measured in [[Picometres]]. */
class LineSegPm2Arr(val arrayUnsafe: Array[Double]) extends LineSegLen2Arr[PtPm2], ArrDbl4[LineSegPm2]
{ type ThisT = LineSegPm2Arr
  def fromArray(array: Array[Double]): LineSegPm2Arr = new LineSegPm2Arr(array)
  override def typeStr: String = "LineSegMArr"
  override def fElemStr: LineSegPm2 => String = _.toString
  override def newElem(d1: Double, d2: Double, d3: Double, d4: Double): LineSegPm2 = new LineSegPm2(d1, d2, d3, d4)
  override def slate(operand: VecPtLen2): LineSegPm2Arr = map(_.slate(operand))
  override def slate(xOperand: Length, yOperand: Length): LineSegPm2Arr = map(_.slate(xOperand, yOperand))
  override def slateX(xOperand: Length): LineSegPm2Arr = map(_.slateX(xOperand))
  override def slateY(yOperand: Length): LineSegPm2Arr = map(_.slateY(yOperand))
  override def scale(operand: Double): LineSegPm2Arr = map(_.scale(operand))
  override def mapGeom2(operand: Length): LineSegArr = map(_.mapGeom2(operand))
}

/** Companion object for the [[LineSegPm2]]s class. */
object LineSegPm2Arr extends CompanionSeqLikeDbl4[LineSegPm2, LineSegPm2Arr]
{
  override def fromArray(array: Array[Double]): LineSegPm2Arr = new LineSegPm2Arr(array)

  /** Implicit instance /evidence for [[BuilderArrFlat]] type class instance. */
  implicit val flatBuildEv: BuilderArrFlat[LineSegPm2Arr] = new BuilderArrDbl4Flat[LineSegPm2Arr]
  { type BuffT = LineSegPm2Buff
    override def fromDblArray(array: Array[Double]): LineSegPm2Arr = new LineSegPm2Arr(array)
    def buffFromBufferDbl(inp: ArrayBuffer[Double]): LineSegPm2Buff = new LineSegPm2Buff(inp)
  }

  /** [[Show]] type class instance / evidence for [[LineSegPm2Arr]]. */
  implicit lazy val showEv: ShowSequ[LineSegPm2, LineSegPm2Arr] = ShowSequ[LineSegPm2, LineSegPm2Arr]()

  /** [[Unshow]] type class instance / evidence for [[LineSegPm2Arr]]. */
  implicit lazy val unshowEv: UnshowSeq[LineSegPm2, LineSegPm2Arr] = UnshowSeq[LineSegPm2, LineSegPm2Arr]()

  /** [[Drawing]] type class instance / evidence for [[LineSegPm2Arr]]. */
  implicit def drawerEv: Drawing[LineSegPm2Arr, RArr[LineSegLen2Draw]] = (obj, lineWidth, colour) => obj.map(_.draw(lineWidth, colour))
}

/** Efficient expandable buffer for [[LineSegPm2]]s. */
class LineSegPm2Buff(val unsafeBuffer: ArrayBuffer[Double]) extends AnyVal with BuffDbl4[LineSegPm2]
{ override def typeStr: String = "LineSegPm2Buff"
  override def newElem(d1: Double, d2: Double, d3: Double, d4: Double): LineSegPm2 = new LineSegPm2(d1, d2, d3, d4)
}