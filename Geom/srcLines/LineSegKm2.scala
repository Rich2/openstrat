/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import collection.mutable.ArrayBuffer

/** A 2-dimensional line segment measured in kilometres, equivalent of the [[LineSeg]] class. A straight line between two points on a 2-dimensional flat
 *  surface. */
class LineSegKm2(val xStartKilometresNum: Double, val yStartKilometresNum: Double, val xEndKilometresNum: Double, val yEndKilometresNum: Double) extends
  LineSegLen2[PtKm2] with LineSegLikeDbl4[PtKm2] with Dbl4Elem
{ def xStart: Kilometres = Kilometres(xStartKilometresNum)
  def yStart: Kilometres = Kilometres(yStartKilometresNum)
  def xEnd: Kilometres = Kilometres(xEndKilometresNum)
  def yEnd: Kilometres = Kilometres(yEndKilometresNum)
  def startPt: PtKm2 = PtKm2.apply(xStartKilometresNum, yStartKilometresNum)
  def endPt: PtKm2 = PtKm2.apply(xEndKilometresNum, yEndKilometresNum)

  override def slate(operand: VecPtLen2): LineSegKm2 = LineSegKm2(xStartKilometresNum + operand.xKilometresNum, yStartKilometresNum + operand.yKilometresNum,
    xEndKilometresNum + operand.xKilometresNum, yEndKilometresNum + operand.yKilometresNum)

  override def slate(xOperand: Length, yOperand: Length): LineSegKm2 = LineSegKm2(xStartKilometresNum + xOperand.kilometresNum,
    yStartKilometresNum + yOperand.kilometresNum, xEndKilometresNum + xOperand.kilometresNum, yEndKilometresNum + yOperand.kilometresNum)

  override def slateX(xOperand: Length): LineSegKm2 = LineSegKm2(xStartKilometresNum + xOperand.kilometresNum, yStartKilometresNum,
    xEndKilometresNum + xOperand.kilometresNum, yEndKilometresNum)

  override def slateY(yOperand: Length): LineSegKm2 = LineSegKm2(xStartKilometresNum, yStartKilometresNum + yOperand.kilometresNum, xEndKilometresNum,
    yEndKilometresNum + yOperand.kilometresNum)

  override def scale(operand: Double): LineSegKm2 =
    LineSegKm2(xStartKilometresNum * operand, yStartKilometresNum * operand, xEndKilometresNum * operand, yEndKilometresNum * operand)
  
  override def mapGeom2(operand: Length): LineSeg = LineSeg(xStartKilometresNum / operand.kilometresNum, yStartKilometresNum / operand.kilometresNum,
    xEndKilometresNum / operand.kilometresNum, yEndKilometresNum / operand.kilometresNum)

  override def xStartFemtometresNum: Double = xStartKilometresNum * 1e15
  override def yStartFemtometresNum: Double = yStartKilometresNum * 1e15
  override def xEndFemtometresNum: Double = xEndKilometresNum * 1e15
  override def yEndFemtometresNum: Double = yEndKilometresNum * 1e15  
  override def xStartPicometresNum: Double = xStartKilometresNum * 1e12
  override def yStartPicometresNum: Double = yStartKilometresNum * 1e12
  override def xEndPicometresNum: Double = xEndKilometresNum * 1e12
  override def yEndPicometresNum: Double = yEndKilometresNum * 1e12  
  override def xStartMetresNum: Double = xStartKilometresNum * 1e3
  override def yStartMetresNum: Double = yStartKilometresNum * 1e3
  override def xEndMetresNum: Double = xEndKilometresNum * 1e3
  override def yEndMetresNum: Double = yEndKilometresNum * 1e3
  override def dbl1: Double = xStartKilometresNum
  override def dbl2: Double = yStartKilometresNum
  override def dbl3: Double = xEndKilometresNum
  override def dbl4: Double = yEndKilometresNum
}

/** Companion object for line segments in a 2-dimensional space measured in metres. Contains an apply method, an implicit ArrMap builder instance and an
 * extension method. */
object LineSegKm2
{ /** Factory apply method for constructing [[LineSegKm2]]s from the start and end points. There is an apply overload to construct from the X and Y components
   * of the start and end points. To construct from scalar quantities use the metresNum method. */
  def apply(startPt: PtLen2, endPt: PtLen2): LineSegKm2 = new LineSegKm2(startPt.xMetresNum, startPt.yMetresNum, endPt.xMetresNum, endPt.yMetresNum)

