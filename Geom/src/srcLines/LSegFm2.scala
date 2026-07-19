/* Copyright 2025 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import collection.mutable.ArrayBuffer

/** A 2-dimensional line segment measured in [[Femtometres]], equivalent of the [[LSeg2]] class. A straight line between two points on a 2-dimensional flat
 *  surface. */
class LSegFm2(val xStartFemtometresNum: Double, val yStartFemtometresNum: Double, val xEndFemtometresNum: Double, val yEndFemtometresNum: Double) extends
  LSegLen2[PtFm2], LSegDbl4[PtFm2], Dbl4Elem
{ def xStart: Femtometres = Femtometres(xStartFemtometresNum)
  def yStart: Femtometres = Femtometres(yStartFemtometresNum)
  def xEnd: Femtometres = Femtometres(xEndFemtometresNum)
  def yEnd: Femtometres = Femtometres(yEndFemtometresNum)
  def startPt: PtFm2 = PtFm2(xStartFemtometresNum, yStartFemtometresNum)
  def endPt: PtFm2 = PtFm2(xEndFemtometresNum, yEndFemtometresNum)

  override def slate(operand: VecPtLen2): LSegFm2 = LSegFm2(xStartFemtometresNum + operand.xFemtometresNum, yStartFemtometresNum + operand.yFemtometresNum,
    xEndFemtometresNum + operand.xFemtometresNum, yEndFemtometresNum + operand.yFemtometresNum)

  override def slate(xOperand: Length, yOperand: Length): LSegFm2 = LSegFm2(xStartFemtometresNum + xOperand.femtometresNum,
    yStartFemtometresNum + yOperand.femtometresNum, xEndFemtometresNum + xOperand.femtometresNum, yEndFemtometresNum + yOperand.femtometresNum)

  override def slateX(xOperand: Length): LSegFm2 = LSegFm2(xStartFemtometresNum + xOperand.femtometresNum, yStartFemtometresNum,
    xEndFemtometresNum + xOperand.femtometresNum, yEndFemtometresNum)

  override def slateY(yOperand: Length): LSegFm2 = LSegFm2(xStartFemtometresNum, yStartFemtometresNum + yOperand.femtometresNum, xEndFemtometresNum,
    yEndFemtometresNum + yOperand.femtometresNum)
  
  override def scale(operand: Double): LSegFm2 =
    LSegFm2(xStartFemtometresNum * operand, yStartFemtometresNum * operand, xEndFemtometresNum * operand, yEndFemtometresNum * operand)

  override def mapGeom2(operand: Length): LSeg2 = LSeg2(xStartFemtometresNum / operand.femtometresNum, yStartFemtometresNum / operand.femtometresNum,
    xEndFemtometresNum / operand.femtometresNum, yEndFemtometresNum / operand.femtometresNum)

  override def xStartPicometresNum: Double = xStartFemtometresNum * 1e-3
  override def yStartPicometresNum: Double = yStartFemtometresNum * 1e-3
  override def xEndPicometresNum: Double = xEndFemtometresNum * 1e-3
  override def yEndPicometresNum: Double = yEndFemtometresNum * 1e-3
  override def xStartMetresNum: Double = xStartFemtometresNum * 1e-15
  override def yStartMetresNum: Double = yStartFemtometresNum * 1e-15
  override def xEndMetresNum: Double = xEndFemtometresNum * 1e-15
  override def yEndMetresNum: Double = yEndFemtometresNum * 1e-15
  override def xStartKilometresNum: Double = xStartFemtometresNum * 1e-18
  override def yStartKilometresNum: Double = yStartFemtometresNum * 1e-18
  override def xEndKilometresNum: Double = xEndFemtometresNum * 1e-18
  override def yEndKilometresNum: Double = yEndFemtometresNum * 1e-18
  override def dbl1: Double = xStartFemtometresNum
  override def dbl2: Double = yStartFemtometresNum
  override def dbl3: Double = xEndFemtometresNum
  override def dbl4: Double = yEndFemtometresNum
}

/** Companion object for line segments in a 2-dimensional space measured in metres. Conatains an apply method, an implicit ArrMap builder instance and an
 * extension method. */
object LSegFm2
{ /** Factory apply method for constructing [[LSegFm2]]s from the start and end points. There is an apply overload to construct from the X and Y components
   * of the start and end points. To construct from scalar quantities use the metresNum method. */
  def apply(startPt: PtFm2, endPt: PtFm2): LSegFm2 = new LSegFm2(startPt.xMetresNum, startPt.yMetresNum, endPt.xMetresNum, endPt.yMetresNum)

