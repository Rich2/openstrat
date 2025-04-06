/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import collection.mutable.ArrayBuffer

/** A 2-dimensional line segment measured in metres, equivalent of the [[LineSeg]] class. A straight line between two points on a 2-dimensional flat surface. */
class LineSegM2(val xStartMetresNum: Double, val yStartMetresNum: Double, val xEndMetresNum: Double, val yEndMetresNum: Double) extends LineSegLen2[PtM2]
  with LineSegLikeDbl4[PtM2] with Dbl4Elem
{ override def xStart: Metres = Metres(xStartMetresNum)
  override def yStart: Metres = Metres(yStartMetresNum)
  override def xEnd: Metres = Metres(xEndMetresNum)
  override def yEnd: Metres = Metres(yEndMetresNum)
  override def startPt: PtM2 = PtM2(xStart, yStart)
  override def endPt: PtM2 = PtM2(xEnd, yEnd)

  override def slate(operand: VecPtLen2): LineSegM2 = LineSegM2(xStartMetresNum + operand.xMetresNum, yStartMetresNum + operand.yMetresNum,
    xEndMetresNum + operand.xMetresNum, yEndMetresNum + operand.yMetresNum)

  override def slate(xOperand: Length, yOperand: Length): LineSegM2 = LineSegM2(xStartMetresNum + xOperand.metresNum, yStartMetresNum + yOperand.metresNum,
    xEndMetresNum + xOperand.metresNum, yEndMetresNum + yOperand.metresNum)

  override def slateX(xOperand: Length): LineSegM2 = LineSegM2(xStartMetresNum + xOperand.metresNum, yStartMetresNum, xEndMetresNum + xOperand.metresNum,
    yEndMetresNum)

  override def slateY(yOperand: Length): LineSegM2 = LineSegM2(xStartMetresNum, yStartMetresNum + yOperand.metresNum, xEndMetresNum,
    yEndMetresNum + yOperand.metresNum)

  override def scale(operand: Double): LineSegM2 =
    LineSegM2(xStartMetresNum * operand, yStartMetresNum * operand, xEndMetresNum * operand, yEndMetresNum * operand)
  
  override def mapGeom2(operand: Length): LineSeg =
    LineSeg(xStartMetresNum / operand.metresNum, yStartMetresNum / operand.metresNum, xEndMetresNum / operand.metresNum, yEndMetresNum / operand.metresNum)

  override def dbl1: Double = xStartMetresNum
  override def dbl2: Double = yStartMetresNum
  override def dbl3: Double = xEndMetresNum
  override def dbl4: Double = yEndMetresNum
  override def xStartFemtometresNum: Double = xStartMetresNum * 1e15
  override def yStartFemtometresNum: Double = yStartMetresNum * 1e15
  override def xEndFemtometresNum: Double = xEndMetresNum * 1e15
  override def yEndFemtometresNum: Double = yEndMetresNum * 1e15
  override def xStartPicometresNum: Double = xStartMetresNum * 1e12
  override def yStartPicometresNum: Double = yStartMetresNum * 1e12 
  override def xEndPicometresNum: Double = xEndMetresNum * 1e12
  override def yEndPicometresNum: Double = yEndMetresNum * 1e12  
  override def xStartKilometresNum: Double = xStartMetresNum * 1e-3
  override def yStartKilometresNum: Double = yStartMetresNum * 1e-3
  override def xEndKilometresNum: Double = xEndMetresNum * 1e-3
  override def yEndKilometresNum: Double = yEndMetresNum * 1e-3
}

/** Companion object for line segments in a 2-dimensional space measured in metres. Conatains an apply method, an implicit ArrMap builder instance and an
 * extension method. */
object LineSegM2
{ /** Factory apply method for constructing [[LineSegM2]]s from the start and end points. There is an apply overload to construct from the X and Y components of
   * the start and end points. To construct from scalar quantities use the metresNum method. */
  def apply(startPt: PtLen2, endPt: PtLen2): LineSegM2 = new LineSegM2(startPt.xMetresNum, startPt.yMetresNum, endPt.xMetresNum, endPt.yMetresNum)

  /** Factory apply method for constructing [[LineSegM2]]s from the X and Y components of the start and end points. There is an apply overload to construct from
   * the start and end points.To construct from scalar quantities use the metresNum method. */
  def apply(xStartPt: Length, yStartPt: Length, xEndPt: Length, yEndPt: Length): LineSegM2 =
    new LineSegM2(xStartPt.metresNum, yStartPt.metresNum, xEndPt.metresNum, yEndPt.metresNum)