  /** Factory apply method for constructing [[LineSegKm2]]s from the X and Y components of the start and end points. There is an apply overload to construct
   * from the start and end points.To construct from scalar quantities use the metresNum method. */
  def apply(xStartPt: Length, yStartPt: Length, xEndPt: Length, yEndPt: Length): LineSegKm2 =
    new LineSegKm2(xStartPt.kilometresNum, yStartPt.kilometresNum, xEndPt.kilometresNum, yEndPt.kilometresNum)

  /** Factory method for constructing [[LineSegKm2]] from scalar quantities. To construct from [[PtLen2]] quantities use the apply methods. */
  def apply(xStartKilometresNum: Double, yStartKilometresNum: Double, xEndKilometresNum: Double, yEndKilometresNum: Double): LineSegKm2 =
    new LineSegKm2(xStartKilometresNum, yStartKilometresNum, xEndKilometresNum, yEndKilometresNum)

  /** [[Show]] and [[Unshow]] type class instances / evidence for [[LineSegKm2]]. */
  implicit val persistEv: Persist2Both[PtKm2, PtKm2, LineSegKm2] =
    Persist2Both[PtKm2, PtKm2, LineSegKm2]("LineSegKM2", "start", _.startPt, "end", _.endPt, LineSegKm2(_, _))

  /** Implicit instance / evidence for [[BuilderArrMap]] type class. */
  implicit val buildEv: BuilderArrDbl4Map[LineSegKm2, LineSegKm2Arr] = new BuilderArrDbl4Map[LineSegKm2, LineSegKm2Arr]
  { type BuffT = LineSegKm2Buff
    override def fromDblArray(array: Array[Double]): LineSegKm2Arr = new LineSegKm2Arr(array)
    def buffFromBufferDbl(buffer: ArrayBuffer[Double]): LineSegKm2Buff = new LineSegKm2Buff(buffer)
  }
}

/** Compact immutable Array[Double] based collection class for [[LineSegKm2]]s. A mathematical straight line segment measured in [[Kilometres]]. */
class LineSegKm2Arr(val arrayUnsafe: Array[Double]) extends AnyVal, LineSegLen2Arr[PtKm2], KilometresBased, ArrDbl4[LineSegKm2]
{ type ThisT = LineSegKm2Arr
  def fromArray(array: Array[Double]): LineSegKm2Arr = new LineSegKm2Arr(array)
  override def typeStr: String = "LineSegKm2Arr"
  override def fElemStr: LineSegKm2 => String = _.toString
  override def newElem(d1: Double, d2: Double, d3: Double, d4: Double): LineSegKm2 = new LineSegKm2(d1, d2, d3, d4)

  override def ++(operand: LineSegLen2Arr[?]): LineSegKm2Arr = ???
  override def slate(operand: _root_.ostrat.geom.VecPtLen2): LineSegKm2Arr = ???
  override def slate(xOperand: _root_.ostrat.geom.Length, yOperand: _root_.ostrat.geom.Length): LineSegKm2Arr = ???
  override def slateX(xOperand: _root_.ostrat.geom.Length): LineSegKm2Arr = ???
  override def slateY(yOperand: _root_.ostrat.geom.Length): LineSegKm2Arr = ???
  override def scale(operand: Double): LineSegKm2Arr = ???
  override def mapGeom2(operand: Length): LineSegArr = ???
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

  /** [[Drawing]] type class instance / evidence for [[LineSegKm2Arr]]. */
  implicit def drawerEv: Drawing[LineSegKm2Arr, RArr[LineSegLen2Draw]] = (obj, lineWidth, colour) => obj.map(_.draw(lineWidth, colour))
}

/** Efficient expandable buffer for [[LineSegKm2]]s. */
class LineSegKm2Buff(val unsafeBuffer: ArrayBuffer[Double]) extends AnyVal with BuffDbl4[LineSegKm2]
{ override def typeStr: String = "LineSegKm2Buff"
  override def newElem(d1: Double, d2: Double, d3: Double, d4: Double): LineSegKm2 = new LineSegKm2(d1, d2, d3, d4)
}