  /** Factory apply method for constructing [[LSegFm2]]s from the X and Y components of the start and end points. There is an apply overload to construct
   * from the start and end points.To construct from scalar quantities use the metresNum method. */
  def apply(xStartPt: Length, yStartPt: Length, xEndPt: Length, yEndPt: Length): LSegFm2 =
    new LSegFm2(xStartPt.kilometresNum, yStartPt.kilometresNum, xEndPt.kilometresNum, yEndPt.kilometresNum)

  /** Factory method for constructing [[LSegFm2]] from scalar quantities. To construct from [[PtLen2]] quantities use the apply methods. */
  def apply(xStartFemtometresNum: Double, yStartFemtometresNum: Double, xEndFemtometresNum: Double, yEndFemtometresNum: Double): LSegFm2 =
    new LSegFm2(xStartFemtometresNum, yStartFemtometresNum, xEndFemtometresNum, yEndFemtometresNum)

  /** [[Show]] and [[Unshow]] type class instances / evidence for [[LSegFm2]]. */
  implicit val persistEv: Persist2Both[PtFm2, PtFm2, LSegFm2] =
    Persist2Both[PtFm2, PtFm2, LSegFm2]("LineSegKM2", "start", _.startPt, "end", _.endPt, LSegFm2(_, _))

  /** Implicit instance / evidence for [[BuilderArrMap]] type class. */
  implicit val buildEv: BuilderArrDbl4Map[LSegFm2, LineSegFm2Arr] = new BuilderArrDbl4Map[LSegFm2, LineSegFm2Arr]
  { type BuffT = LineSegFm2Buff
    override def fromDblArray(array: Array[Double]): LineSegFm2Arr = new LineSegFm2Arr(array)
    def buffFromBufferDbl(buffer: ArrayBuffer[Double]): LineSegFm2Buff = new LineSegFm2Buff(buffer)
  }
}

/** Compact immutable Array[Double] based collection class for [[LSegFm2]]s. A mathematical straight line segment measured in [[Femtometres]]. */
class LineSegFm2Arr(val arrayUnsafe: Array[Double]) extends ArrDbl4[LSegFm2]
{ type ThisT = LineSegFm2Arr
  def fromArray(array: Array[Double]): LineSegFm2Arr = new LineSegFm2Arr(array)
  override def typeStr: String = "LineSegMArr"
  override def fElemStr: LSegFm2 => String = _.toString
  override def elemFromDbls(d1: Double, d2: Double, d3: Double, d4: Double): LSegFm2 = new LSegFm2(d1, d2, d3, d4)
}

/** Companion object for the [[LSegFm2]]s class. */
object LineSegFm2Arr extends CompanionSeqLikeDbl4[LSegFm2, LineSegFm2Arr]
{ override def fromArray(array: Array[Double]): LineSegFm2Arr = new LineSegFm2Arr(array)

  /** Implicit instance /evidence for [[BuilderArrFlat]] type class instance. */
  implicit val flatBuildEv: BuilderArrFlat[LineSegFm2Arr] = new BuilderFlatArrDbl4[LineSegFm2Arr]
  { type BuffT = LineSegFm2Buff
    override def fromDblArray(array: Array[Double]): LineSegFm2Arr = new LineSegFm2Arr(array)
    def buffFromBufferDbl(inp: ArrayBuffer[Double]): LineSegFm2Buff = new LineSegFm2Buff(inp)
  }

  /** [[Show]] type class instance / evidence for [[LineSegFm2Arr]]. */
  implicit lazy val showEv: ShowSequ[LSegFm2, LineSegFm2Arr] = ShowSequ[LSegFm2, LineSegFm2Arr]()

  /** [[Unshow]] type class instance / evidence for [[LineSegFm2Arr]]. */
  implicit lazy val unshowEv: UnshowSeq[LSegFm2, LineSegFm2Arr] = UnshowSeq[LSegFm2, LineSegFm2Arr]()
  
  /** [[Drawing]] type class instance / evidence for [[LineSegFm2Arr]]. */
  implicit def drawerEv: Drawing[LineSegFm2Arr, RArr[LineSegLen2Draw]] = (obj, lineWidth, colour) => obj.map(_.draw(lineWidth, colour))
}

/** Efficient expandable buffer for [[LSegFm2]]s. */
class LineSegFm2Buff(val bufferUnsafe: ArrayBuffer[Double]) extends AnyVal, BuffDbl4[LSegFm2]
{ override def typeStr: String = "LineSegFm2Buff"
  override def elemFromDbls(d1: Double, d2: Double, d3: Double, d4: Double): LSegFm2 = new LSegFm2(d1, d2, d3, d4)
}