  /** Factory method for constructing [[LineSegM2]] from scalar quantities. To construct from [[PtLen2]] quantities use the apply methods. */
  def apply(xStartMetresNum: Double, yStartMetresNum: Double, xEndMetresNum: Double, yEndMetresNum: Double): LineSegM2 =
    new LineSegM2(xStartMetresNum, yStartMetresNum, xEndMetresNum, yEndMetresNum)

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
class LineSegM2Arr(val arrayUnsafe: Array[Double]) extends LineSegLen2Arr[PtM2], ArrDbl4[LineSegM2], MetresBased
{ type ThisT = LineSegM2Arr
  def fromArray(array: Array[Double]): LineSegM2Arr = new LineSegM2Arr(array)
  override def typeStr: String = "LineSegMArr"

  override def ++(operand: LineSegLen2Arr[?]): LineSegM2Arr =
  { val finalArray: Array[Double] =  operand match
    { case m2Arr: LineSegM2Arr => arrayUnsafe ++ m2Arr.arrayUnsafe

      case _ =>
      { val opLen = operand.arrayLen
        val newArray = new Array[Double](arrayLen + opLen)
        Array.copy(arrayUnsafe, 0, newArray, 0, arrayLen)
        var i = 0
        while (i < operand.arrayLen)
        { newArray(arrayLen + i) = operand.arrayUnsafe(i) * toMetresNum
          i += 1
        }
        newArray
      }
    }  
    new LineSegM2Arr(finalArray)
  }
  
  override def fElemStr: LineSegM2 => String = _.toString
  override def elemFromDbls(d1: Double, d2: Double, d3: Double, d4: Double): LineSegM2 = new LineSegM2(d1, d2, d3, d4)
  override def slate(operand: VecPtLen2): LineSegM2Arr = map(_.slate(operand))
  override def slate(xOperand: Length, yOperand: Length): LineSegM2Arr = map(_.slate(xOperand, yOperand))
  override def slateX(xOperand: Length): LineSegM2Arr = map(_.slateX(xOperand))
  override def slateY(yOperand: Length): LineSegM2Arr = map(_.slateY(yOperand))
  override def scale(operand: Double): LineSegM2Arr = map(_.scale(operand))
  override def mapGeom2(operand: Length): LineSegArr = map(_.mapGeom2(operand))
}

/** Companion object for the [[LineSegM2]]s class. */
object LineSegM2Arr extends CompanionSeqLikeDbl4[LineSegM2, LineSegM2Arr]
{ override def fromArray(array: Array[Double]): LineSegM2Arr = new LineSegM2Arr(array)

  /** Implicit instance / evidence for [[BuilderArrFlat]] type class instance. */
  implicit val flatBuildEv: BuilderArrFlat[LineSegM2Arr] = new BuilderArrDbl4Flat[LineSegM2Arr]
  { type BuffT = LineSegM2Buff
    override def fromDblArray(array: Array[Double]): LineSegM2Arr = new LineSegM2Arr(array)
    def buffFromBufferDbl(inp: ArrayBuffer[Double]): LineSegM2Buff = new LineSegM2Buff(inp)
  }

  /** [[Show]] type class instance / evidence for [[LineSegM2Arr]]. */
  implicit lazy val showEv: ShowSequ[LineSegM2, LineSegM2Arr] = ShowSequ[LineSegM2, LineSegM2Arr]()

  /** [[Unshow]] type class instance / evidence for [[LineSegM2Arr]]. */
  implicit lazy val unshowEv: UnshowSeq[LineSegM2, LineSegM2Arr] = UnshowSeq[LineSegM2, LineSegM2Arr]()

  /** [[Drawing]] type class instance / evidence for [[LineSegM2Arr]]. */
  implicit def drawerEv: Drawing[LineSegM2Arr, RArr[LineSegLen2Draw]] = (obj, lineWidth, colour) => obj.map(_.draw(lineWidth, colour))
}

/** Efficient expandable buffer for [[LineSegM2]]s. */
class LineSegM2Buff(val unsafeBuffer: ArrayBuffer[Double]) extends AnyVal with BuffDbl4[LineSegM2]
{ override def typeStr: String = "Line2sBuff"
  override def newElem(d1: Double, d2: Double, d3: Double, d4: Double): LineSegM2 = new LineSegM2(d1, d2, d3, d4)
}